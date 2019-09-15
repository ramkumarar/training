package com.rbsg.reactive.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

import java.time.LocalDateTime;

public class HelloVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        super.start();
        HttpServer httpServer = vertx.createHttpServer();

        Router router = Router.router(vertx);

        Route routeHandler1 = router.get("/hello/:name")
                .handler( routingContext -> {
                    String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
                    LocalDateTime localDate = LocalDateTime.now();


                    String name = routingContext.request().getParam("name");
                    HttpServerResponse response = routingContext.response();
                    response.putHeader("Content-Type", "text/plain");
                    response.end("Hello " + name + " " + localDate);
                });

        Route routeHandler2 = router.get("/calculate/:input")
                .blockingHandler( routingContext -> {
                    String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
                    LocalDateTime localDate = LocalDateTime.now();


                    Integer input = Integer.valueOf(routingContext.request().getParam("input"))  ;
                    HttpServerResponse response = routingContext.response();
                    response.putHeader("Content-Type", "text/plain");
                    response.end(String.valueOf(PI.calculate(input)));
                }, false);



        httpServer.requestHandler(router).listen(9081, handlr -> {
            if(handlr.failed()){
                System.out.println(handlr.cause().getMessage());
            }else {
                System.out.println("Server started successfully");
            }
        });

    }
}
