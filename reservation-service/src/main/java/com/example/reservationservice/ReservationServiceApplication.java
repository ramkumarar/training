package com.example.reservationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
public class ReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}

   @Bean
   RouterFunction<ServerResponse> routerFunction( ReservationRepository reservationRepository){
       return RouterFunctions.route().GET("/reservations", serverRequest -> ServerResponse.ok().body(reservationRepository.findAll(), Reservation.class)).build();
   }

}
