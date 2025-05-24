package sk.kosickaakademia.hibernatev2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.hibernatev2.entity.Customer;
import sk.kosickaakademia.hibernatev2.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Integer id) {
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        Customer saved = customerService.create(customer);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(
            @PathVariable Integer id,
            @RequestBody Customer details
    ) {
        return ResponseEntity.ok(customerService.update(id, details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
