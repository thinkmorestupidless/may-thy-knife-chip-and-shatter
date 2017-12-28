package cc.xuloo.betfair.client;

import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.OverflowStrategy;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;
import akka.stream.javadsl.SourceQueue;
import akka.stream.javadsl.SourceQueueWithComplete;
import akka.stream.scaladsl.Sink;
import cc.xuloo.betfair.stream.RequestMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opengamma.strata.collect.Unchecked;

public class BetfairSocketActor extends AbstractActor {

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
        Materializer mat = ActorMaterializer.create(getContext());
        Source<RequestMessage, SourceQueueWithComplete<RequestMessage>> source = Source.queue(1000, OverflowStrategy.backpressure());
        Flow<RequestMessage, String, NotUsed> flow = Flow.of(RequestMessage.class).map(msg -> Unchecked.wrap(() -> mapper.writeValueAsString(msg)));
        Sink<String, NotUsed> sink = Sink.actorRefWithAck(socket, SocketWrapper.Init.class, SocketWrapper.Ack.class, SocketWrapper.Complete.class, t -> {
            log.warning("message failed with {}", t);
            return null;
        });

        stream = source.via(flow).to(sink).run(mat);
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RequestMessage.class, msg -> stream.offer(msg))
                .build();
    }
}
