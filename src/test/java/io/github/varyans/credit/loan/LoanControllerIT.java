package io.github.varyans.credit.loan;


import io.github.varyans.credit.loan.model.CreateLoanRequest;
import io.github.varyans.credit.loan.model.EnumInstallment;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanControllerIT {

   @LocalServerPort
    private int port;

   @Autowired
   private TestRestTemplate restTemplate;

    @Test
    void invalid_customerId_provided_by_admin_user_should_return_bad_request() {
        String token = getAdminToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        CreateLoanRequest request = CreateLoanRequest.builder()
                .customerId("1")
                .installment(EnumInstallment.i6)
                .rate(0.4)
                .amount(BigDecimal.valueOf(1000))
                .build();

        HttpEntity<CreateLoanRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/loan",
                HttpMethod.POST,
                entity,
                String.class
        );
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }

    @Test
    void too_much_amount_asked_for_loan_by_admin_user_should_return_bad_request() {
        String token = getAdminToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        CreateLoanRequest request = CreateLoanRequest.builder()
                .customerId("customer1")
                .installment(EnumInstallment.i6)
                .rate(0.4)
                .amount(BigDecimal.valueOf(1000000))
                .build();

        HttpEntity<CreateLoanRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/loan",
                HttpMethod.POST,
                entity,
                String.class
        );
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }

    private String getAdminToken() {

        try (Keycloak
         keycloak = Keycloak.getInstance(
                "http://localhost:8888",
                "inghubs",
                "admin1",
                "admin1",
                "credit")) {

            return keycloak.tokenManager().getAccessToken().getToken();

        }
    }

}
