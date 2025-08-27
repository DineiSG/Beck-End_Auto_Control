package com.autoshopping.stock_control.api.acesso;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AcessosService {

    @Autowired
    private AcessosRepository acessosRepository;

    public Iterable<Acessos> getAcessos(){return acessosRepository.findAll(); }

    public Iterable <Acessos> getAcessosByPlaca(String placa){return  acessosRepository.getAcessosByPlaca(placa);};
}
