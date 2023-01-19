package com.interview.shoppingbasket.promotions;

import com.interview.shoppingbasket.BasketItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TwoForOnePromotionTest {

    @Mock
    BasketItem basketItem;

    @ParameterizedTest
    @CsvSource(value = {
        "1,0",
        "3,100",
        "5,200",
        "7,300"
    })
    public void twoForOnePromotionComputationIsRight(int amount, double expectedDiscount) {
        when(basketItem.getQuantity()).thenReturn(amount);
        when(basketItem.getProductRetailPrice()).thenReturn(100.0);

        final TwoForOnePromotion twoForOnePromotion = new TwoForOnePromotion("A");
        final double discount = twoForOnePromotion.computeDiscount(basketItem);

        assertEquals("Pay 1 Get 2 A", twoForOnePromotion.getDescription());
        assertEquals(expectedDiscount, discount);
    }
}