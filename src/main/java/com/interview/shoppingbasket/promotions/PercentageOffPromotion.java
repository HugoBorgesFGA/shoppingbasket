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
    protected double computePromotionPrice(BasketItem basketItem) {
        return 0;
    }
}
