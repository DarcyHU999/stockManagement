package com.stockManager.nearaTask.controllers;

import com.stockManager.nearaTask.model.Balance;
import com.stockManager.nearaTask.services.BalanceService;
import com.stockManager.nearaTask.tools.Code;
import com.stockManager.nearaTask.tools.Result;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/balance")
public class BalanceController {
    @Autowired
    BalanceService balanceService;

    @CrossOrigin(origins ="http://localhost:3000")
    @PostMapping("/deposit")
    public Result deposit(@RequestParam("cash") String cash){
        try {
            BigDecimal balance = new BigDecimal(cash);
            balanceService.deposit(balance);
        }catch (Exception e){
            return new Result(Code.SAVE_ERR,balanceService.query().toString(),"Unknown Error");
        }
        System.out.println(balanceService.query().toString());
        return new Result(Code.SAVE_OK,balanceService.query().toString(),"deposit successfully");
    }

    @CrossOrigin(origins ="http://localhost:3000")
    @PostMapping("/withdraw")
    public Result withdraw(@RequestParam("cash") String cash){
        try {
            BigDecimal balance = new BigDecimal(cash);
            balanceService.withdraw(balance);
        }catch (Exception e){
            return new Result(Code.SAVE_ERR,balanceService.query().toString(),"Unknown Error");
        }
        System.out.println(balanceService.query().toString());
        return new Result(Code.SAVE_OK,balanceService.query().toString(),"withdraw successfully");
    }

    @CrossOrigin(origins ="http://localhost:3000")
    @GetMapping("/query")
    public Result query(){
        BigDecimal balance;
        try{
            balance = balanceService.query();
        }catch (Exception e){
            return new Result(Code.GET_ERR,-1,"Unknown Error");
        }
        return new Result(Code.GET_OK,balance.toString() ,"withdraw successfully");

    }

}
