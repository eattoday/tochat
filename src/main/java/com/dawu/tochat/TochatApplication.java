package com.dawu.tochat;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TochatApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TochatApplication.class, args);
        System.out.println("----------------------  启动成功  ----------------------");
    }

}
