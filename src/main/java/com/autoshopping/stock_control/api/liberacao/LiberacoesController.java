package com.autoshopping.stock_control.api.liberacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/liberacoes")
public class LiberacoesController {

    @Autowired
    private LiberacoesService service;

    @GetMapping
    public ResponseEntity<Iterable<Liberacoes>> get() {
        return ResponseEntity.ok(service.getLiberacoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Integer id) {
        Optional<Liberacoes> liberacoes = service.getLiberacoesById(id);
        return liberacoes
                .map(Liberacao -> ResponseEntity.ok(liberacoes))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity getLiberacoesByPlaca(@PathVariable("placa") String placa) {
        Optional<Liberacoes> liberacao = service.getLiberacoesByPlaca(placa);
        return liberacao
                .map(Liberacoes -> ResponseEntity.ok(liberacao))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/motivo/{motivo}")
    public Optional<Liberacoes> getLiberacoesByMotivo(@PathVariable("motivo") String motivo) {
        return service.getLiberacoesByMotivo(motivo);
    }


    @PostMapping
    public ResponseEntity post(@RequestBody Liberacoes liberacoes) {
        Liberacoes novo = service.insert(liberacoes);
        return ResponseEntity.ok("Liberacao realizada.");
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity put(@PathVariable("id") Integer id, @RequestBody Liberacoes liberacoes) {
        Liberacoes atualizarLiberacao = service.update(liberacoes, id);
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Liberacao atualizada");
        response.put("liberacoes", atualizarLiberacao);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        boolean ok = service.delete(id);
        return ok ?
                ResponseEntity.ok("Liberacao excluida com sucesso") :
                ResponseEntity.notFound().build();

    }
}