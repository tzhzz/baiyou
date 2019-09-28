package com.baiyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author shkstart
 * @date 2019/9/28 - 17:59
 */
@SpringBootApplication
@EnableAsync
@EnableDiscoveryClient
@EnableTransactionManagement
public class ByItemService {
    public static void main(String[] args) {
        SpringApplication.run(ByItemService.class,args);
    }
}
