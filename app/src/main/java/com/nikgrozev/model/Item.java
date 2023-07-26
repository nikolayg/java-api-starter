package com.nikgrozev.model;

import java.util.UUID;

public class Item {
    private String name;
    private String id;

    public Item() {
        this(UUID.randomUUID().toString(), "");
    }

    public Item(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public Item(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
