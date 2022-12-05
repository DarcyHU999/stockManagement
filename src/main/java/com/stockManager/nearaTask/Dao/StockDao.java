package com.stockManager.nearaTask.Dao;

import com.stockManager.nearaTask.model.Stock;

import java.math.BigDecimal;

public interface StockDao {
    public int insertStock(String stockSym, Stock stock);
    public int removeStock(String stockSym,Stock stock);
    public Stock getStock(String stockSym);
}
