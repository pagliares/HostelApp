package com.hostelapp.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rodrigo Martins Pagliares.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
