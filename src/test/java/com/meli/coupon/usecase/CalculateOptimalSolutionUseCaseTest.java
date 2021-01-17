package com.meli.coupon.usecase;

import com.meli.coupon.exception.IncorrectAmountException;
import com.meli.coupon.exception.IncorrectItemPriceException;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class CalculateOptimalSolutionUseCaseTest {

    @Test
    public void whenPassCorrectItemsAndAmountShouldReturnOptiomalItems() {
        CalculateOptimalSolutionUseCase calculateOptimalSolutionUseCase =
                new CalculateOptimalSolutionUseCase();

        Map<String, Integer> items  = new HashMap<String, Integer>() {{
            put("MLA898568818", 48999);
            put("MLA821631446", 5292);
            put("MLA635957485", 659);
        }};

        final Integer amount = 6000;
        List<String> optimalItemIdsExpected = Arrays.asList("MLA821631446", "MLA635957485");

        List<String> optimalItemIdsReceived = calculateOptimalSolutionUseCase.calculate(items, amount);

        Assertions.assertTrue(
                optimalItemIdsExpected.size() == optimalItemIdsReceived.size() &&
                        optimalItemIdsExpected.containsAll(optimalItemIdsReceived) &&
                        optimalItemIdsReceived.containsAll(optimalItemIdsExpected)
        );
    }

    @Test(expected = IncorrectAmountException.class)
    public void whenAmountIsZeroShouldThrowInsufficientAmountException() {
        CalculateOptimalSolutionUseCase calculateOptimalSolutionUseCase =
                new CalculateOptimalSolutionUseCase();
        final Integer amount = 0;
        Map<String, Integer> items  = new HashMap<String, Integer>() {{
            put("MLA898568818", 48999);
            put("MLA821631446", 5292);
            put("MLA635957485", 659);
        }};

        calculateOptimalSolutionUseCase.calculate(items, amount);
    }

    @Test(expected = IncorrectItemPriceException.class)
    public void whenSomeItemHaveZeroPriceShouldThrowIncorrectPriceException() {
        CalculateOptimalSolutionUseCase calculateOptimalSolutionUseCase =
                new CalculateOptimalSolutionUseCase();
        final Integer amount = 6000;
        Map<String, Integer> items  = new HashMap<String, Integer>() {{
            put("MLA898568818", 48999);
            put("MLA821631446", 0);
            put("MLA635957485", 659);
        }};

        calculateOptimalSolutionUseCase.calculate(items, amount);
    }

}