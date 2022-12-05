package com.stockManager.nearaTask.model;

import org.springframework.web.bind.annotation.ModelAttribute;

import java.math.BigDecimal;

public class Stock {
    private String sym;
    private BigDecimal avgPrice;
    private BigDecimal count;

    public Stock(String sym, BigDecimal avgPrice, BigDecimal count) {
        this.sym = sym;
        this.avgPrice = avgPrice;
        this.count = count;
    }

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }
}
