package com.rbsg.reactive;


import com.rbsg.reactive.verticles.HelloVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Hello world!
 *
 */
public class App extends AbstractVerticle
{

     @Override
     public void start() throws Exception {
         final int PROCESSOR_COUNT = Runtime.getRuntime().availableProcessors();
         System.out.println("Processsor Count " + PROCESSOR_COUNT);

         final DeploymentOptions options = new DeploymentOptions().setInstances(PROCESSOR_COUNT);
         this.vertx.deployVerticle(HelloVerticle.class.getName(),options);
     }

    public static void main( String[] args ) {
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(App.class.getName());


    }
}
