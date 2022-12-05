package com.stockManager.nearaTask.services;

import com.stockManager.nearaTask.Dao.BalanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class BalanceService {

    @Autowired
    private BalanceDao balanceDao;

    public BigDecimal deposit(BigDecimal cash){
        return balanceDao.addCash(cash);
    }

    public BigDecimal withdraw(BigDecimal cash){
        return balanceDao.removeCash(cash);
    }

    public BigDecimal query(){
        return balanceDao.getBalance();
    }
}
