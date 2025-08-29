package com.autoshopping.stock_control.api.registro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/acessos")
public class RegistroController {

    @Autowired
    private RegistroService service;

    /*Buscando todos os registros de acesso*/
    @GetMapping
    public ResponseEntity<Iterable<Registro>> get() {
        return ResponseEntity.ok(service.getRegistro());
    }

    @GetMapping("/placa/{placa}")
    public Iterable<Registro> getRegistroByPlaca(@PathVariable("placa") String placa) {
        return service.getRegistroByPlaca(placa);
    }

}
