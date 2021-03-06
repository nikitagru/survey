package com.nikitagru.repositories;

import com.nikitagru.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/***
 * Репозиторий клиентов
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
