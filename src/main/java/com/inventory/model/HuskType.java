package com.inventory.model;

public enum HuskType {
    GREEN_HUSK("Green Husk"),
    BROWN_HUSK("Brown Husk");

    private final String displayName;

    HuskType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}