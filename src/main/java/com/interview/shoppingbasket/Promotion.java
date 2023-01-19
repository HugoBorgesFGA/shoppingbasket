package com.interview.shoppingbasket;

public abstract class Promotion {

    private final String productCode;

    public Promotion(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public double computePriceAfterPromotion(BasketItem basketItem) {
        if (!isApplicable(basketItem)) {
            return basketItem.getProductRetailPrice();
        }

        return computePromotionPrice(basketItem);
    }

    private boolean isApplicable(BasketItem basketItem) {
        return productCode.equals(basketItem.getProductCode());
    }

    protected abstract double computePromotionPrice(BasketItem basketItem);
}
