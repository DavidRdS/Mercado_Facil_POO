package com.mercadofacil.controller;

import com.mercadofacil.model.Venda;
import com.mercadofacil.service.VendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    private final VendaService service;

    public VendaController(VendaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Venda venda) {
        try {
            Venda salva = service.registrarVenda(venda);
            return ResponseEntity.status(201).body(salva);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
