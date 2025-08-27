package com.autoshopping.stock_control.api.inventario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    @Autowired
    private InventarioRepository rep;
    private Iterable<Inventario> optional;
    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }



    public Iterable<Inventario> getInventario() {
        return rep.findAll();
    }


    public Optional<Inventario> getInventarioByIdInventario(Integer id_inventario) {
        return rep.findByIdInventario(id_inventario);
    }

    public Inventario insert(Inventario inventario) {
        return rep.save(inventario);
    }

    public List<Inventario> insertAll(List<Inventario>  inventarios) {
        List<Inventario> savedInventarios = new ArrayList<>();

        for (Inventario inventario : inventarios) {
            Inventario saved = inventarioRepository.save(inventario);
            savedInventarios.add(saved);
        }

        return savedInventarios;
    }

    @Transactional
    public void deleteByIdInventario(Integer id_inventario) {
        rep.deleteByIdInventario(id_inventario);
    }


}
