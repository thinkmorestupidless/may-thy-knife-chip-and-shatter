package cc.xuloo.betfair.client.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.stream.javadsl.SourceQueue;
import akka.util.ByteString;
import cc.xuloo.betfair.stream.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

import static com.opengamma.strata.collect.Unchecked.wrap;

public class BetfairSocketActor extends AbstractActor {

    private static final String CRLF = "\r\n";

    public static Props props(ActorRef socket, ObjectMapper mapper) {
        return Props.create(BetfairSocketActor.class, () -> new BetfairSocketActor(socket, mapper));
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final ActorRef socket;

    private final ObjectMapper mapper;

    private SourceQueue<RequestMessage> stream;

    private Cancellable timer;

    public BetfairSocketActor(ActorRef socket, ObjectMapper mapper) {
        this.socket = socket;
        this.mapper = mapper;
    }

    @Override
    public void preStart() throws Exception {

//        Materializer mat = ActorMaterializer.create(getContext());
//        Source<RequestMessage, SourceQueueWithComplete<RequestMessage>> source = Source.queue(1000, OverflowStrategy.backpressure());
//        Flow<RequestMessage, ByteString, NotUsed> flow = Flow.of(RequestMessage.class).map(msg -> Unchecked.wrap(() -> ByteString.fromString(mapper.writeValueAsString(msg) + CRLF)));
//        Sink<ByteString, NotUsed> sink = Sink.actorRefWithAck(socket, SocketWrapper.Init.class, SocketWrapper.Ack.class, SocketWrapper.Complete.class, t -> t);
//
//        stream = source.via(flow).to(sink).run(mat);
    }

    @Override
    public void postStop() throws Exception {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ConnectMessage.class, msg -> {
                    socket.forward(msg, getContext());
                })
                .match(AuthenticationMessage.class, msg -> {
                    timer = getContext()
                                .getSystem()
                                .scheduler()
                                .schedule(FiniteDuration.create(1, TimeUnit.SECONDS),
                                          FiniteDuration.create(10, TimeUnit.SECONDS),
                                          getSelf(),
                                          HeartbeatMessage.builder().id(2).build(),
                                          getContext().getSystem().dispatcher(),
                                          getSelf());
                    toSocket(msg);
                })
                .match(StreamProtocol.class, this::toSocket)
                .matchAny(o -> log.info("i don't know what to do with {}", o))
                .build();
    }

    public void toSocket(StreamProtocol msg) {
        log.info("sending message {}", msg);

        ByteString bs = wrap(() -> ByteString.fromString(mapper.writeValueAsString(msg) + CRLF));
        socket.tell(bs, getSelf());
    }
}
