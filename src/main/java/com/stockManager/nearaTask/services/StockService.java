package com.stockManager.nearaTask.services;

import com.stockManager.nearaTask.Dao.StockDao;
import com.stockManager.nearaTask.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StockService {
    @Autowired
    private StockDao stockDao;

    public int insertStock(String stockSym, Stock stock){
        stockDao.insertStock(stockSym,stock);
        return 1;
    }

    public int removeStock(String stockSym,Stock stock){
        return stockDao.removeStock(stockSym,stock);
    }

    public int updateStock(String stockSym,Stock stock){
        return stockDao.insertStock(stockSym,stock);
    }

    public Stock getStock(String stockSym){
        return stockDao.getStock(stockSym);
    }

    public HashMap<String,Stock> getAllStock(){
        return stockDao.getAllStock();
    }
}
