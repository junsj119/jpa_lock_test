package com.lock.test.demo.home;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HumanServiceTest {
    @Autowired
    HomeService homeService;

    @Test
    @DisplayName("돈 줄여보기(멀티 스레드) 테스트")
    void decreaseMoneyForMultiThreadTest() throws InterruptedException {
        AtomicInteger successCount = new AtomicInteger();
        int COUNT = 100;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(COUNT);

        for (int i = 0; i < COUNT; i++) {
            service.execute(() -> {
                try {
                    homeService.decreaseMoney("조재영", 1000);
                    successCount.getAndIncrement();
                    System.out.println("성공");
                } catch (ObjectOptimisticLockingFailureException oe) {
                    System.out.println("충돌감지");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();

        assertThat(successCount.get()).isEqualTo(10);
    }
}