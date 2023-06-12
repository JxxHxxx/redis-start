package com.jxx.redis.store.presentation;


import com.jxx.redis.store.application.StoreService;
import com.jxx.redis.store.dto.StoreForm;
import com.jxx.redis.store.dto.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/stores")
    public String create(@RequestBody StoreForm form) {
        storeService.create(form);
        return "가게 등록 완료";
    }

    @GetMapping("/stores")
    public ResponseEntity<List<StoreResponse>> readAll() {
        List<StoreResponse> response = storeService.readAll();

        return ResponseEntity.ok(response);

    }
}
