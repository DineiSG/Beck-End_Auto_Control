package com.autoshopping.stock_control.api.inventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/resultado")
public class ResultadoController {

    @Autowired
    private ResultadoService service;

    @GetMapping
    public ResponseEntity<Iterable<Resultado>> get(){return ResponseEntity.ok(service.getResultado());}

    @GetMapping("/unidade/{unidade}")
    public ResponseEntity get(@PathVariable("unidade") String unidade){
        Optional<Resultado> resultado=service.getResultadoByUnidade(unidade);
        return resultado
                .map(Resultado -> ResponseEntity.ok(resultado))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Resultado resultado) {
        Resultado novo=service.insert(resultado);
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Resultado salvo");
        response.put("resultado", novo);
        return ResponseEntity.ok(response);
    }
}
