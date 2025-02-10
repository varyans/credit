package io.github.varyans.credit.loan;

import io.github.varyans.credit.config.UserPrincipal;
import io.github.varyans.credit.customer.model.Customer;
import io.github.varyans.credit.customer.CustomerService;
import io.github.varyans.credit.loan.model.CreateLoanRequest;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanService {

    private final CustomerService customerService;

    public LoanService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Object createLoan(CreateLoanRequest request, UserPrincipal principal) {
        if (!principal.getIsAdmin() && !principal.getUsername().equalsIgnoreCase(request.customerId())) {
            throw new IllegalArgumentException("You are not authorized to create loan for another user");
        }
        Customer customer = upsertCustomer(request, principal);

        if (request.amount().compareTo(customer.creditLimit().add(customer.usedCreditLimit().negate())) > 0) {
            throw new IllegalArgumentException("Credit limit exceeded");
        }

        return null;
    }

    private Customer upsertCustomer(CreateLoanRequest request, UserPrincipal principal) {
        if (principal.getIsAdmin() && !principal.getUsername().equalsIgnoreCase(request.customerId())){
            return customerService.findCustomer(request.customerId());
        }
        return customerService.upsertCustomer(principal);
    }

}
