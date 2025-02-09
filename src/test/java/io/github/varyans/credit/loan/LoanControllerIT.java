package io.github.varyans.credit.loan;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.varyans.credit.loan.model.CreateLoanRequest;
import io.github.varyans.credit.loan.model.EnumInstallment;
import org.junit.jupiter.api.Test;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RealmsResource;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LoanControllerIT {

    private TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth("admin1","admin1");



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
