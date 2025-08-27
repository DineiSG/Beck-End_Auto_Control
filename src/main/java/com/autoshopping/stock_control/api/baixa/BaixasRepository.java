package com.autoshopping.stock_control.api.baixa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaixasRepository extends CrudRepository<Baixas, String> {


    public Optional<Baixas> findByPlaca(String placa);

    public Optional<Baixas> findByMotivo(String motivo);

    public Optional<Baixas> findByUnidade(String unidade) ;
}
