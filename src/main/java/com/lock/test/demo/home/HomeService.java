package com.lock.test.demo.home;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class HomeService {

    private final HomeRepository homeRepository;

    private final int numberId = 1;

    @Transactional
    public int currentPrice(String name) {
        Home home = homeRepository.findByName(name);
        return home.getPrice();
    }

    @Transactional
    public int decreasePrice(String name, int price) {
        Home home = homeRepository.findWithNameForUpdate(name);
        home.decreasePrice(price);
        return home.getPrice();
    }
}