package com.autoshopping.stock_control.api.loja;


import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.Optional;


public interface LojasRepository extends CrudRepository<Lojas, String> {

    Optional<Lojas> getLojasById(BigInteger id);

    Optional<Lojas> findByDescricaoIgnoreCase(String descricao);

}
