package com.example.reservationservice;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class RsocketController {

    private final IntervalGreetingProducer intervalGreetingProducer;

    @MessageMapping("greetings")
    Publisher<GreetingResponse> rsocketProcessor(GreetingRequest request){
        return intervalGreetingProducer.greetingProducer(request);
    }

}
