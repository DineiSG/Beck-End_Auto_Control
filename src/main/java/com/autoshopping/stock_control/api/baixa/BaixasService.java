package com.autoshopping.stock_control.api.baixa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
@Service
public class BaixasService {

    private static final Logger logger= LoggerFactory.getLogger(BaixasService.class);

    @Autowired
    private BaixasRepository rep;
    private Iterable<Baixas> optional;


    public Optional<Baixas> getBaixasByPlaca(String placa) {
        return rep.findByPlaca(placa);
    }

    public Optional<Baixas> getBaixasByMotivo(String motivo) {
        return rep.findByMotivo(motivo);
    }

    public Optional<Baixas> getBaixasByUnidade(String unidade) {
        return rep.findByUnidade(unidade);
    }

    public Optional<Baixas> getBaixasById(Integer id) {
        return rep.findById(String.valueOf(id));
    }

    public Iterable<Baixas> getBaixas() {
        return rep.findAll();
    }

    public Baixas insert(Baixas baixa) {
        return rep.save(baixa);
    }
}
