package com.meli.coupon.usecase;

import com.meli.coupon.exception.InsufficientAmountException;
import com.meli.coupon.exception.WrongItemIdException;
import com.meli.coupon.model.ItemMeli;
import com.meli.coupon.model.ItemMeliResponse;
import com.meli.coupon.model.OptionalSolution;
import com.meli.coupon.repository.ItemsMeliRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

public class CalculateOptimalSolutionUseCaseTest {

    @Test
    public void sholdReturnOptimalSolution() throws Exception {

        ItemsMeliRepositoryImpl itemsMeliRepository =
                Mockito.mock(ItemsMeliRepositoryImpl.class);

        final String itemMeliId1 = "MLA898568818";
        ItemMeli itemMeli1 = ItemMeli.builder()
                .id(itemMeliId1)
                .price(48999f)
                .title("Aire Acondicionado Philco Split  Frío/calor 2881 Frigorías  Blanco 220v - 240v Phs32ha3an")
                .build();

        ItemMeliResponse itemMeliResponse1 = ItemMeliResponse.builder()
                .body(itemMeli1)
                .code(200)
                .build();

        final String itemMeliId2 = "MLA821631446";
        ItemMeli itemMeli2 = ItemMeli.builder()
                .id(itemMeliId2)
                .price(5292f)
                .title("Biblioteca 5 Estantes Melamina Centro Estant - Rex")
                .build();

        ItemMeliResponse itemMeliResponse2 = ItemMeliResponse.builder()
                .body(itemMeli2)
                .code(200)
                .build();

        final String itemMeliId3 = "MLA635957485";
        ItemMeli itemMeli3 = ItemMeli.builder()
                .id(itemMeliId3)
                .price(659.99f)
                .title("Cable Adaptador Conversor Hdmi A Vga Con Audio Fact A Y B")
                .build();

        ItemMeliResponse itemMeliResponse3 = ItemMeliResponse.builder()
                .body(itemMeli3)
                .code(200)
                .build();

        ItemMeliResponse[] itemMeliResponse = new ItemMeliResponse[3];

        itemMeliResponse[0] = itemMeliResponse1;
        itemMeliResponse[1] = itemMeliResponse2;
        itemMeliResponse[2] = itemMeliResponse3;

        List<String> itemIds = Arrays.asList(itemMeliId1, itemMeliId2, itemMeliId3);
        Mockito.when(itemsMeliRepository.findByIds(itemIds)).thenReturn(itemMeliResponse);

        CalculateOptimalSolutionUseCase calculateOptimalSolutionUseCase =
                new CalculateOptimalSolutionUseCase(itemsMeliRepository);

        final Float amount = 6000f;
        OptionalSolution optimalSolution =
                calculateOptimalSolutionUseCase.getOptimalSolution(itemIds, amount);

        Float expectedTotal = 5951.99f;
        List<String> expectedOptimalIds = Arrays.asList(itemMeliId2, itemMeliId3);
        Assertions.assertEquals(expectedTotal, optimalSolution.getTotal());
        Assertions.assertEquals(expectedOptimalIds, optimalSolution.getItemIds());

        Assertions.assertTrue(
                expectedOptimalIds.size() == optimalSolution.getItemIds().size() &&
                        expectedOptimalIds.containsAll(optimalSolution.getItemIds()) &&
                        optimalSolution.getItemIds().containsAll(expectedOptimalIds)
        );
    }

    @Test(expected = WrongItemIdException.class)
    public void whenItemNotExistShouldThrowWrongItemIdException() {
        ItemsMeliRepositoryImpl itemsMeliRepository =
                Mockito.mock(ItemsMeliRepositoryImpl.class);

        final String itemMeliId1 = "MLA898568818";
        ItemMeli itemMeli1 = ItemMeli.builder()
                .id(itemMeliId1)
                .build();

        ItemMeliResponse itemMeliResponse1 = ItemMeliResponse.builder()
                .body(itemMeli1)
                .code(404)
                .build();

        ItemMeliResponse[] itemMeliResponse = new ItemMeliResponse[1];
        itemMeliResponse[0] = itemMeliResponse1;
        List<String> itemIds = Collections.singletonList(itemMeliId1);
        Mockito.when(itemsMeliRepository.findByIds(itemIds)).thenReturn(itemMeliResponse);

        CalculateOptimalSolutionUseCase calculateOptimalSolutionUseCase =
                new CalculateOptimalSolutionUseCase(itemsMeliRepository);

        final Float amount = 500f;

        calculateOptimalSolutionUseCase.getOptimalSolution(itemIds, amount);
    }

    @Test(expected = InsufficientAmountException.class)
    public void whenAmountIsInsufficientShouldThrowInsufficientAmountException() {
        ItemsMeliRepositoryImpl itemsMeliRepository =
                Mockito.mock(ItemsMeliRepositoryImpl.class);

        final String itemMeliId1 = "MLA898568818";
        ItemMeli itemMeli1 = ItemMeli.builder()
                .id(itemMeliId1)
                .price(48999f)
                .title("Aire Acondicionado Philco Split  Frío/calor 2881 Frigorías  Blanco 220v - 240v Phs32ha3an")
                .build();

        ItemMeliResponse itemMeliResponse1 = ItemMeliResponse.builder()
                .body(itemMeli1)
                .code(200)
                .build();

        ItemMeliResponse[] itemMeliResponse = new ItemMeliResponse[1];
        itemMeliResponse[0] = itemMeliResponse1;
        List<String> itemIds = Collections.singletonList(itemMeliId1);
        Mockito.when(itemsMeliRepository.findByIds(itemIds)).thenReturn(itemMeliResponse);

        CalculateOptimalSolutionUseCase calculateOptimalSolutionUseCase =
                new CalculateOptimalSolutionUseCase(itemsMeliRepository);

        final Float amount = 6000f;

        calculateOptimalSolutionUseCase.getOptimalSolution(itemIds, amount);
    }
}