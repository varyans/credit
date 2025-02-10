package io.github.varyans.credit.customer;

import io.github.varyans.credit.customer.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

}
