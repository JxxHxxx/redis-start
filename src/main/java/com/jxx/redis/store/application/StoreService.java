package com.jxx.redis.store.application;

import com.jxx.redis.store.domain.Store;
import com.jxx.redis.store.domain.StoreRepository;
import com.jxx.redis.store.dto.StoreForm;
import com.jxx.redis.store.dto.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public void create(StoreForm form) {
        Store store = new Store(form.name());
        storeRepository.save(store);
    }

    public List<StoreResponse> readAll() {
        List<Store> stores = storeRepository.findAll();

        return stores.stream().map(s -> new StoreResponse(
                s.getId(),
                s.getName())).toList();
    }
}
