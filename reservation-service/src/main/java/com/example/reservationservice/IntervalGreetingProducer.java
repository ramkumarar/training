package com.example.reservationservice;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Component
public class IntervalGreetingProducer {

    Flux<GreetingResponse> greetingProducer(GreetingRequest request){
        return Flux.fromStream(Stream.generate(() -> new GreetingResponse("Hello " + request.getRequest() + " @ " + Instant.now()))).delayElements(Duration.ofSeconds(2));
    }

}
