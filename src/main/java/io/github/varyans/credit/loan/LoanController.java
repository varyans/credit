package io.github.varyans.credit.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.varyans.credit.loan.model.CreateLoanRequest;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class LoanController {

    @PostMapping("loan")
    public Object createLoan(@RequestBody CreateLoanRequest request){
        return request;
    }

//    @PostMapping("loan")
//    public Object createLoan(){
//        return "request";
//    }

    @SneakyThrows
    @GetMapping("api/test")
    public String loan(UsernamePasswordAuthenticationToken token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(authentication);
    }

    @SneakyThrows
    @GetMapping("api/admin")
    public String loan2(UsernamePasswordAuthenticationToken token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(authentication);
    }
}
