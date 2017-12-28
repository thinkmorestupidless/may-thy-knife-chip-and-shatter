package cc.xuloo.betfair.client.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.stream.javadsl.SourceQueue;
import akka.util.ByteString;
import cc.xuloo.betfair.stream.RequestMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opengamma.strata.collect.Unchecked;

public class BetfairSocketActor extends AbstractActor {

    private static final String CRLF = "\r\n";

    public static Props props(ActorRef socket, ObjectMapper mapper) {
        return Props.create(BetfairSocketActor.class, () -> new BetfairSocketActor(socket, mapper));
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final ActorRef socket;

    private final ObjectMapper mapper;

    private SourceQueue<RequestMessage> stream;

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
    public Receive createReceive() {
        return receiveBuilder()
//                .match(RequestMessage.class, msg -> socket.tell(msg, getSelf())/*stream.offer(msg)*/)
                .match(StreamProtocol.class, msg -> {
                    String s = Unchecked.wrap(() -> mapper.writeValueAsString(msg)) + CRLF;
                    ByteString bs = ByteString.fromString(s);
                    log.info("sending message {}", bs);
                    socket.tell(bs, getSelf());
                })
                .matchAny(o -> log.info("i don't know what to do with {}", o))
                .build();
    }
}
