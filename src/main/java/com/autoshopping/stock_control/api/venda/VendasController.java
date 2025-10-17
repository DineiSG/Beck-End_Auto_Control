package com.autoshopping.stock_control.api.venda;

import com.autoshopping.stock_control.api.veiculo.Veiculos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("api/v1/vendas")
public class VendasController {

    private static final Logger logger = LoggerFactory.getLogger(VendasController.class);

    @Autowired
    private VendasService service;

    @GetMapping
    public ResponseEntity<Iterable<Vendas>> get(){return ResponseEntity.ok(service.getVendas());}

    @GetMapping("/placa/{placa}")
    public ResponseEntity<Vendas> getVendasByPlaca(@PathVariable("placa") String placa){
        logger.info("Consulta à venda do veiculo com a placa {} realizada.", placa);
        Optional<Vendas> venda = service.getVendasByPlaca(placa);
        if (venda.isPresent()){
            return ResponseEntity.ok(venda.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/unidade/{unidade}")
    public Optional<Vendas> getVendasByUnidade(@PathVariable("unidade") String unidade){
        logger.info("Consulta às venda de veiculos da loja {} realizada.", unidade);
        return  service.getVendasByUnidade(unidade);
    }

    @GetMapping("/vendedor/{vendedor}")
    public Optional<Vendas> getVendasByVendedor(@PathVariable("vendedor") String vendedor){
        logger.info("Consulta às vendas realizadas pelo vendedor "+vendedor," realizada");
        return service.getVendasByVendedor(vendedor);
    }

    @PostMapping
    public ResponseEntity post (@RequestBody Vendas venda){
        Vendas nova=service.insert(venda);
        logger.info("Foi realizada uma nova comunicação de venda: "+venda);
        return ResponseEntity.ok("Venda comunicada com sucesso.");
    }

    @PutMapping(path="/placa/{placa}")
    public ResponseEntity put(@PathVariable("placa") String placa, @RequestBody Vendas venda){
        Vendas atualizarVenda=service.update(venda, placa);
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Veiculo atualizado com sucesso");
        response.put("veiculo", atualizarVenda);
        return ResponseEntity.ok(response);
    }



    @DeleteMapping("{id}")
    public ResponseEntity delete (@PathVariable("id") Integer id){
        boolean ok=service.delete(id);
        return ok?
                ResponseEntity.ok("Registro deletado com successo"):
                ResponseEntity.notFound().build();
    }
}

