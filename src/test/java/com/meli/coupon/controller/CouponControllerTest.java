package com.meli.coupon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.coupon.model.CouponRequest;
import com.meli.coupon.model.OptionalSolution;
import com.meli.coupon.usecase.CalculateOptimalSolutionUseCase;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CouponController.class)
@RunWith(SpringRunner.class)
public class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CalculateOptimalSolutionUseCase calculateOptimalSolutionUseCase;

    @Test
    public void whenPassInvalidListItemIdsReturnBadRequest() throws Exception {
        final CouponRequest couponRequest = CouponRequest.builder()
                .itemIds(new ArrayList<>())
                .amount(6000f)
                .build();

        mockMvc.perform(post("/coupon")
                .content(objectMapper.writeValueAsString(couponRequest))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPassInvalidAmountReturnBadRequest() throws Exception {
        final CouponRequest couponRequest = CouponRequest.builder()
                .itemIds(Arrays.asList("MLA635957485", "MLA821631446", "MLA898568819"))
                .amount(0f)
                .build();

        mockMvc.perform(post("/coupon")
                .content(objectMapper.writeValueAsString(couponRequest))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenValidInputThenReturnsOptimalSolution() throws Exception {
        final Float expectedTotal = 555f;
        List<String> expectedItemIds = Arrays.asList("MLA635957485", "MLA821631446");
        final OptionalSolution optionalSolutionExpected = OptionalSolution.builder()
                .total(expectedTotal)
                .itemIds(expectedItemIds)
                .build();

        final CouponRequest couponRequest = CouponRequest.builder()
                .itemIds(Arrays.asList("MLA635957485", "MLA821631446", "MLA898568819"))
                .amount(6000f)
                .build();

        Mockito.when(calculateOptimalSolutionUseCase.getOptimalSolution(
                couponRequest.getItemIds(),
                couponRequest.getAmount())
        ).thenReturn(optionalSolutionExpected);

        mockMvc.perform(post("/coupon")
                .content(objectMapper.writeValueAsString(couponRequest))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(expectedTotal))
                .andExpect(jsonPath("$.item_ids.length()").value(2));

    }

}