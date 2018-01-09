package cc.xuloo.betfair.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import akka.io.TcpMessage;
import akka.util.ByteString;
import cc.xuloo.betfair.client.stream.ConnectMessage;

import java.net.InetSocketAddress;

public class SocketActor extends AbstractActor {

    public static Props props() {
        return Props.create(SocketActor.class, SocketActor::new);
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public SocketActor() {

    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ConnectMessage.class, msg -> {
                    final ActorRef tcp = Tcp.get(getContext().getSystem()).manager();
                    InetSocketAddress remote = InetSocketAddress.createUnresolved(msg.getHost(), msg.getPort());
                    tcp.tell(TcpMessage.connect(remote), getSelf());
                    getContext().become(connecting(getSender()));
                })
                .build();
    }

    private Receive connecting(final ActorRef listener) {
        return receiveBuilder()
                .match(Tcp.CommandFailed.class, msg -> {
                    listener.tell("failed", getSelf());
                    getContext().stop(getSelf());

                })
                .match(Tcp.Connected.class, msg -> {
                    listener.tell(msg, getSelf());
                    getSender().tell(TcpMessage.register(getSelf()), getSelf());
                    getContext().become(connected(getSender(), listener));
                })
                .build();
    }

    private Receive connected(final ActorRef connection, final ActorRef listener) {
        return receiveBuilder()
                .match(ByteString.class, msg -> {
                    log.debug("sending message -> {}", msg);
                    connection.tell(TcpMessage.write((ByteString) msg), getSelf());
                })
                .match(Tcp.CommandFailed.class, msg -> {
                    // OS kernel socket buffer was full
                })
                .match(Tcp.Received.class, msg -> {
                    log.debug("received -> {} -> {}", msg.data(), listener);
                    listener.tell(msg.data(), getSelf());
                })
                .matchEquals("close", msg -> {
                    connection.tell(TcpMessage.close(), getSelf());
                })
                .match(Tcp.ConnectionClosed.class, msg -> {
                    getContext().stop(getSelf());
                })
                .build();
    }

}
