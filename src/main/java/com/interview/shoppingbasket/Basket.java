package com.interview.shoppingbasket;

import java.util.*;
import java.util.stream.Collectors;

public class Basket {
    private List<BasketItem> items = new ArrayList<>();

    public void add(String productCode, String productName, int quantity) {
        BasketItem basketItem = new BasketItem();
        basketItem.setProductCode(productCode);
        basketItem.setProductName(productName);
        basketItem.setQuantity(quantity);

        items.add(basketItem);
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void consolidateItems() {
        final Map<String, List<BasketItem>> basketItemsPerCode = items.stream()
            .collect(Collectors.groupingBy(BasketItem::getProductCode));

        this.items = basketItemsPerCode.values().stream()
            .map(siblingItems -> siblingItems.stream()
                .reduce((basketItem, basketItem2) -> {
                    final BasketItem result = new BasketItem();

                    // The following could be extracted to an auxiliary clone method in BasketItem
                    // keeping it here to strictly follow the problem requirements
                    result.setProductCode(basketItem2.getProductCode());
                    result.setProductName(basketItem2.getProductName());
                    result.setProductRetailPrice(basketItem2.getProductRetailPrice());
                    result.setQuantity(basketItem.getQuantity() + basketItem2.getQuantity());

                    return result;
                }))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }
}
