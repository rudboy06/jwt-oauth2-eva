package com.example.eval.controller;

import com.example.eval.model.Item;
import com.example.eval.repository.ItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemRepository repo;

    public ItemController(ItemRepository repo) {
        this.repo = repo;
    }

    // POST /api/items/search
    @PostMapping("/search")
    public List<Item> search(@RequestBody ItemSearchRequest request) {
        String nombre = request.getNombre();
        if (nombre == null || nombre.trim().isEmpty()) {
            return repo.findAll(); // si no hay filtro, devuelve todo
        }
        return repo.findByNombreContainingIgnoreCase(nombre.trim());
    }
}
