package org.lk.mysecondspringproject.repository;

import org.lk.mysecondspringproject.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, String> {
}
