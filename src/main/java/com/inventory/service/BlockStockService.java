package com.inventory.service;

public interface BlockStockService {
    void addStock(Double quantity);

    Double getCurrentStock();
}