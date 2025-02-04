package io.github.varyans.credit.loan.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Objects;

@Builder
public record CreateLoanRequest(String customerId,
                                EnumInstallment installment,
                                Double rate,
                                BigDecimal amount) {

    public CreateLoanRequest {
        if (Objects.isNull(customerId)) {
            throw new IllegalArgumentException("CustomerId cannot be null");
        }
        if (Objects.isNull(installment)) {
            throw new IllegalArgumentException("Installment cannot be null");
        }
        if (Objects.isNull(rate)) {
            throw new IllegalArgumentException("Rate cannot be null");
        }
        if (rate < 0.1 || rate > 0.5) {
            throw new IllegalArgumentException("Rate must be between 0.1 and 0.5");
        }
        if (Objects.isNull(amount)) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
    }
}
