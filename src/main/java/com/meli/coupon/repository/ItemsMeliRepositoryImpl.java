package com.meli.coupon.repository;

import com.meli.coupon.model.ItemMeliResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ItemsMeliRepositoryImpl implements ItemsMeliRepository {

    private final String meliUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public ItemsMeliRepositoryImpl(
            @Value("${meli.url}") String meliUrl,
            RestTemplate restTemplate
    ) {
        this.meliUrl = meliUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public ItemMeliResponse[] findByIds(final List<String> itemIds){

        final String itemIdsParameter = String.join(",", itemIds);
        final String fullItemsUrl = String.format("%s/items?ids=%s", meliUrl, itemIdsParameter);
        ResponseEntity<ItemMeliResponse[]> responseItemsMeli = restTemplate.getForEntity(
                fullItemsUrl,
                ItemMeliResponse[].class
        );

        return responseItemsMeli.getBody();
    }
}
