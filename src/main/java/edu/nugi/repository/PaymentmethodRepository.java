package edu.nugi.repository;

import edu.nugi.entity.CategoryEntity;
import edu.nugi.entity.PaymentmethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentmethodRepository extends JpaRepository<PaymentmethodEntity, Integer> {
}
