package com.stockManager.nearaTask.Dao;

import java.math.BigDecimal;

public interface BalanceDao {
    BigDecimal addCash(BigDecimal cash);
    BigDecimal removeCash(BigDecimal cash);
    BigDecimal getBalance();
}
