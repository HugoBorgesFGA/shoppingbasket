package com.interview.shoppingbasket;

import java.util.List;
import java.util.Map;
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

            discountTotal += promotionsPerProductCode.get(basketItem.getProductCode()).stream()
                .mapToDouble(promotion -> applyPromotion(promotion, basketItem))
                .sum();
        }

        checkoutContext.setRetailPriceTotal(retailTotal - discountTotal);
    }

    public double applyPromotion(Promotion promotion, BasketItem item) {
        return promotion.computeDiscount(item);
    }
}
