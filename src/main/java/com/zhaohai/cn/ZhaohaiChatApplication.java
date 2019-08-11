package com.zhaohai.cn;

import com.zhaohai.cn.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ZhaohaiChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhaohaiChatApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(0, 0);
    }
}
