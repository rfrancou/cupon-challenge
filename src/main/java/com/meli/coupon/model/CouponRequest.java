package com.meli.coupon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CouponRequest {

    @JsonProperty("item_ids")
    private List<String> itemIds;
    private Float amount;
}
