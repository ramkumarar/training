package com.example.rsocketclient;

import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;

import java.net.InetSocketAddress;

@Configuration
public class RsocketConfig {

    @Bean
    RSocketRequester rSocketRequester(RSocketStrategies rSocketStrategies){
        return RSocketRequester.builder().rsocketStrategies(rSocketStrategies)
                .rsocketFactory(clientRSocketFactory -> clientRSocketFactory
                        .metadataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .frameDecoder(PayloadDecoder.ZERO_COPY)
                )
                .connect(TcpClientTransport.create(new InetSocketAddress("localhost", 8000)))
                .retry()
                .block();
    }
}
