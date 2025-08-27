package com.autoshopping.stock_control.api.acesso;

import com.autoshopping.stock_control.api.baixa.BaixasController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/acessos")
public class AcessosController {

    private static final Logger logger= LoggerFactory.getLogger(AcessosController.class);

    @Autowired
    private AcessosService service;

    /*Buscando todos os registros de entrada e saida*/
    @GetMapping
    public ResponseEntity<Iterable<Acessos>> get() {
        return ResponseEntity.ok(service.getAcessos());
    }

    @GetMapping("/placa/{placa}")
    public Iterable<Acessos>getAcessosByPlaca(@PathVariable ("placa") String placa){
        logger.info("Consulta realizada aos acessos realizados pelo veículo de placa "+placa);
        return service.getAcessosByPlaca(placa);

    }

}
