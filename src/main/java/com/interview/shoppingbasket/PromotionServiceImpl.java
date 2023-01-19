package com.interview.shoppingbasket;

import java.util.*;
import java.util.stream.Collectors;

public class PromotionServiceImpl implements PromotionsService {

    private final Map<String, List<Promotion>> promotionsByCode;

    public PromotionServiceImpl(Set<Promotion> promotions) {
        this.promotionsByCode = promotions.stream()
            .collect(Collectors.groupingBy(Promotion::getProductCode));
    }

    @Override
    public List<Promotion> getPromotions(Basket basket) {
        return basket.getItems().stream()
            .map(BasketItem::getProductCode)
            .flatMap(productCode -> Optional.ofNullable(promotionsByCode.get(productCode))
                .stream()
                .flatMap(Collection::stream)
            )
            .collect(Collectors.toList());
    }
}
