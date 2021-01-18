package com.meli.coupon.controller;

import com.meli.coupon.exception.EmptyItemIdsException;
import com.meli.coupon.exception.IncorrectAmountException;
import com.meli.coupon.model.CouponRequest;
import com.meli.coupon.model.OptionalSolution;
import com.meli.coupon.usecase.CalculateOptimalSolutionUseCase;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

public class CouponControllerTest {

    @Test(expected = IncorrectAmountException.class)
    public void whenPassInvalidAmountReturnBadRequest() {
        CalculateOptimalSolutionUseCase calculateOptimalSolutionUseCase =
                Mockito.mock(CalculateOptimalSolutionUseCase.class);
        CouponController couponController = new CouponController(calculateOptimalSolutionUseCase);
        final CouponRequest couponRequest = CouponRequest.builder()
                .itemIds(Arrays.asList("MLA635957485", "MLA821631446", "MLA898568819"))
                .amount(0f)
                .build();
        couponController.getOptimalSolution(couponRequest);
    }

    @Test(expected = EmptyItemIdsException.class)
    public void whenPassInvalidListItemIdsReturnBadRequest() {
        CalculateOptimalSolutionUseCase calculateOptimalSolutionUseCase =
                Mockito.mock(CalculateOptimalSolutionUseCase.class);
        CouponController couponController = new CouponController(calculateOptimalSolutionUseCase);
        final CouponRequest couponRequest = CouponRequest.builder()
                .itemIds(new ArrayList<>())
                .amount(6000f)
                .build();
        couponController.getOptimalSolution(couponRequest);
    }

    @Test
    public void whenPassCorrectParamsShouldReturnOptimalSolution() {
        CalculateOptimalSolutionUseCase calculateOptimalSolutionUseCase =
                Mockito.mock(CalculateOptimalSolutionUseCase.class);
        CouponController couponController = new CouponController(calculateOptimalSolutionUseCase);
        final CouponRequest couponRequest = CouponRequest.builder()
                .amount(600f)
                .itemIds(Arrays.asList("123", "456"))
                .build();

        final OptionalSolution optionalSolutionExpected = OptionalSolution.builder()
                .itemIds(Arrays.asList("123"))
                .total(200f)
                .build();

        Mockito.when(
                calculateOptimalSolutionUseCase.getOptimalSolution(
                        couponRequest.getItemIds(),
                        couponRequest.getAmount()
                )
        ).thenReturn(optionalSolutionExpected);

        final OptionalSolution optimalSolutionReceived = couponController.getOptimalSolution(couponRequest);

        Assert.assertEquals(optionalSolutionExpected.getTotal(), optimalSolutionReceived.getTotal());
        Assert.assertEquals(optionalSolutionExpected.getItemIds().get(0), optimalSolutionReceived.getItemIds().get(0));
    }
}