package com.example.eval.repository;

import com.example.eval.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repositorio JPA para consultar items
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByNombreContainingIgnoreCase(String nombre);
}
