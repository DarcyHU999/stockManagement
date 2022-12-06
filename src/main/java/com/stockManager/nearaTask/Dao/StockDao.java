package com.stockManager.nearaTask.Dao;

import com.stockManager.nearaTask.model.Stock;

import java.math.BigDecimal;
import java.util.HashMap;

public interface StockDao {
    public int insertStock(String stockSym, Stock stock);
    public int removeStock(String stockSym,Stock stock);
    public Stock getStock(String stockSym);
    public HashMap<String,Stock> getAllStock();
}
