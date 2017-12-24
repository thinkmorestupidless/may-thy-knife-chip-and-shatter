package com.betfair.client;

import akka.Done;
import akka.NotUsed;
import akka.stream.Materializer;
import akka.stream.OverflowStrategy;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.betfair.stream.model.RequestMessage;
import com.betfair.stream.model.ResponseMessage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class StreamEnvelope {

    private final RequestMessage request;

    private CompletableFuture<ResponseMessage> future;

    public StreamEnvelope(RequestMessage request) {
        this.request = request;
    }

    public RequestMessage getRequest() {
        return request;
    }

    public CompletionStage<Done> forEach(Flow<Object, Done, NotUsed> flow) {
        Materializer mat = null;
        return CompletableFuture.completedFuture(Done.getInstance());//Source.queue(100, OverflowStrategy.backpressure()).via(flow).to(Sink.ignore()).run(mat);
    }

    public CompletableFuture<ResponseMessage> getFuture() {
        if (future == null) {
            future = new CompletableFuture<>();
        }

        return future;
    }

    @Override
    public String toString() {
        return "StreamEnvelope{" +
                "request=" + request +
                ", future=" + future +
                '}';
    }
}
