package org.lk.mysecondspringproject.repository;

import org.lk.mysecondspringproject.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {
}
