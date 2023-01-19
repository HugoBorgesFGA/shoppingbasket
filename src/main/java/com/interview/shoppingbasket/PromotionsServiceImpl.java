package com.interview.shoppingbasket;

import java.util.*;
import java.util.stream.Collectors;

public class PromotionsServiceImpl implements PromotionsService {

    // Assumed that a single product code may have more than one applicable promotion
    private Map<String, List<Promotion>> promotionsPerProductCode;

    public PromotionsServiceImpl(Set<Promotion> promotions) {
        this.promotionsPerProductCode = promotions.stream()
            .collect(Collectors.groupingBy(Promotion::getProductCode));
    }

    @Override
    public List<Promotion> getPromotions(Basket basket) {
        return basket.getItems().stream()
            .map(BasketItem::getProductCode)
            .flatMap(productCode -> Optional.ofNullable(promotionsPerProductCode.get(productCode))
                    .stream()
                    .flatMap(Collection::stream))
            .collect(Collectors.toList());
    }
}
