package io.github.varyans.credit;

import org.springframework.boot.SpringApplication;

public class TestCreditApplication {

    public static void main(String[] args) {
        SpringApplication.from(CreditApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
