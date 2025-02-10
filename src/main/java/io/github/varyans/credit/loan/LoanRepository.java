package io.github.varyans.credit.loan;

import io.github.varyans.credit.loan.model.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    @Query("SELECT l FROM LoanEntity l WHERE l.customerId = :username OR true = :isAdmin")
    List<LoanEntity> findAllByCustomerIdOrIsAdmin(String username, Boolean isAdmin);
}
