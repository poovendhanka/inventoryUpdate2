package com.inventory.service;

public interface WhiteFiberStockService {
    void addStock(Double quantity);

    Double getCurrentStock();
}