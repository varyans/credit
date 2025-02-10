package io.github.varyans.credit.loan;

import io.github.varyans.credit.config.UserPrincipal;
import io.github.varyans.credit.customer.CustomerService;
import io.github.varyans.credit.customer.model.Customer;
import io.github.varyans.credit.loan.model.CreateLoanRequest;
import io.github.varyans.credit.loan.model.CreateLoanResponse;
import io.github.varyans.credit.loan.model.LoanEntity;
import io.github.varyans.credit.loan.model.LoanInstallmentEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class LoanService {

    private final CustomerService customerService;

    private final LoanRepository loanRepository;

    private final LoanInstallmentRepository loanInstallmentRepository;

    public LoanService(CustomerService customerService, LoanRepository loanRepository, LoanInstallmentRepository loanInstallmentRepository) {
        this.customerService = customerService;
        this.loanRepository = loanRepository;
        this.loanInstallmentRepository = loanInstallmentRepository;
    }

    public Object createLoan(CreateLoanRequest request, UserPrincipal principal) {
        if (!principal.getIsAdmin() && !principal.getUsername().equalsIgnoreCase(request.customerId())) {
            throw new IllegalArgumentException("You are not authorized to create loan for another user");
        }
        Customer customer = upsertCustomer(request, principal);

        BigDecimal amount = request.amount();
        if (amount.compareTo(customer.creditLimit().add(customer.usedCreditLimit().negate())) > 0) {
            throw new IllegalArgumentException("Credit limit exceeded");
        }

        customerService.updateUsedCredit(customer.id(), customer.usedCreditLimit().add(amount));

        int installment = request.installment().getInstallment();
        final LoanEntity loanEntity = LoanEntity.builder()
                .customerId(request.customerId())
                .amount(amount)
                .installment(installment)
                .build();
        LoanEntity savedLoan = loanRepository.save(loanEntity);

        final BigDecimal installmentAmount = amount
                .multiply(BigDecimal.ONE.add(BigDecimal.valueOf(request.rate()))).divide(BigDecimal.valueOf(installment), RoundingMode.CEILING);

        final LocalDate now = LocalDate.now();

        final List<LoanInstallmentEntity> loanInstallmentEntities =
                IntStream.rangeClosed(1, installment)
                        .mapToObj(i -> LoanInstallmentEntity.builder()
                                .loanId(savedLoan.getId())
                                .amount(installmentAmount)
                                .dueDate(now.plusMonths(i).withDayOfMonth(1))
                                .createDate(now)
                                .build())
                        .toList();
        List<LoanInstallmentEntity> savedInstallments = loanInstallmentRepository.saveAll(loanInstallmentEntities);


        return CreateLoanResponse.builder()
                .id(savedLoan.getId())
                .paid(false)
                .createDate(now)
                .amount(amount)
                .installments(savedInstallments)
                .build();
    }

    private Customer upsertCustomer(CreateLoanRequest request, UserPrincipal principal) {
        if (principal.getIsAdmin() && !principal.getUsername().equalsIgnoreCase(request.customerId())){
            return customerService.findCustomer(request.customerId());
        }
        return customerService.upsertCustomer(principal);
    }

    public Object getLoans(UserPrincipal principal) {
        return loanRepository.findAllByCustomerIdOrIsAdmin(principal.getUsername(),principal.getIsAdmin());
    }

    public Object getLoan(Long loanId, UserPrincipal principal) {

        return loanInstallmentRepository.findAllByLoanId(loanId);
    }
}
