package io.github.varyans.credit.loan.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record CreateLoanResponse(Long id, boolean paid, BigDecimal amount, LocalDate createDate, List<LoanInstallmentEntity> installments) {
}
