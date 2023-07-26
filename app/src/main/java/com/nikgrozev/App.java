package com.nikgrozev;

import static io.javalin.apibuilder.ApiBuilder.crud;

import com.nikgrozev.controllers.ItemController;
import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        var app = makeJavalinServer();
        app.start(7070);
    }

    public static Javalin makeJavalinServer() {
        var app = Javalin.create();
        app.get("/api/health", ctx -> ctx.result("Healthy"));
        app.routes(() -> crud("/api/items/{id}", new ItemController()));
        return app;
    }
}
