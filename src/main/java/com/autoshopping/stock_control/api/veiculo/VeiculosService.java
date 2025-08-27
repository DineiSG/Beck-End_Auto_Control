package com.autoshopping.stock_control.api.veiculo;


import com.autoshopping.stock_control.api.loja.LojasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VeiculosService {

    @Autowired
    private VeiculosRepository rep;

    private static final Logger logger = LoggerFactory.getLogger(VeiculosService.class);


    private Iterable<Veiculos> optional;
    private Integer id_unidade;

    public Iterable<Veiculos> getVeiculos() {
        return rep.findAll();
    }

    public Iterable<Veiculos> getVeiculosByUnidade(String unidade) {
        return rep.findByUnidade(unidade);
    }

    public Iterable<Veiculos> getVeiculosByModelo(String modelo) {
        return rep.findByModelo(modelo);
    }

    Optional<Veiculos> getVeiculosById(Integer id) {
        return rep.findById(id);
    }

    public Optional<Veiculos> getVeiculosByPlaca(String placa) {
        return rep.getVeiculosByPlaca(placa);
    }

    /*Metodo para salvar um veículo*/
    public Veiculos insert(Veiculos veiculo) {return rep.save(veiculo);}

    /*Metodo para atualizar um veículo*/
    public Veiculos update(Veiculos veiculo, String placa) {
        Optional<Veiculos> optional = getVeiculosByPlaca(placa);
        if (optional.isPresent()) {
            Veiculos veiculos = optional.get();
            rep.save(veiculo);
            return veiculo;
        } else {
            throw new RuntimeException("Nao foi possivel atualizar o registro");
        }
    }

    /*Metodo para deletar um veículo*/
    public boolean delete(String placa) {
        if (getVeiculosByPlaca(placa).isPresent()) {
            rep.deleteByPlaca(placa);
            return true;
        }
        return false;
    }

}




	