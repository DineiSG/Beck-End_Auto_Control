package com.autoshopping.stock_control.api.inventario;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultadoRepository extends CrudRepository<Resultado, String> {

    Optional<Resultado> findByUnidade(String unidade);

}
