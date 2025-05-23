package sk.kosickaakademia.hibernatev2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.kosickaakademia.hibernatev2.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // findByEmail later
}
