package io.github.varyans.credit.customer;

import io.github.varyans.credit.config.UserPrincipal;
import io.github.varyans.credit.customer.model.Customer;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CustomerService {
    public Customer findCustomer(String customerId) {
        try (Keycloak keycloak = Keycloak.getInstance(
                "http://localhost:8888",
                "master",
                "keycloak",
                "keycloak",
                "admin-cli")) {

            Customer customer = keycloak.realm("inghubs")
                            .users()
                            .searchByUsername(customerId,true).stream()
                    //.stream()
//                    .map(UserResource::toRepresentation)
                    .findAny()
                    .map(userRepresentation -> {
                        String username = userRepresentation.getUsername();
                        String firstName = userRepresentation.getFirstName();
                        String lastName = userRepresentation.getLastName();
                        BigDecimal creditLimit = userRepresentation.getAttributes().get("creditLimit").stream().findFirst().map(BigDecimal::new).orElse(BigDecimal.ZERO);
                        return Customer.builder()
                                .id(username)
                                .name(firstName)
                                .surname(lastName)
                                .creditLimit(creditLimit)
                                .build();
                    })
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            return upsertCustomer(customer);

        }
    }

    private Customer upsertCustomer(Customer customer) {

        return null;
    }

    public Customer upsertCustomer(UserPrincipal principal) {
        return null;
    }
}
