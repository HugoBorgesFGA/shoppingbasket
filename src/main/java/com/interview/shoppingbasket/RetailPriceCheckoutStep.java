package com.interview.shoppingbasket;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RetailPriceCheckoutStep implements CheckoutStep {
    private PricingService pricingService;
    private double retailTotal;
    private double discountTotal;

    public RetailPriceCheckoutStep(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @Override
    public void execute(CheckoutContext checkoutContext) {
        Basket basket = checkoutContext.getBasket();
        retailTotal = 0.0;
        discountTotal = 0.0;

        final Map<String, List<Promotion>> promotionsPerProductCode = checkoutContext.getPromotions()
            .stream()
            .collect(Collectors.groupingBy(Promotion::getProductCode));

        for (BasketItem basketItem: basket.getItems()) {
            int quantity = basketItem.getQuantity();
            double price = pricingService.getPrice(basketItem.getProductCode());
            basketItem.setProductRetailPrice(price);
            retailTotal += quantity*price;

            final List<Promotion> applicablePromotions = Optional.ofNullable(promotionsPerProductCode.get(basketItem.getProductCode()))
                .orElse(Collections.emptyList());

            discountTotal += applicablePromotions.stream()
                .mapToDouble(promotion -> applyPromotion(promotion, basketItem))
                .sum();
        }

        checkoutContext.setRetailPriceTotal(retailTotal - discountTotal);
    }

    public double applyPromotion(Promotion promotion, BasketItem item) {
        return promotion.computeDiscount(item);
    }
}
