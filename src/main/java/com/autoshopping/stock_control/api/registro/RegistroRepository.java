package com.autoshopping.stock_control.api.registro;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroRepository extends CrudRepository<Registro, Integer> {

    Iterable<Registro> getRegistroByPlaca(String placa);

}

