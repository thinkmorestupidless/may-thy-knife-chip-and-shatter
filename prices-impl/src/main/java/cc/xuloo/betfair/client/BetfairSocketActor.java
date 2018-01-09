package cc.xuloo.betfair.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import akka.util.ByteString;
import cc.xuloo.betfair.client.stream.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import scala.concurrent.duration.FiniteDuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.opengamma.strata.collect.Unchecked.wrap;

public class BetfairSocketActor extends AbstractActor {

    private static final String CRLF = "\r\n";

    public static Props props(ActorRef socket, ObjectMapper mapper, ActorRef streamHandler) {
        return Props.create(BetfairSocketActor.class, () -> new BetfairSocketActor(socket, mapper, streamHandler));
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final ActorRef socket;

    private final ObjectMapper mapper;

    private final ActorRef streamHandler;

    private Cancellable timer;

    private StringBuilder buffer;

    public BetfairSocketActor(ActorRef socket, ObjectMapper mapper, ActorRef streamHandler) {
        this.socket = socket;
        this.mapper = mapper;
        this.streamHandler = streamHandler;
    }

    @Override
    public void postStop() throws Exception {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public Receive createReceive() {
        return disconnected();
    }

    public Receive disconnected() {
        return receiveBuilder()
                .match(ConnectMessage.class, msg -> {
                    getContext().become(connecting(getSender()));
                    socket.tell(msg, getSelf());
                })
                .matchAny(o -> log.warning("i'm in state 'disconnected' and i don't know what to do with {}", o))
                .build();
    }

    public Receive connecting(ActorRef listener) {
        return receiveBuilder()
                .match(Tcp.Connected.class, msg -> {
                    log.debug("TCP socket connection established to {}", msg.remoteAddress());
                })
                .match(ByteString.class, msg -> {
                    ConnectionMessage cm = mapper.readValue(msg.utf8String(), ConnectionMessage.class);

                    log.debug("connection to betfair established");

                    getContext().become(connected(listener));
                    listener.tell(cm, getSelf());
                })
                .matchAny(o -> log.warning("i'm in state 'connecting' and i don't know what to do with {}", o))
                .build();
    }

    public Receive connected(ActorRef listener) {
        buffer = new StringBuilder();

        return receiveBuilder()
                .match(AuthenticationMessage.class, msg -> {
                    restartHeartbeatTimer();
                    toSocket(msg);
                })
                .match(StreamProtocol.HeartbeatReceived.class, msg -> {
                    restartHeartbeatTimer();
                })
                .match(StreamProtocol.class, this::toSocket)
                .match(ByteString.class, bytes -> {
                    String s = bytes.utf8String();
                    log.info(s);
                    buffer.append(s);

                    if (buffer.toString().endsWith("\r\n")) {

                        log.debug("parsing {}", buffer.toString());

                        ResponseMessage msg = null;

                        try {
                            msg = mapper.readValue(buffer.toString(), ResponseMessage.class);
                        } catch (IOException e) {
                            log.error("problem reading json value -> {} -> {}", buffer.toString(), e);
                        }

                        if (msg != null) {
                            if (msg instanceof StatusMessage) {
                                log.debug("Handling status message {}", msg);

                                if (msg.getId() != null) {
                                    if (msg.getId().equals(1)) {
                                        log.debug("stream succesfully authenticated");

                                        listener.tell(msg, getSelf());
                                    } else if (msg.getId().equals(2)) {
                                        restartHeartbeatTimer();
                                    }
                                }

                                listener.tell(msg, getSelf());

                            } else if (msg instanceof MarketChangeMessage) {
                                log.info(buffer.toString());
                                log.debug("handling market change message -> {}", msg);

                                MarketChangeMessage mcm = (MarketChangeMessage) msg;

                                if (mcm.getCt() == MarketChangeMessage.CtEnum.HEARTBEAT) {
                                    restartHeartbeatTimer();
                                } else if (mcm.getCt() == MarketChangeMessage.CtEnum.SUB_IMAGE) {
                                    streamHandler.tell(mcm, getSelf());
                                }
                            } else {
                                log.debug("ignoring message -> {}", msg);
                            }
                        }

                        buffer = new StringBuilder();
                    }
                })
                .matchAny(o -> log.warning("i'm in state 'connected' and i don't know what to do with {}", o))
                .build();
    }

    public void restartHeartbeatTimer() {
        if (timer != null) {
            timer.cancel();
        }

        timer = getContext()
                .getSystem()
                .scheduler()
                .scheduleOnce(FiniteDuration.create(10, TimeUnit.SECONDS),
                        getSelf(),
                        HeartbeatMessage.builder().id(2).build(),
                        getContext().getSystem().dispatcher(),
                        getSelf());
    }

    public void toSocket(StreamProtocol msg) {
        log.debug("sending message {}", msg);

        ByteString bs = wrap(() -> ByteString.fromString(mapper.writeValueAsString(msg) + CRLF));
        socket.tell(bs, getSelf());
    }
}
