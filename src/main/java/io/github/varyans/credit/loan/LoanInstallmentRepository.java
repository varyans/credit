package io.github.varyans.credit.loan;

import io.github.varyans.credit.loan.model.LoanInstallmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanInstallmentRepository extends JpaRepository<LoanInstallmentEntity, Long> {
}
