package io.github.varyans.credit.customer.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Customer(String id, String name, String surname, BigDecimal creditLimit, BigDecimal usedCreditLimit) {
}
