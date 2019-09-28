package com.baiyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author shkstart
 * @date 2019/9/28 - 16:43
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class ByApiGateway {
    public static void main(String[] args) {
        SpringApplication.run(ByApiGateway.class,args);
    }
}
