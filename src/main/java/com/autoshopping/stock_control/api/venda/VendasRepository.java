package com.autoshopping.stock_control.api.venda;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendasRepository extends CrudRepository<Vendas, String> {


    Optional<Vendas> findByPlaca(String placa);

    Optional<Vendas> findByVendedor(String vendedor);

    Optional<Vendas> findByUnidade(String unidade);
}

