package io.github.varyans.credit.loan;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.varyans.credit.loan.model.CreateLoanRequest;
import io.github.varyans.credit.loan.model.EnumInstallment;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanControllerIT {

   @LocalServerPort
    private int port;

   @Autowired
   private TestRestTemplate restTemplate;

    @Test
    void test() {
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

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
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

    @Test
    void name() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        Keycloak keycloak = Keycloak.getInstance(
                "http://localhost:8888",
                "master",
                "keycloak",
                "keycloak",
                "admin-cli");
        List<UserRepresentation> inghubs = keycloak.realm("inghubs").users().list();
        System.out.println(inghubs);
        System.out.println(objectMapper.writeValueAsString(inghubs));
//        keycloak.realm("inghubs").users().list().forEach(System.out::println);
        assertThat(keycloak
                .realm("inghubs").users()).isNotNull();

//
//        ResponseEntity<String> stringResponseEntity = restTemplate
//                .postForEntity("api/v1/loan", CreateLoanRequest.builder()
//                .customerId("1")
//                .installment(EnumInstallment.i6)
//                .rate(0.4)
//                .amount(BigDecimal.valueOf(1000))
//                .build(), String.class);
//
//        assertThat(stringResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
