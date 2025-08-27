package com.autoshopping.stock_control.api.liberacao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LiberacoesService {

    @Autowired
    private LiberacoesRepository rep;
    private Iterable<Liberacoes> optional;


    public Optional<Liberacoes> getLiberacoesByMotivo(String motivo) {
        return rep.findByMotivo(motivo);
    }

    public Iterable<Liberacoes> getLiberacoes() {
        return rep.findAll();
    }

    public Optional<Liberacoes> getLiberacoesById(Integer id) {return rep.findById(String.valueOf(id));}

    public Optional<Liberacoes> getLiberacoesByPlaca(String placa) {return rep.getLiberacoesByPlaca(placa);}

    public Liberacoes insert(Liberacoes liberacoes) {return rep.save(liberacoes); }

    public Liberacoes update(Liberacoes liberacoes, Integer id){
        Optional<Liberacoes>optional=getLiberacoesById(id);
        if(optional.isPresent()){
            Liberacoes liberacao=optional.get();
            rep.save(liberacoes);
            return liberacoes;
        }else{
            throw new RuntimeException("Nao foi possivel atualizar o registro");
        }
    }

    public boolean delete(Integer id){
        if(getLiberacoesById(id).isPresent()){
            rep.deleteById(String.valueOf(id));
            return true;
        }
        return false;
    }
}

