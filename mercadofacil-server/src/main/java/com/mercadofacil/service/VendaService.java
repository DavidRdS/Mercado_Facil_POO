package com.mercadofacil.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mercadofacil.repository.ProdutoRepository;
import com.mercadofacil.repository.VendaRepository;
import com.mercadofacil.model.ItemVenda;
import com.mercadofacil.model.Produto;
import com.mercadofacil.model.Venda;

@Service
public class VendaService {

    private final VendaRepository vendaRepo;
    private final ProdutoRepository produtoRepo;

    public VendaService(VendaRepository vendaRepo, ProdutoRepository produtoRepo) {
        this.vendaRepo = vendaRepo;
        this.produtoRepo = produtoRepo;
    }

    @Transactional
    public Venda registrarVenda(Venda venda) {
        // aplica estoque
        for (ItemVenda item : venda.getItens()) {
            Long pid = item.getProduto().getId();
            Produto p = produtoRepo.findById(pid)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + pid));
            if (p.getEstoque() < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para produto: " + p.getNome());
            }
            p.setEstoque(p.getEstoque() - item.getQuantidade());
            produtoRepo.save(p);
        }
        return vendaRepo.save(venda);
    }
}
