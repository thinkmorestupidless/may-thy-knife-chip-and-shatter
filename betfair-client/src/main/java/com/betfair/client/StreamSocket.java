package com.betfair.client;

import akka.Done;
import com.betfair.stream.model.AuthenticationMessage;
import com.betfair.stream.model.ConnectionMessage;
import com.betfair.stream.model.RequestMessage;
import com.betfair.stream.model.ResponseMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.collect.Maps;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class StreamSocket {

    private static final Logger log = LoggerFactory.getLogger(StreamSocket.class);

    private static final String CRLF = "\r\n";

    private final ObjectMapper mapper;

    private final Map<Integer, StreamEnvelope> envelopes = Maps.newHashMap();

    private final SessionProvider sessions;

    private final Config conf;

    private Socket socket;

    private BufferedReader reader;

    private BufferedWriter writer;

    private Thread readThread;

    private Optional<ResponseMessage> authenticated = Optional.empty();

    public StreamSocket(SessionProvider sessions, Config conf) {
        this.sessions = sessions;
        this.conf = conf;

        mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
    }

    public CompletionStage<ResponseMessage> send(RequestMessage request) {
        log.info("sending {}", request);

        StreamEnvelope envelope = new StreamEnvelope(request);

        envelopes.put(request.getId(), envelope);

        doSend(request);

        return envelope.getFuture();
    }

    public void doSend(Object message) {
        try {
            String line = mapper.writeValueAsString(message);
            log.debug("writing -> {}", line);

            writer.write(line);
            writer.write(CRLF);
            writer.flush();
        } catch (JsonProcessingException e) {
            log.error("error processing JSON -> {}", e);
        } catch (Exception e) {
            log.error("error writing line -> {}", e);
        }
    }

    public CompletionStage<ResponseMessage> authenticate() {
        log.info("authenticating {}", authenticated);

        if (authenticated.isPresent()) {
            log.info("already authenticated - noop");

            return CompletableFuture.completedFuture(authenticated.get());
        }

        log.info("setting up the socket and sending auth");

        return connect().thenCompose(
                ignored -> sessions.session().thenCompose(
                        session -> send(AuthenticationMessage.builder().session(session.sessionToken()).appKey(session.applicationKey()).build()).thenApply(response -> {
                            log.info("authentication successful");
                            authenticated = Optional.of(response);
                            return response;
                        })));
    }

    public CompletionStage<Socket> connect() {
        return socket().thenApply(socket -> this.socket = socket);
    }

    public CompletionStage<Done> disconnect() {
        return CompletableFuture.completedFuture(Done.getInstance());
    }

    public CompletionStage<Socket> socket() {

        if (socket != null) {
            return CompletableFuture.completedFuture(socket);
        }

        CompletableFuture future = new CompletableFuture();

        try {
            Socket socket = createSocket();
            socket.setReceiveBufferSize(1024 * 100 * 2);
            socket.setSoTimeout(30 * 1000);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            (readThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String line = reader.readLine();

                            ResponseMessage response = mapper.readValue(line, ResponseMessage.class);

                            if (response instanceof ConnectionMessage) {
                                log.debug("connection response received");

                                future.complete(socket);
                            } else {
                                log.info("message received -> {}", line);

                                if (envelopes.containsKey(response.getId())) {
                                    StreamEnvelope envelope = envelopes.get(response.getId());

                                    envelope.getFuture().complete(response);
                                } else {
                                    log.warn("we don't appear to have a request for response id {}", response.getId());
                                }
                            }
                        } catch (Exception e) {
                            log.error("problem reading from socket", e);
                        }

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            log.error("interruption", e);
                        }
                    }
                }
            }))
            .start();

            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (SocketException e) {
            throw new RuntimeException("failed to set property on socket", e);
        } catch (IOException e) {
            throw new RuntimeException("failed to get stream from socket", e);
        }

        return future;
    }

    public Socket createSocket() {
        String hostName = conf.getString("betfair.stream.uri");
        int port = 80;

        if (hostName.contains(":")) {
            String[] parts = hostName.split(":");
            hostName = parts[0];
            port = Integer.parseInt(parts[1]);
        }

        if (port == 443) {
            try {
                SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(hostName, port);
                socket.startHandshake();

                return socket;
            } catch (IOException e) {
                throw new RuntimeException("failed to create SSL Socket", e);
            }
        } else {
            try {
                return new Socket(hostName, port);
            } catch (IOException e) {
                throw new RuntimeException("failed to create Socket", e);
            }
        }
    }
}
