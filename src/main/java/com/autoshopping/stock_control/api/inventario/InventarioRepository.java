package com.autoshopping.stock_control.api.inventario;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface InventarioRepository extends CrudRepository <Inventario, String> {

    Optional<Inventario> findByIdInventario(Integer id_inventario);


    void deleteByIdInventario(Integer id_inventario);
}
