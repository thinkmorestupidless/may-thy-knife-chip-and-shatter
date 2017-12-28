package cc.xuloo.betfair.client.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import akka.io.TcpMessage;
import akka.util.ByteString;

import java.net.InetSocketAddress;

public class SocketActor extends AbstractActor {

    public static Props props(InetSocketAddress remote, ActorRef listener) {
        return Props.create(SocketActor.class, remote, listener);
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final InetSocketAddress remote;

    private final ActorRef listener;

    public SocketActor(InetSocketAddress remote, ActorRef listener) {
        this.remote = remote;
        this.listener = listener;

        final ActorRef tcp = Tcp.get(getContext().getSystem()).manager();
        tcp.tell(TcpMessage.connect(remote), getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Tcp.CommandFailed.class, msg -> {
                    log.info("failed");
                    listener.tell("failed", getSelf());
                    getContext().stop(getSelf());

                })
                .match(Tcp.Connected.class, msg -> {
                    log.info("connected");
                    listener.tell(msg, getSelf());
                    getSender().tell(TcpMessage.register(getSelf()), getSelf());
                    getContext().become(connected(getSender()));
                })
                .build();
    }

    private Receive connected(final ActorRef connection) {
        return receiveBuilder()
                .match(ByteString.class, msg -> {
                    log.info("sending {}", msg);
                    connection.tell(TcpMessage.write((ByteString) msg), getSelf());
                })
                .match(Tcp.CommandFailed.class, msg -> {
                    // OS kernel socket buffer was full
                })
                .match(Tcp.Received.class, msg -> {
                    log.info("received -> {}", msg.data());
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
