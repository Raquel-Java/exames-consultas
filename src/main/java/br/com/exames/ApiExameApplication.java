package br.com.exames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
public class ApiExameApplication {

    /* ------------------------------------------------------------------------------------------------------*/

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApiExameApplication.class);
        application.run(args);
    }

    /* ------------------------------------------------------------------------------------------------------*/

}
