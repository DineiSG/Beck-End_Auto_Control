package com.autoshopping.stock_control.api.acesso;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface AcessosRepository extends CrudRepository<Acessos, Integer> {

    Iterable<Acessos> getAcessosByPlaca(String placa);

}

