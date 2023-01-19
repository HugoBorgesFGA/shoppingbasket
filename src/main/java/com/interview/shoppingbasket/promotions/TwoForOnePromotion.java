package com.interview.shoppingbasket.promotions;

import com.interview.shoppingbasket.BasketItem;
import com.interview.shoppingbasket.Promotion;

public class TwoForOnePromotion extends Promotion {

    public TwoForOnePromotion(String productCode) {
        super(productCode);
    }

    @Override
    protected double computePromotionPrice(BasketItem basketItem) {
        return 0;
    }
}
