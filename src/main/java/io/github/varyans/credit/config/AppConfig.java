package io.github.varyans.credit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @PostConstruct
    public void getKeycloak() {
        try(Keycloak keycloak = Keycloak.getInstance(
                "http://localhost:8888",
                "master",
                "keycloak",
                "keycloak",
                "admin-cli")) {

            RealmResource realmResource = keycloak.realm("inghubs");
            UsersResource usersResource = realmResource.users();
            usersResource.list().stream()
                    .map(UserRepresentation::getId)
                    .forEach(id -> usersResource.get(id).remove());

            defineUser(keycloak, "admin1", "FirstAdmin1", "LastAdmin1", "admin1@local.test", "admin1", "100000", "ADMIN");
            defineUser(keycloak, "customer1", "FirstCustomer1", "LastCustomer1", "customer1@local.test", "customer1", "100000", "CUSTOMER");
            defineUser(keycloak, "customer2", "FirstCustomer2", "LastCustomer2", "customer2@local.test", "customer2", "1000000", "CUSTOMER");
        }
    }

    private void defineUser(Keycloak keycloak, String username, String firstName, String lastName, String email, String password, String creditLimit, String clientRole) {
        RealmResource realmResource = keycloak.realm("inghubs");
        UsersResource usersResource = realmResource.users();


        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAttributes(Collections.singletonMap("creditLimit", List.of(creditLimit)));

        Response response = usersResource.create(user);
        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        ClientRepresentation app1Client = realmResource.clients() //
                .findByClientId("credit").get(0);

        RoleRepresentation userClientRole = realmResource.clients().get(app1Client.getId()) //
                .roles().get(clientRole).toRepresentation();

        usersResource.get(userId).roles() //
                .clientLevel(app1Client.getId()).add(List.of(userClientRole));

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);

        usersResource.get(userId).resetPassword(passwordCred);
    }
}
