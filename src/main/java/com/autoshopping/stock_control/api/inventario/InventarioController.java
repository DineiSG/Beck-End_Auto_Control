package com.autoshopping.stock_control.api.inventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/inventario")
public class InventarioController {

    @Autowired
    private InventarioService service;

    public InventarioController (InventarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Iterable<Inventario>> get(){return ResponseEntity.ok(service.getInventario());}

    @GetMapping("/id_inventario/{id_inventario}")
    public ResponseEntity get(@PathVariable("id_inventario") Integer id_inventario){
        Optional<Inventario> inventario=service.getInventarioByIdInventario(id_inventario);
        return inventario
                .map(Inventario -> ResponseEntity.ok(inventario))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody List<Inventario> inventarios) {
        List<Inventario> novosInventarios = service.insertAll(inventarios);
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Inventarios criados com sucesso");
        response.put("inventarios", novosInventarios);
        return ResponseEntity.ok(response);
    }
    /*Deletando uma listagem*/
    @DeleteMapping("/id_inventario/{id_inventario}")
    public ResponseEntity<Void> delete(@PathVariable("id_inventario") Integer id_inventario) {
        service.deleteByIdInventario(id_inventario);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content após a exclusão
    }


}


