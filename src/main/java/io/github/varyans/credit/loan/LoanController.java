package io.github.varyans.credit.loan;

import io.github.varyans.credit.config.UserPrincipal;
import io.github.varyans.credit.loan.model.CreateLoanRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/loan")
public class LoanController implements LoanOperation{

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public Object createLoan(@RequestBody CreateLoanRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return loanService.createLoan(request, principal);
    }

    @GetMapping
    public Object getLoans() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return loanService.getLoans(principal);
    }

    @GetMapping("/{loanId}")
    public Object getLoan(@PathVariable Long loanId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return loanService.getLoan(loanId,principal);
    }




}
