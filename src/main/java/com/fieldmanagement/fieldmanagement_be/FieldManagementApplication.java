package com.fieldmanagement.fieldmanagement_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class FieldManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(FieldManagementApplication.class, args);
    }

}
