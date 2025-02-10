package io.github.varyans.credit.loan;

import io.github.varyans.credit.loan.model.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
}
