package io.github.varyans.credit.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @GetMapping
    public List<String> getCustomer() {
        return List.of("Customer");
    }

    @PostMapping
    public String createCustomer() {
        return "Customer created";
    }

    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable String id) {
        return "Customer updated with id: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable String id) {
        return "Customer deleted with id: " + id;
    }
}
