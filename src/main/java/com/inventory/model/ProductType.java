package com.inventory.model;

public enum ProductType {
    PITH_REGULAR("Regular Pith"),
    PITH_LOW_EC("Low EC Pith"),
    FIBER_BROWN("Brown Fiber"),
    FIBER_WHITE("White Fiber"),
    BLOCK("Block");

    private final String displayName;

    ProductType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}