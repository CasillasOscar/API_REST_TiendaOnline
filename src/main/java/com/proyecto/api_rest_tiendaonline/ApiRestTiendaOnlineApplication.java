package com.proyecto.api_rest_tiendaonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiRestTiendaOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiRestTiendaOnlineApplication.class, args);
    }

}
