package parcial.backend.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parcial.backend.demo.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
