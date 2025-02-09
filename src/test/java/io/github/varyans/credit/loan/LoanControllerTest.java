package io.github.varyans.credit.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.varyans.credit.loan.model.CreateLoanRequest;
import io.github.varyans.credit.loan.model.EnumInstallment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(LoanController.class)
class LoanControllerTest {

    @MockitoBean
    LoanService loanService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void customerId_null_should_throw_illegalArgumentException() {
        assertThrows(IllegalArgumentException.class,() ->
        mockMvc.perform(post("/api/v1/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CreateLoanRequest.builder()
                                .customerId(null)
                                .installment(EnumInstallment.i6)
                                .rate(0.4)
                                .amount(BigDecimal.valueOf(1000))
                        .build()))));
    }

    @Test
    void installment_null_should_throw_illegalArgumentException() {
        assertThrows(IllegalArgumentException.class,() ->
                mockMvc.perform(post("/api/v1/loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CreateLoanRequest.builder()
                                .customerId("1")
                                .installment(null)
                                .rate(0.4)
                                .amount(BigDecimal.valueOf(1000))
                                .build()))));
    }


    @Test
    void installment_unknown_should_throw_illegalArgumentException() {
        assertThrows(IllegalArgumentException.class,() ->
                mockMvc.perform(post("/api/v1/loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CreateLoanRequest.builder()
                                .customerId("1")
                                .installment(EnumInstallment.of(5))
                                .rate(0.4)
                                .amount(BigDecimal.valueOf(1000))
                                .build()))));
    }

    @Test
    void rate_null_should_throw_illegalArgumentException() {
        assertThrows(IllegalArgumentException.class,() ->
                mockMvc.perform(post("/api/v1/loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CreateLoanRequest.builder()
                                .customerId("1")
                                .installment(EnumInstallment.of(6))
                                .rate(null)
                                .amount(BigDecimal.valueOf(1000))
                                .build()))));
    }

    @Test
    void rate_less_then_point_one_should_throw_illegalArgumentException() {
        assertThrows(IllegalArgumentException.class,() ->
                mockMvc.perform(post("/api/v1/loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CreateLoanRequest.builder()
                                .customerId("1")
                                .installment(EnumInstallment.of(6))
                                .rate(.01)
                                .amount(BigDecimal.valueOf(1000))
                                .build()))));
    }

    @Test
    void rate_higher_then_point_five_should_throw_illegalArgumentException() {
        assertThrows(IllegalArgumentException.class,() ->
                mockMvc.perform(post("/api/v1/loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CreateLoanRequest.builder()
                                .customerId("1")
                                .installment(EnumInstallment.of(6))
                                .rate(.51)
                                .amount(BigDecimal.valueOf(1000))
                                .build()))));
    }

    @Test
    void amount_null_should_throw_illegalArgumentException() {
        assertThrows(IllegalArgumentException.class,() ->
                mockMvc.perform(post("/api/v1/loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CreateLoanRequest.builder()
                                .customerId("1")
                                .installment(EnumInstallment.of(6))
                                .rate(.5)
                                .amount(null)
                                .build()))));
    }
}