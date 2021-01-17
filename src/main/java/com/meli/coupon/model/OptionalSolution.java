package com.meli.coupon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OptionalSolution {

    @JsonProperty("item_ids")
    private List<String> itemIds;
    private Float total;
}
