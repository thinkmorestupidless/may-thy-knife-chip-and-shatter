package cc.xuloo.betfair.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import cc.xuloo.betfair.stream.RequestMessage;
import cc.xuloo.betfair.stream.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opengamma.strata.collect.Unchecked;

public class JacksonSerialisingSocketActor extends AbstractActor {

    public static Props props(ActorRef client, ObjectMapper mapper) {
        return Props.create(JacksonSerialisingSocketActor.class, () -> new JacksonSerialisingSocketActor(client, mapper));
    }

    private final ActorRef client;

    private final ObjectMapper mapper;

    public JacksonSerialisingSocketActor(ActorRef client, ObjectMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RequestMessage.class, this::handleSend)
                .match(ResponseMessage.class, this::handleReceive)
                .build();
    }

    public void handleSend(RequestMessage msg) {
        String s = Unchecked.wrap(() -> mapper.writeValueAsString(msg));
        client.tell(s, getSelf());
    }

    public void handleReceive(ResponseMessage msg) {

    }
}
