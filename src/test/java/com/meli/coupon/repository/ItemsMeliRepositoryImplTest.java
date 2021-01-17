package com.meli.coupon.repository;

import com.meli.coupon.model.ItemMeli;
import com.meli.coupon.model.ItemMeliResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class ItemsMeliRepositoryImplTest {

    private RestTemplate restTemplate;


    @Test
    public void whenPassExistingItemIdsShouldReturnResponse(){
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
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

        List<String> itemIdsExpected = Arrays.asList("MLA898568818","MLA821631446", "MLA635957485");
        Mockito.when(
                restTemplate.getForEntity(
                        "mock-url/items?ids=MLA898568818,MLA821631446,MLA635957485",
                        ItemMeliResponse[].class
                )
        ).thenReturn(ResponseEntity.ok(itemMeliResponse));
        ItemsMeliRepositoryImpl repository = new ItemsMeliRepositoryImpl("mock-url", restTemplate);

        final ItemMeliResponse[] meliResponseReceived = repository.findByIds(itemIdsExpected);

        Assertions.assertEquals(itemIdsExpected.size(), itemMeliResponse.length);

        Assertions.assertEquals(itemMeliResponse[0].getBody().getId(), meliResponseReceived[0].getBody().getId());
        Assertions.assertEquals(itemMeliResponse[0].getBody().getPrice(), meliResponseReceived[0].getBody().getPrice());
        Assertions.assertEquals(itemMeliResponse[0].getBody().getTitle(), meliResponseReceived[0].getBody().getTitle());
        Assertions.assertEquals(itemMeliResponse[0].getCode(), meliResponseReceived[0].getCode());

        Assertions.assertEquals(itemMeliResponse[1].getBody().getId(), meliResponseReceived[1].getBody().getId());
        Assertions.assertEquals(itemMeliResponse[1].getBody().getPrice(), meliResponseReceived[1].getBody().getPrice());
        Assertions.assertEquals(itemMeliResponse[1].getBody().getTitle(), meliResponseReceived[1].getBody().getTitle());
        Assertions.assertEquals(itemMeliResponse[1].getCode(), meliResponseReceived[1].getCode());

        Assertions.assertEquals(itemMeliResponse[2].getBody().getId(), meliResponseReceived[2].getBody().getId());
        Assertions.assertEquals(itemMeliResponse[2].getBody().getPrice(), meliResponseReceived[2].getBody().getPrice());
        Assertions.assertEquals(itemMeliResponse[2].getBody().getTitle(), meliResponseReceived[2].getBody().getTitle());
        Assertions.assertEquals(itemMeliResponse[2].getCode(), meliResponseReceived[2].getCode());
    }


}
