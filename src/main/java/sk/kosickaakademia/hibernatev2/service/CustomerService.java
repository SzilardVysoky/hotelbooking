package sk.kosickaakademia.hibernatev2.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import sk.kosickaakademia.hibernatev2.entity.Customer;
import sk.kosickaakademia.hibernatev2.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepo;

    public CustomerService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    public Optional<Customer> findById(Integer id) {
        return customerRepo.findById(id);
    }

    public Customer create(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer update(Integer id, Customer customerDetails) {
        Customer cust = customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + id));
        cust.setName(customerDetails.getName());
        cust.setEmail(customerDetails.getEmail());
        return customerRepo.save(cust);
    }

    public Customer save(Customer customer) {
        return customerRepo.save(customer);
    }

    public void delete(Integer id) {
        customerRepo.deleteById(id);
    }
}
