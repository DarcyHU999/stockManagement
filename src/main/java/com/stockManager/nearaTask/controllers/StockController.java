package com.stockManager.nearaTask.controllers;


import com.stockManager.nearaTask.model.Stock;
import com.stockManager.nearaTask.services.BalanceService;
import com.stockManager.nearaTask.services.StockService;
import com.stockManager.nearaTask.tools.Code;
import com.stockManager.nearaTask.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    StockService stockService;

    @Autowired
    BalanceService balanceService;

    @CrossOrigin(origins ="http://localhost:3000")
    @GetMapping("/queryprice/{sym}")
    public Result queryPrice(@PathVariable String sym){
        BigDecimal price;
        try {
            yahoofinance.Stock stockQuery = YahooFinance.get(sym);
            price = stockQuery.getQuote().getPrice();
            if(price == null){
                return new Result(Code.GET_ERR,"-1","Sym does not exist");
            }
        }catch (IOException e){
            return new Result(Code.GET_ERR,"-1","IO Exception");
        }
        return new Result(Code.GET_OK, price.toString(),"Query successfully");
    }

    @CrossOrigin(origins ="http://localhost:3000")
    @PostMapping("/buy")
    public Result buyStock(@RequestParam("sym") String sym,@RequestParam("amount") String amountStr){
        BigDecimal price;
        try{
            yahoofinance.Stock stockQuery = YahooFinance.get(sym);
            price = stockQuery.getQuote().getPrice();
            BigDecimal amount = new BigDecimal(amountStr);
            balanceService.withdraw(amount.multiply(price));
            Stock stockFind = stockService.getStock(sym);
            if(stockFind == null){
                stockService.insertStock(sym,new Stock(sym,price,amount));
            }else{
                BigDecimal totalPrice = stockFind.getAvgPrice().multiply(stockFind.getCount()).add(price.multiply(amount));
                BigDecimal avgPrice = totalPrice.divide(amount.add(stockFind.getCount()));
                stockFind.setCount(amount.add(stockFind.getCount()));
                stockFind.setAvgPrice(avgPrice);
                stockService.insertStock(sym,stockFind);
            }
        }catch (IOException e){
            return new Result(Code.SAVE_ERR,balanceService.query().toString(),"IO Exception");
        }
        return new Result(Code.SAVE_OK,balanceService.query().toString(), "successfully buy");
    }

    @CrossOrigin(origins ="http://localhost:3000")
    @PostMapping("/sell")
    public Result sellStock(@RequestParam("sym") String sym,@RequestParam("amount") String amountStr){
        BigDecimal price;
        try{
            yahoofinance.Stock stockQuery = YahooFinance.get(sym);
            price = stockQuery.getQuote().getPrice();
            BigDecimal amount = new BigDecimal(amountStr);
            balanceService.deposit(amount.multiply(price));
            Stock stockFind = stockService.getStock(sym);
            if(stockFind == null){
                return new Result(Code.SAVE_ERR,balanceService.query().toString(),"you do not have this stock");
            }else{
                BigDecimal totalPrice = stockFind.getAvgPrice().multiply(stockFind.getCount()).subtract(price.multiply(amount));
                BigDecimal avgPrice = totalPrice.divide(amount.subtract(stockFind.getCount()));
                stockFind.setCount(amount.add(stockFind.getCount()));
                stockFind.setAvgPrice(avgPrice);
                stockService.insertStock(sym,stockFind);
            }
        }catch (IOException e){
            return new Result(Code.SAVE_ERR,balanceService.query().toString(),"IO Exception");
        }
        return new Result(Code.SAVE_OK,balanceService.query().toString(), "successfully sell");
    }

    @CrossOrigin(origins ="http://localhost:3000")
    @GetMapping("/stockHold")
    public Result stockHold() {
        HashMap<String,Stock> stockHold = stockService.getAllStock();
        ArrayList<String> lst = new ArrayList<>();
        for(String sym: stockHold.keySet()){
            Stock e = stockHold.get(sym);
            String stockStr = "{" +
                    "sym:" + "\'" + e.getSym() +"\'" + "," +
                    "count:" + "\'" + e.getCount() +"\'" + "," +
                    "avg:" + "\'" + e.getAvgPrice() +"\'" +
                    "}";
            lst.add(stockStr);
        }
        String returnStr = Arrays.toString(lst.toArray());
        System.out.println(returnStr);
        return new Result(Code.GET_OK,returnStr,"successfully get all stocks");
    }


}
