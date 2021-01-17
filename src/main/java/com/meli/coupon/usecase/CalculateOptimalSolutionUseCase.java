package com.meli.coupon.usecase;

import com.meli.coupon.exception.IncorrectAmountException;
import com.meli.coupon.exception.IncorrectItemPriceException;
import com.meli.coupon.exception.InsufficientAmountException;
import com.meli.coupon.exception.WrongItemIdException;
import com.meli.coupon.model.ItemMeliResponse;
import com.meli.coupon.model.OptionalSolution;
import com.meli.coupon.model.State;
import com.meli.coupon.repository.ItemsMeliRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CalculateOptimalSolutionUseCase {

    private ItemsMeliRepositoryImpl itemsMeliRepositoryImpl;

    public CalculateOptimalSolutionUseCase(ItemsMeliRepositoryImpl itemsMeliRepositoryImpl) {
        this.itemsMeliRepositoryImpl = itemsMeliRepositoryImpl;
    }

    private Map<String, Integer> getItems(final List<String> itemsId) {
        ItemMeliResponse[] allMeliItems = itemsMeliRepositoryImpl.findByIds(itemsId);

        if(!allItemsOk(allMeliItems)) {
            throw new WrongItemIdException("Some item id not exists");
        }

        return Arrays.stream(allMeliItems)
                .collect(Collectors.toMap(
                        i -> i.getBody().getId(),
                        i -> (int) (i.getBody().getPrice() * 100)
                ));
    }

    public OptionalSolution getOptimalSolution(
            final List<String> itemsId,
            final Float amount
    ) {
        Map<String, Integer> items = getItems(itemsId);
        List<String> optionalItemsId = calculate(items, (int) (amount*100));

        if(optionalItemsId.isEmpty()) {
            throw new InsufficientAmountException();
        }

        Float totalAmount = getTotalAmount(items, optionalItemsId);
        return OptionalSolution.builder()
                .itemIds(optionalItemsId)
                .total(totalAmount)
                .build();
    }

    private Float getTotalAmount(final Map<String, Integer> items, final List<String> optionalItemsId) {
        int optimalSum = optionalItemsId.stream().mapToInt(items::get).sum();

        return Float.parseFloat(String.valueOf(optimalSum)) / 100;
    }

    private boolean validateItemsPrice(final Collection<Integer> itemsValues){
        return itemsValues.stream().noneMatch(i -> i == null || i <= 0);
    }

    private boolean allItemsOk(final ItemMeliResponse[] items) {
        return Arrays.stream(items).noneMatch(item -> item.getCode() == 404);
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
