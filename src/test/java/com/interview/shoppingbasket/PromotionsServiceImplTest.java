package com.interview.shoppingbasket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PromotionsServiceImplTest {

    @Mock
    Promotion promotionForProductA;

    @Mock
    Promotion anotherPromotionForProductA;

    @Mock
    Promotion promotionForProductB;

    PromotionsService promotionsService;

    @BeforeEach
    public void beforeEachTest() {
        when(promotionForProductA.getProductCode()).thenReturn("A");
        when(anotherPromotionForProductA.getProductCode()).thenReturn("A");
        when(promotionForProductB.getProductCode()).thenReturn("B");

        Set<Promotion> promotions = new HashSet<>();
        promotions.add(promotionForProductA);
        promotions.add(anotherPromotionForProductA);
        promotions.add(promotionForProductB);

        promotionsService = new PromotionsServiceImpl(promotions);
    }

    @Test
    public void promotionServiceSelectsCumulativePromotions() {
        final Basket basket = new Basket();
        basket.add("A", "Product A", 1);

        final List<Promotion> promotions = promotionsService.getPromotions(basket);

        assertEquals(2, promotions.size());
        assertTrue(promotions.contains(promotionForProductA));
        assertTrue(promotions.contains(anotherPromotionForProductA));
    }

    @Test
    public void promotionServiceSelectsPertinentPromotions() {
        final Basket basket = new Basket();
        basket.add("B", "Product B", 1);

        final List<Promotion> promotions = promotionsService.getPromotions(basket);

        assertEquals(1, promotions.size());
        assertTrue(promotions.contains(promotionForProductB));
    }

    @Test
    public void promotionServiceSelectsPertinentPromotionsForMultipleProducts() {
        final Basket basket = new Basket();
        basket.add("A", "Product A", 1);
        basket.add("B", "Product B", 1);

        final List<Promotion> promotions = promotionsService.getPromotions(basket);

        assertEquals(3, promotions.size());
        assertTrue(promotions.contains(promotionForProductA));
        assertTrue(promotions.contains(anotherPromotionForProductA));
        assertTrue(promotions.contains(promotionForProductB));
    }
}