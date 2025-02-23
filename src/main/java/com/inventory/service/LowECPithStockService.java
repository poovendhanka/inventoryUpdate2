package com.inventory.service;

public interface LowECPithStockService {
    void addStock(Double quantity);

    Double getCurrentStock();
}