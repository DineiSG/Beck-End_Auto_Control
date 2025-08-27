package com.autoshopping.stock_control.api.baixa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/baixas")
public class BaixasController {

    private static final Logger logger= LoggerFactory.getLogger(BaixasController.class);

    @Autowired
    private BaixasService service;

    @GetMapping
    public ResponseEntity <Iterable<Baixas>> get() { return ResponseEntity.ok(service.getBaixas());}

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Integer id){
        Optional<Baixas> baixas=service.getBaixasById(id);
        return baixas
                .map(Baixas -> ResponseEntity.ok(baixas))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/placa/{placa}")
    public Optional<Baixas> getBaixasByPlaca(@PathVariable("placa")String placa){
        return service.getBaixasByPlaca(placa);
    }

    @GetMapping("/motivo/{motivo}")
    public Optional<Baixas> getBaixasByMotivo(@PathVariable("motivo")String motivo){
        return service.getBaixasByMotivo(motivo);
    }

    @GetMapping("/unidade/{unidade}")
    public Optional<Baixas> getBaixasByUnidade(@PathVariable("unidade")String unidade){
        return service.getBaixasByUnidade(unidade);
    }

    @PostMapping
    public ResponseEntity post (@RequestBody Baixas baixa) {
        Baixas novo=service.insert(baixa);
        logger.info("Registro de baixa realizado");
        return ResponseEntity.ok("Baixa realizada com sucesso");
    }



}
