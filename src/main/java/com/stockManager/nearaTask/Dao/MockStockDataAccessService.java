package com.stockManager.nearaTask.Dao;

import com.stockManager.nearaTask.model.Stock;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Repository
public class MockStockDataAccessService implements StockDao {
    private static HashMap<String,Stock> dBForStock = new HashMap<>();

    @Override
    public int insertStock(String stockSym,Stock stock){
        dBForStock.put(stockSym,stock);
        return 1;
    }

    @Override
    public int removeStock(String stockSym,Stock stock){
        dBForStock.remove(stockSym,stock);
        return 1;
    }

    @Override
    public Stock getStock(String stockSym){
        return dBForStock.get(stockSym);
    }

    @Override
    public HashMap<String,Stock> getAllStock(){
        return dBForStock;
    }
}
