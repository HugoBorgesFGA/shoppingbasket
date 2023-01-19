package com.interview.shoppingbasket.promotions;

import com.interview.shoppingbasket.BasketItem;
import com.interview.shoppingbasket.Promotion;

public class TwoForOnePromotion extends Promotion {

    public TwoForOnePromotion(String productCode) {
        super(productCode);
    }

    @Override
    public double computeDiscount(BasketItem basketItem) {
        final int quantity = basketItem.getQuantity();
        final int freeItems = quantity / 2;

        return freeItems * basketItem.getProductRetailPrice();
    }

    @Override
    public String getDescription() {
        return "Pay 1 Get 2 " + getProductCode();
    }
}
