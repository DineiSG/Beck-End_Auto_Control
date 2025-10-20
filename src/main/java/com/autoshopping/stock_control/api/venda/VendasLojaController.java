package com.autoshopping.stock_control.api.venda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/vendas/loja")
public class VendasLojaController {

    private static final Logger logger = LoggerFactory.getLogger(VendasLojaController.class);

    @Autowired
    private VendasLojaService service;

    // Buscar todas as vendas da loja
    @GetMapping("/{loja}")
    public ResponseEntity<Iterable<Vendas>> getVendasByLoja(@PathVariable String loja) {
        logger.info("Consulta às vendas da loja {} realizada.", loja);
        return ResponseEntity.ok(service.getVendasByLoja(loja));
    }

    // Buscar venda por placa na loja específica
    @GetMapping("/{loja}/placa/{placa}")
    public ResponseEntity<Vendas> getVendaByPlacaAndLoja(
            @PathVariable String loja,
            @PathVariable String placa) {
        logger.info("Consulta à venda do veículo com placa {} na loja {}.", placa, loja);
        Optional<Vendas> venda = service.getVendaByPlacaAndLoja(placa, loja);
        return venda.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Registrar nova venda na loja
    @PostMapping("/{loja}")
    public ResponseEntity<Map<String, String>> postVendaByLoja(
            @PathVariable String loja,
            @RequestBody Vendas venda) {
        // Garante que a venda seja registrada na loja correta
        venda.setUnidade(loja);
        Vendas novaVenda = service.saveVendaByLoja(venda);
        logger.info("Nova venda registrada na loja {}: {}", loja, novaVenda);

        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Venda registrada com sucesso na loja " + loja);
        return ResponseEntity.ok(response);
    }

    // Atualizar venda por placa na loja
    @PutMapping("/{loja}/placa/{placa}")
    public ResponseEntity<Map<String, Object>> updateVendaByLoja(
            @PathVariable String loja,
            @PathVariable String placa,
            @RequestBody Vendas venda) {
        venda.setUnidade(loja);
        Vendas vendaAtualizada = service.updateVendaByLoja(venda, placa, loja);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Venda atualizada com sucesso na loja " + loja);
        response.put("venda", vendaAtualizada);
        return ResponseEntity.ok(response);
    }

    // Deletar venda por ID na loja
    @DeleteMapping("/{loja}/{id}")
    public ResponseEntity<Map<String, String>> deleteVendaByLoja(
            @PathVariable String loja,
            @PathVariable Integer id) {
        boolean deleted = service.deleteVendaByLoja(id, loja);
        Map<String, String> response = new HashMap<>();
        if (deleted) {
            response.put("mensagem", "Venda deletada com sucesso da loja " + loja);
            return ResponseEntity.ok(response);
        } else {
            response.put("erro", "Não foi possível deletar a venda. Verifique se ela pertence à loja " + loja);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }
}
