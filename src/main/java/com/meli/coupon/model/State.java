package com.meli.coupon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class State {

    private State previousState = null;
    private int value = 0;
    private String id;
}
