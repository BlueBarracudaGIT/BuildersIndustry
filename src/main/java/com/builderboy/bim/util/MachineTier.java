package com.builderboy.bim.util;

public enum MachineTier {

    SIMPLE(0),
    BASIC(1),
    DEFAULT(2),
    ADVANCED(3),
    ENHANCED(4),
    ENDER(5);

    private int id;

    MachineTier(int id) {
        this.id = id;
    }

    public int getId() { return id; }
}