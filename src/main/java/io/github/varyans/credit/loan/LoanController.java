package io.github.varyans.credit.loan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.varyans.credit.loan.model.CreateLoanRequest;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/v1")
public class LoanController {

    private final ObjectMapper objectMapper;

    public LoanController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping("loan")
    public Object createLoan(@RequestBody CreateLoanRequest request) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal1 = authentication.getPrincipal();
        return objectMapper.writeValueAsString(principal1);
    }

//    @PostMapping("loan")
//    public Object createLoan(){
//        return "request";
//    }

    @SneakyThrows
    @GetMapping("api/test")
    public String loan(UsernamePasswordAuthenticationToken token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return objectMapper.writeValueAsString(authentication);
    }

    @SneakyThrows
    @GetMapping("api/admin")
    public String loan2(UsernamePasswordAuthenticationToken token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return objectMapper.writeValueAsString(authentication);
    }
}
