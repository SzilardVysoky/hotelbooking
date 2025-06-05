package sk.kosickaakademia.hibernatev2.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.hibernatev2.dto.CustomerCreateDTO;
import sk.kosickaakademia.hibernatev2.dto.CustomerUpdateDTO;
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
    public ResponseEntity<Customer> create(@RequestBody CustomerCreateDTO dto) {

        Customer c = new Customer();
        c.setName(dto.getName());
        c.setEmail(dto.getEmail());


        Customer saved = customerService.save(c);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(
            @PathVariable Integer id,
            @RequestBody CustomerUpdateDTO dto
    ) {
        // existing or 404
        Customer existing = customerService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + id));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());

        // persist changes
        Customer updated = customerService.save(existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
