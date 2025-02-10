package io.github.varyans.credit.customer;

import io.github.varyans.credit.config.UserPrincipal;
import io.github.varyans.credit.customer.model.Customer;
import io.github.varyans.credit.customer.model.CustomerEntity;
import org.keycloak.admin.client.Keycloak;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

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
       customerRepository.findById(customer.id())
                .ifPresentOrElse(
                        existingCustomer -> customerRepository.save(existingCustomer.withCreditLimit(customer.creditLimit())),
                        () -> customerRepository.save(CustomerEntity.builder()
                                        .id(customer.id())
                                        .name(customer.name())
                                        .surname(customer.surname())
                                        .creditLimit(customer.creditLimit())
                                        .usedCreditLimit(BigDecimal.ZERO)
                                .build())
                );
        return customerRepository.findById(customer.id())
                .map(customerEntity -> Customer.builder()
                        .id(customerEntity.getId())
                        .name(customerEntity.getName())
                        .surname(customerEntity.getSurname())
                        .creditLimit(customerEntity.getCreditLimit())
                        .usedCreditLimit(customerEntity.getUsedCreditLimit())
                        .build())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public Customer upsertCustomer(UserPrincipal principal) {
        return upsertCustomer(Customer.builder()
                .id(principal.getUsername())
                .name(principal.getFirstName())
                .surname(principal.getLastName())
                .creditLimit(BigDecimal.valueOf(principal.getCreditLimit()))
                .build());
    }

    public void updateUsedCredit(String customerId,BigDecimal newUsedCreditLimit) {
        customerRepository.findById(customerId)
                .ifPresentOrElse(
                        existingCustomer -> customerRepository.save(existingCustomer.withUsedCreditLimit(newUsedCreditLimit)),
                        () -> {
                            throw new IllegalArgumentException("User not found");
                        }
                );
    }
}
