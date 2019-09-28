package com.baiyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author shkstart
 * @date 2019/9/28 - 11:25
 */
@SpringBootApplication
@EnableEurekaServer
public class ByRegistry {
    public static void main(String[] args){
        SpringApplication.run(ByRegistry.class,args);
    }
}
