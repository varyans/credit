package io.github.varyans.credit.loan;

import io.github.varyans.credit.config.UserPrincipal;
import io.github.varyans.credit.loan.model.CreateLoanRequest;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanService {

    public LoanService() {
    }

    public Object createLoan(CreateLoanRequest request, UserPrincipal principal) {
           String userId = Optional.ofNullable(request.customerId()).orElse(principal.getUsername());
           getUser(userId);

        return null;
    }

    @SneakyThrows
    private void getUser(String userId) {
        if (userId.equals("1")) {
            throw new IllegalArgumentException("User not found");
        }
    }
}
