package com.lock.test.demo.home;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor

public class HomeController {
    private final HomeService homeService;

    @GetMapping("/api/decrease")
    public String decreasePrice(@RequestParam(value = "name") String name,
                                @RequestParam(value = "price") int price) {
        String result;
        try {
            homeService.decreasePrice(name, price);
            result = "현재 가격 : " + homeService.currentPrice(name);
        } catch (Exception e) {
            result = e.getMessage();
        }
        log.info(result);
        return result;
    }
}