package com.nikgrozev.controllers;

import com.nikgrozev.model.Item;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.List;

public class ItemController implements CrudHandler {
    private List<Item> items = new ArrayList<>(List.of(new Item("item 1"), new Item("item 2")));

    @Override
    public void create(Context ctx) {
        Item newItem = ctx.bodyAsClass(Item.class);
        items.add(newItem);
        ctx.json(newItem, Item.class);
    }

    @Override
    public void delete(Context ctx, String id) {
        if (items.removeIf(i -> id.equals((i.getId())))) {
            ctx.status(200);
        } else {
            ctx.status(404);
        }
    }

    @Override
    public void getAll(Context ctx) {
        ctx.json(items);
    }

    @Override
    public void getOne(Context ctx, String id) {
        var item = items.stream().filter(i -> id.equals(i.getId())).findFirst();
        if (item.isPresent()) {
            ctx.json(item.get());
        } else {
            ctx.status(404);
        }
    }

    @Override
    public void update(Context ctx, String id) {
        var item = items.stream().filter(i -> id.equals(i.getId())).findFirst();
        if (item.isPresent()) {
            var updatedItem = ctx.bodyAsClass(Item.class);
            items.replaceAll(i -> id.equals(i.getId()) ? updatedItem : i);
            ctx.status(200);
        } else {
            ctx.status(404);
        }
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public void setItems(List<Item> items) {
        this.items = new ArrayList<>(items);
    }
}
