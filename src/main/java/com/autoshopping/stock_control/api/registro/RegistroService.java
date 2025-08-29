package com.autoshopping.stock_control.api.registro;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegistroService {

    @Autowired
    private RegistroRepository registroRepository;

    public Iterable<Registro> getRegistro(){return registroRepository.findAll(); }

    public Iterable <Registro> getRegistroByPlaca(String placa){return  registroRepository.getRegistroByPlaca(placa);};
}
