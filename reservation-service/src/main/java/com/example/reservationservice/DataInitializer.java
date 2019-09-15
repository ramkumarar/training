package com.example.reservationservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
@Log4j2
public class DataInitializer {

    private final ReservationRepository reservationRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize(){
        Flux<Reservation> reservations = Flux.just("Tom", "Jerry", "Donald")
            .map( name -> new Reservation(null, name))
            .flatMap( reservation -> reservationRepository.save(reservation));

        reservationRepository.deleteAll()
                .thenMany(reservations)
                .thenMany(reservationRepository.findAll())
                .subscribe(log::info);
    }
}
