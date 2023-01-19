package com.interview.shoppingbasket;

import java.util.List;

public class PromotionComputationCheckoutStep implements CheckoutStep {

    private final PromotionsService promotionsService;

    public PromotionComputationCheckoutStep(PromotionsService promotionsService) {
        this.promotionsService = promotionsService;
    }

    @Override
    public void execute(CheckoutContext checkoutContext) {
        final List<Promotion> applicablePromotions = promotionsService.getPromotions(checkoutContext.getBasket());
        checkoutContext.setPromotions(applicablePromotions);
    }
}
