package io.github.varyans.credit.customer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@With
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CustomerEntity {

    @Id
    private String id;
    private String name;
    private String surname;
    private BigDecimal creditLimit;
    private BigDecimal usedCreditLimit;
}
