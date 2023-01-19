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
        final Map<String, BasketItem> basketItemPerCode = new HashMap<>();

        for(BasketItem item: items) {
            final String productCode = item.getProductCode();

            if (basketItemPerCode.containsKey(productCode)) {
                final BasketItem basketItem = basketItemPerCode.get(productCode);
                final int previousQuantity = basketItem.getQuantity();
                basketItem.setQuantity(previousQuantity + item.getQuantity());

                continue;
            }

            final BasketItem basketItem = new BasketItem();
            basketItem.setProductCode(productCode);
            basketItem.setProductName(item.getProductName());
            basketItem.setQuantity(item.getQuantity());

            basketItemPerCode.put(productCode, basketItem);
        }

        this.items = new ArrayList<>(basketItemPerCode.values());
    }
}
