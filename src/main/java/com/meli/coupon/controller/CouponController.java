package com.meli.coupon.controller;

import com.meli.coupon.exception.EmptyItemIdsException;
import com.meli.coupon.exception.IncorrectAmountException;
import com.meli.coupon.model.CouponRequest;
import com.meli.coupon.model.OptionalSolution;
import com.meli.coupon.usecase.CalculateOptimalSolutionUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CouponController {

    private final CalculateOptimalSolutionUseCase calculateOptimalSolutionUseCase;

    public CouponController(final CalculateOptimalSolutionUseCase calculateOptimalSolutionUseCase) {
        this.calculateOptimalSolutionUseCase = calculateOptimalSolutionUseCase;
    }

    @PostMapping("/coupon")
    public ResponseEntity getOptimalSolution(
            @RequestBody CouponRequest body
    ) {
        if(body.getItemIds() == null || body.getItemIds().isEmpty()) {
            throw new EmptyItemIdsException("Must have at least one item id.");
        }
        if(body.getAmount() == null || body.getAmount() <= 0) {
            throw new IncorrectAmountException("Must have amount greater than zero");
        }

        OptionalSolution optimalSolution =
                calculateOptimalSolutionUseCase.getOptimalSolution(body.getItemIds(), body.getAmount());
        return ResponseEntity.ok(optimalSolution);
    }

}
