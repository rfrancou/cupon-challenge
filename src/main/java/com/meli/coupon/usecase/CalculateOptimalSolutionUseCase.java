package com.meli.coupon.usecase;

import com.meli.coupon.exception.IncorrectAmountException;
import com.meli.coupon.exception.IncorrectItemPriceException;
import com.meli.coupon.model.State;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class CalculateOptimalSolutionUseCase {

    private boolean validateItemsPrice(final Collection<Integer> itemsValues){
        return itemsValues.stream().noneMatch(i -> i == null || i <= 0);
    }

    public List<String> calculate(
            final Map<String, Integer> items,
            final Integer amount
    ) {
        if (amount <= 0) {
            throw new IncorrectAmountException("Amount must be greater than zero.");
        }

        if(!validateItemsPrice(items.values())) {
            throw new IncorrectItemPriceException("Some item has incorrect price.");
        }

        State[] states = new State[amount + 1];
        states[0] = new State();
        for (Map.Entry<String,Integer> entry : items.entrySet()) {
            for (int j = amount - entry.getValue(); j >= 0; --j) {
                if (states[j] != null) {
                    State newState = State.builder()
                            .previousState(states[j])
                            .value(entry.getValue())
                            .id(entry.getKey())
                            .build();
                    states[entry.getValue() + j] = newState;
                }
            }
        }

        State s = null;
        for (int i = amount; i >= 0; --i) {
            if (states[i] != null) {
                s = states[i];
                break;
            }
        }

        List<String> optimalItemsIds = new ArrayList<>();

        while (s.getPreviousState() != null) {
            optimalItemsIds.add(s.getId());
            s = s.getPreviousState();
        }
        return optimalItemsIds;
    }
}
