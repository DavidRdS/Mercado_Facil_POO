package com.mercadofacil.service;

import com.mercadofacil.model.Produto;
import com.mercadofacil.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository repo;

    public ProdutoService(ProdutoRepository repo) {
        this.repo = repo;
    }

    public List<Produto> listarAtivos() {
        return repo.findByAtivoTrue();
    }

    public List<Produto> listarTodos() {
        return repo.findAll();
    }

    public Produto salvar(Produto p) {
        return repo.save(p);
    }

    public Optional<Produto> buscar(Long id) {
        return repo.findById(id);
    }

    public void remover(Long id) {
        repo.deleteById(id);
    }
}
