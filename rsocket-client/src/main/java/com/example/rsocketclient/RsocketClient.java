package com.example.rsocketclient;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RsocketClient {

    private final RSocketRequester rSocketRequester;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/greet/{name}")
    Publisher<GreetingResponse> greetingResponsePublisher(@PathVariable String name){

        return rSocketRequester.route("greetings").data(new GreetingRequest(name)).retrieveFlux(GreetingResponse.class);
    }
}
