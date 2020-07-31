package com.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/31 9:49 上午
 * <p>
 */
//标识为SpringBoot启动类
@SpringBootApplication
//Mybatis要扫描Mapper接口的包
@MapperScan("com.mp.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}