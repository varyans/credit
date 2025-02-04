package io.github.varyans.credit.loan.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumInstallment {
    @JsonProperty("6") i6(6),
    @JsonProperty("9") i9(9),
    @JsonProperty("12") i12(12),
    @JsonProperty("24") i24(24);

    private final int installment;

    EnumInstallment(int installment) {
        this.installment = installment;
    }


    @JsonCreator
    public static EnumInstallment of(int installment) {
        for (EnumInstallment e : EnumInstallment.values()) {
            if (e.installment == installment) {
                return e;
            }
        }
        throw new IllegalArgumentException("Invalid installment: " + installment);
    }

    @JsonValue
    public int getInstallment() {
        return installment;
    }



}
