package com.interview.shoppingbasket.promotions;

import com.interview.shoppingbasket.BasketItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PercentageOffPromotionTest {

    @Mock
    BasketItem basketItem;

    @Test
    public void percentageOffPromotionComputationIsRight() {
        when(basketItem.getQuantity()).thenReturn(3);
        when(basketItem.getProductRetailPrice()).thenReturn(100.0);

        final PercentageOffPromotion fiftyPercentOffInA = new PercentageOffPromotion("A", 50.0);
        final double discount = fiftyPercentOffInA.computeDiscount(basketItem);

        assertEquals("50.00 percent off in A", fiftyPercentOffInA.getDescription());
        assertEquals(discount, 150.0);
    }
}