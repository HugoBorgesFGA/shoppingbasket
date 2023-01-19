package com.interview.shoppingbasket.promotions;

import com.interview.shoppingbasket.BasketItem;
import com.interview.shoppingbasket.Promotion;

public class PercentageOffPromotion extends Promotion {
    private final double percentageOff;

    public PercentageOffPromotion(String productCode, double percentageOff) {
        super(productCode);
        this.percentageOff = percentageOff;
    }

    @Override
    public double computeDiscount(BasketItem basketItem) {
        final double totalPrice = basketItem.getQuantity() * basketItem.getProductRetailPrice();
        return (percentageOff/100.0) * totalPrice;
    }

    @Override
    public String getDescription() {
        return String.format("%.2f percent off in %s", percentageOff, getProductCode());
    }
}
