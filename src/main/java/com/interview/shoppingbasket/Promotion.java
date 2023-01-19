package com.interview.shoppingbasket;

public abstract class Promotion {

    private final String productCode;

    public Promotion(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public abstract double computeDiscount(BasketItem basketItem);
    public abstract String getDescription();
}
