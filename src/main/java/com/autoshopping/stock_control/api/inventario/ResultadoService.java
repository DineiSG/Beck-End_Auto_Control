package com.autoshopping.stock_control.api.inventario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResultadoService {

    @Autowired
    private ResultadoRepository rep;
    private Iterable<Resultado> optional;


    public Iterable<Resultado> getResultado() {
        return rep.findAll();
    }

    public Optional<Resultado> getResultadoByUnidade(String unidade) {
        return rep. findByUnidade(unidade);
    }

    public Resultado insert(Resultado resultado) {
        return rep.save(resultado);
    }
}
