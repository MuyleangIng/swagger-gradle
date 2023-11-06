package com.handsome.keo.simplespringswagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
/*@ComponentScan(basePackages = {"com.handsome.keo.simplespringswagger.restcontroller"}) */
public class SimpleSpringSwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleSpringSwaggerApplication.class, args);
    }

}
