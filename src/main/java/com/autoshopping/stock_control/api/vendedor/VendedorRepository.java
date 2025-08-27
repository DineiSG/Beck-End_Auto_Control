package com.autoshopping.stock_control.api.vendedor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface VendedorRepository extends CrudRepository<Vendedor, String> {

    @Transactional
    void deleteByNome(String nome);

    Optional<Vendedor> findByNome(String nome);

    Optional<Vendedor> findByUnidade(String unidade);

}
