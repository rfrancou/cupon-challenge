package com.meli.coupon.repository;

import com.meli.coupon.model.ItemMeliResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("Items")
public interface ItemsMeliRepository {

    ItemMeliResponse[] findByIds(final List<String> itemIds);
}
