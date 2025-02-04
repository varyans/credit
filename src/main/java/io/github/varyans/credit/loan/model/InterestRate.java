package io.github.varyans.credit.loan.model;

public record InterestRate(double rate) {
    public InterestRate {
        if (rate < 0.1 || rate > 0.5) {
            throw new IllegalArgumentException("Interest rate must be between 0.1 and 0.5");
        }
    }
}
