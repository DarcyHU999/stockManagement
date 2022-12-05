package com.stockManager.nearaTask.Dao;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
@Repository
public class MockBalanceDataAccessService implements BalanceDao {
    private static BigDecimal Balance = new BigDecimal("100000");

    @Override
    public BigDecimal addCash(BigDecimal cash){
        Balance = Balance.add(cash);
        return Balance;
    }

    @Override
    public BigDecimal removeCash(BigDecimal cash){
        Balance = Balance.subtract(cash);
        return Balance;
    }
    @Override
    public BigDecimal getBalance(){
        return Balance;
    }
}
