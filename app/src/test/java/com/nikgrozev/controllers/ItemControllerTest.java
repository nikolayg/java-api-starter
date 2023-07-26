package com.nikgrozev.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.nikgrozev.model.Item;
import io.javalin.http.Context;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemControllerTest {

    private final Context ctx = mock(Context.class);
    private ItemController controller = new ItemController();

    @BeforeEach
    void setUp() {
        controller.setItems(List.of());
    }

    @Test
    void testCreate() {
        var newItem = new Item("id1", "item1");
        when(ctx.bodyAsClass(Item.class)).thenReturn(newItem);
        controller.create(ctx);
        verify(ctx).json(newItem, Item.class);
        assertEquals(1, controller.getItems().size());
        assertEquals("id1", controller.getItems().get(0).getId());
    }

    @Test
    void testDeletePresent() {
        controller.setItems(List.of(new Item("id1", "item1")));
        controller.delete(ctx, "id1");
        verify(ctx).status(200);
        assertTrue(controller.getItems().isEmpty());
    }

    @Test
    void testDeleteMissing() {
        controller.setItems(List.of(new Item("id1", "item1")));
        controller.delete(ctx, "id2");
        verify(ctx).status(404);
        assertEquals(1, controller.getItems().size());
    }

    @Test
    void testGetAll() {
        controller.setItems(List.of(new Item("id1", "item1"), new Item("id2", "item2")));
        controller.getAll(ctx);
        verify(ctx).json(controller.getItems());
    }

    @Test
    void testGetOneWhenPresent() {
        var item = new Item("id1", "item1");
        controller.setItems(List.of(item));
        controller.getOne(ctx, "id1");
        verify(ctx).json(item);
    }

    @Test
    void testGetOneWhenMissing() {
        var item = new Item("id1", "item1");
        controller.setItems(List.of(item));
        controller.getOne(ctx, "id2");
        verify(ctx).status(404);
        verify(ctx, never()).json(any(Item.class));
    }

    @Test
    void testUpdateWhenPresent() {
        controller.setItems(List.of(new Item("id1", "item1"), new Item("id2", "item2")));
        when(ctx.bodyAsClass(Item.class)).thenReturn(new Item("id1", "item1-new"));
        controller.update(ctx, "id1");
        verify(ctx).status(200);
        assertEquals("item1-new", controller.getItems().get(0).getName());
    }

    @Test
    void testUpdateWhenMissing() {
        controller.setItems(List.of(new Item("id1", "item1"), new Item("id2", "item2")));
        when(ctx.bodyAsClass(Item.class)).thenReturn(new Item("id1", "item1-new"));
        controller.update(ctx, "id3");
        verify(ctx).status(404);
    }
}
