package com.interview.shoppingbasket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.interview.shoppingbasket.promotions.PercentageOffPromotion;
import com.interview.shoppingbasket.promotions.TwoForOnePromotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class CheckoutPipelineTest {

    CheckoutPipeline checkoutPipeline;

    @Mock
    Basket basket;

    @Mock
    CheckoutStep checkoutStep1;

    @Mock
    CheckoutStep checkoutStep2;

    @Mock
    PricingService pricingService;

    @BeforeEach
    void setup() {
        checkoutPipeline = new CheckoutPipeline();
    }

    @Test
    void returnZeroPaymentForEmptyPipeline() {
        PaymentSummary paymentSummary = checkoutPipeline.checkout(basket);

        assertEquals(paymentSummary.getRetailTotal(), 0.0);
    }

    @Test
    void executeAllPassedCheckoutSteps() {
        // 1st - Basket Consolidation Step
        final BasketConsolidationCheckoutStep basketConsolidationStep = new BasketConsolidationCheckoutStep();

        // 2nd - Promotion Computation Step
        final Set<Promotion> promotions = new HashSet<>();
        promotions.add(new TwoForOnePromotion("A"));
        promotions.add(new PercentageOffPromotion("B", 50));
        promotions.add(new PercentageOffPromotion("C", 10));

        final PromotionsService promotionsService = new PromotionsServiceImpl(promotions);
        final PromotionComputationCheckoutStep promotionStep = new PromotionComputationCheckoutStep(promotionsService);

        // 3rd - Retail Price step
        final RetailPriceCheckoutStep retailPriceStep = new RetailPriceCheckoutStep(pricingService);

        // Pipeline assembly
        checkoutPipeline.addStep(basketConsolidationStep);
        checkoutPipeline.addStep(promotionStep);
        checkoutPipeline.addStep(retailPriceStep);

        // Creating basket and mocking prices on pricingService
        final Basket testBasket = new Basket();
        testBasket.add("A", "Product A", 3);
        when(pricingService.getPrice(eq("A"))).thenReturn(100.0);

        testBasket.add("B", "Product B", 10);
        when(pricingService.getPrice(eq("B"))).thenReturn(10.0);

        testBasket.add("C", "Product C", 10);
        when(pricingService.getPrice(eq("C"))).thenReturn(50.0);

        final PaymentSummary summary = checkoutPipeline.checkout(testBasket);
        final double finalPrice = summary.getRetailTotal();

        /*
         Expecting:

            | Code | Retail Price | Discount Price | Total |
            | A    | 3x100 = 300  | 100            | 200   |
            | B    | 10x10 = 100  | 50             | 50    |
            | C    | 10x50 = 500  | 50             | 450   |

            200 + 50 + 450 = 700
         */
        assertEquals(700, finalPrice);
    }

}
