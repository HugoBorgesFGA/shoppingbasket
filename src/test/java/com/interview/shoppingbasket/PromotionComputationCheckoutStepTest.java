package com.interview.shoppingbasket;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PromotionComputationCheckoutStepTest {

    @Mock
    PromotionsService promotionsService;

    @Mock
    List<Promotion> promotions;

    @Mock
    Basket basket;

    @Mock
    CheckoutContext checkoutContext;

    @Test
    public void promotionComputationCheckoutStepCallsInterfaceAndSetsContext() {
        when(checkoutContext.getBasket()).thenReturn(basket);
        when(promotionsService.getPromotions(any(Basket.class))).thenReturn(promotions);

        final PromotionComputationCheckoutStep promotionComputationCheckoutStep = new PromotionComputationCheckoutStep(promotionsService);
        promotionComputationCheckoutStep.execute(checkoutContext);

        verify(promotionsService, times(1)).getPromotions(eq(basket));
        verify(checkoutContext, times(1)).setPromotions(eq(promotions));
    }
}