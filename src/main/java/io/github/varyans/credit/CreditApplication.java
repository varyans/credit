package io.github.varyans.credit;

import jakarta.annotation.PostConstruct;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserProfileResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class CreditApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditApplication.class, args);
    }



}
