package com.autoshopping.stock_control.api.venda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendasLojaService {

    @Autowired
    private VendasLojaRepository repository;

    public Iterable<Vendas> getVendasByLoja(String loja) {
        return repository.findByUnidade(loja);
    }

    public Optional<Vendas> getVendaByPlacaAndLoja(String placa, String loja) {
        return repository.findByPlacaAndUnidade(placa, loja);
    }

    public Vendas saveVendaByLoja(Vendas venda) {
        // Validação adicional pode ser adicionada aqui
        return repository.save(venda);
    }

    public Vendas updateVendaByLoja(Vendas venda, String placa, String loja) {
        Optional<Vendas> vendaExistente = repository.findByPlacaAndUnidade(placa, loja);
        if (vendaExistente.isPresent()) {
            Vendas v = vendaExistente.get();
            // Atualize os campos necessários
            v.setComprador(venda.getComprador());
            v.setCpf(venda.getCpf());
            v.setValorVenda(venda.getValorVenda());
            // ... atualize outros campos conforme necessário
            return repository.save(v);
        }
        throw new RuntimeException("Venda não encontrada para atualização na loja " + loja);
    }

    public boolean deleteVendaByLoja(Integer id, String loja) {
        Optional<Vendas> venda = repository.findByIdAndUnidade(id, loja);
        if (venda.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}