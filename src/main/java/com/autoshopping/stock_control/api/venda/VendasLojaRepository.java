package com.autoshopping.stock_control.api.venda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendasLojaRepository extends JpaRepository<Vendas, Integer> {

    Iterable<Vendas> findByUnidade(String unidade);

    Optional<Vendas> findByPlacaAndUnidade(String placa, String unidade);

    Optional<Vendas> findByIdAndUnidade(Integer id, String unidade);
}