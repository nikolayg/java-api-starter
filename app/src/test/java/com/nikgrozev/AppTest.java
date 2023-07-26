package com.nikgrozev;

import static org.junit.jupiter.api.Assertions.*;

import com.nikgrozev.model.Item;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.javalin.testtools.JavalinTest;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppTest {

    private Javalin app;
    private static JavalinJackson JJson = new JavalinJackson();

    private static Item parseItem(String json) {
        return (Item) JJson.fromJsonString(json, Item.class);
    }

    private static Item[] parseItems(String json) {
        return (Item[]) JJson.fromJsonString(json, Item[].class);
    }

    private static String toJson(Item item) {
        return JJson.toJsonString(item, Item.class);
    }

    @BeforeEach
    void setUp() throws Exception {
        app = App.makeJavalinServer();
    }

    @Test
    void testHealthEndpoint() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    var response = client.get("/api/health");
                    assertEquals(200, response.code());
                    assertEquals("Healthy", response.body().string());
                });
    }

    @Test
    void testCreateItem() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    // Create one
                    var response = client.post("/api/items", toJson(new Item("test")));
                    assertEquals(200, response.code());
                    // Get all to confirm it's all there
                    response = client.get("/api/items");
                    var allItems = parseItems(response.body().string());
                    assertTrue(Arrays.stream(allItems).anyMatch(i -> "test".equals(i.getName())));
                });
    }

    @Test
    void testDeleteItem() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    // Create one
                    var response = client.post("/api/items", toJson(new Item("test")));
                    var createdItem = (Item) parseItem(response.body().string());

                    // Delete it
                    response = client.delete("/api/items/" + createdItem.getId());
                    assertEquals(200, response.code());

                    // Get all to confirm it's not there
                    response = client.get("/api/items");
                    var allItems = parseItems(response.body().string());
                    assertFalse(Arrays.stream(allItems).anyMatch(i -> "test".equals(i.getName())));
                });
    }

    @Test
    void testDeleteMissingItem() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    var response = client.delete("/api/items/missing-item");
                    assertEquals(404, response.code());
                });
    }

    @Test
    void testGetOneItem() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    // Create one
                    var response = client.post("/api/items", toJson(new Item("test")));
                    var createdItem = parseItem(response.body().string());

                    // Get it back
                    response = client.get("/api/items/" + createdItem.getId());
                    assertEquals(200, response.code());
                    var loadedItem = parseItem(response.body().string());
                    assertEquals("test", loadedItem.getName());
                });
    }

    @Test
    void tesGetOneMissingItem() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    var response = client.get("/api/items/missing-item");
                    assertEquals(404, response.code());
                });
    }

    @Test
    void testUpdateOneItem() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    // Create one
                    var response = client.post("/api/items", toJson(new Item("test")));
                    var createdItem = parseItem(response.body().string());

                    // Update it
                    createdItem.setName("NewName");
                    response =
                            client.patch("/api/items/" + createdItem.getId(), toJson(createdItem));
                    assertEquals(200, response.code());

                    // Get all to confirm it's been deleted
                    response = client.get("/api/items");
                    var allItems = parseItems(response.body().string());
                    assertTrue(
                            Arrays.stream(allItems)
                                    .anyMatch(
                                            i ->
                                                    createdItem.getId().equals(i.getId())
                                                            && "NewName".equals(i.getName())));
                });
    }

    @Test
    void testUpdateOneMissingItem() {
        JavalinTest.test(
                app,
                (server, client) -> {
                    var response =
                            client.patch("/api/items/missing-item", toJson(new Item("test")));
                    assertEquals(404, response.code());
                });
    }
}
