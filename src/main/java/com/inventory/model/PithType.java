package com.inventory.model;

public enum PithType {
    REGULAR("Regular Pith"),
    LOW_EC("Low EC Pith");

    private final String displayName;

    PithType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}