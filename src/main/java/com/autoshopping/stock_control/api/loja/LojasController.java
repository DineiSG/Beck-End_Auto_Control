package com.autoshopping.stock_control.api.loja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/lojas")
public class LojasController {

    private static final Logger logger= LoggerFactory.getLogger(LojasController.class);

    @Autowired
    private LojasService service;


    @GetMapping
    public ResponseEntity<Iterable<Lojas>> get(){return ResponseEntity.ok(service.getLojas());}

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Integer id){
        Optional<Lojas> loja=service.getLojasById(id);
        return loja
                .map(Lojas -> ResponseEntity.ok(loja))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity getgetLojasByDescricao(@PathVariable("descricao") String descricao){
        logger.info("Consulta à loja "+descricao+" realizada");
        Optional<Lojas> loja=service.getLojasByDescricao(descricao);
        return loja
                .map(Lojas -> ResponseEntity.ok(loja))
                .orElse(ResponseEntity.notFound().build());
    }

    /*Adicionando uma nova loja*/
    @PostMapping
    public ResponseEntity post (@RequestBody Lojas loja) {
        logger.info("Loja "+loja+" cadastrada.");
        Lojas novaLoja=service.insert(loja);
        return ResponseEntity.ok("Nova loja cadastrada com sucesso. " +novaLoja);
    }

    /*Atualizando uma loja*/
    @PutMapping(path="/{id}")
    public ResponseEntity put(@PathVariable("id") Integer id, @RequestBody Lojas loja){
        Lojas atualizarLojas=service.update(loja, id);
        /* tratando para que a resposta seja um json*/
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Loja atualizada com sucesso.");
        response.put("loja", atualizarLojas);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patch(@PathVariable("id") Integer id, @RequestBody Lojas loja){
        Lojas atualizar=service.update(loja, id);
        return  ResponseEntity.ok("Detalhe atualizado" +atualizar);
    }

    /*Deletando uma loja*/
    @DeleteMapping(path="{id}")
    public ResponseEntity delete (@PathVariable("id") Integer id){
        boolean ok=service.delete(id);
        return ok?
                ResponseEntity.ok("Loja excluida com sucesso"):
                ResponseEntity.notFound().build();
    }
}
