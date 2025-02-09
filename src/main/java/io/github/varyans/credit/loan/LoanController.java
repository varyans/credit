package io.github.varyans.credit.loan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.varyans.credit.config.UserPrincipal;
import io.github.varyans.credit.loan.model.CreateLoanRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class LoanController implements LoanOperation{

    private final ObjectMapper objectMapper;
    private final LoanService loanService;

    public LoanController(ObjectMapper objectMapper, LoanService loanService) {
        this.objectMapper = objectMapper;
        this.loanService = loanService;
    }

    @PostMapping("loan")
    public Object createLoan(@RequestBody CreateLoanRequest request) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal1 = (UserPrincipal) authentication.getPrincipal();
        loanService.createLoan(request, principal1);
        return objectMapper.writeValueAsString(principal1.getCreditLimit());
    }

}
