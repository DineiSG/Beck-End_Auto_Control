package com.autoshopping.stock_control.api.loja;

import com.autoshopping.stock_control.api.veiculo.VeiculosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class LojasService {

    @Autowired
    private LojasRepository rep;

    @Autowired
    private VeiculosRepository veiculosRepository;

    public Iterable<Lojas> getLojas(){return rep.findAll(); }

    public Optional<Lojas> getLojasById(Integer id) {return rep.getLojasById(BigInteger.valueOf(id)); }

    public Optional<Lojas> getLojasByDescricao(String descricao) { return rep.findByDescricaoIgnoreCase(descricao);}

    /*Metodo para cadastrar uma nova loja*/
    public Lojas insert(Lojas loja) {
        return rep.save(loja);
    }

    /*Metodo para atualizar uma loja*/
    public Lojas update(Lojas loja, Integer id){
        Optional<Lojas>optional=getLojasById(id);
        if(optional.isPresent()){
            Lojas lojas=optional.get();
            rep.save(loja);

            return loja;
        }else{
            throw new RuntimeException("Nao foi possivel atualizar o registro");
        }
    }

    /*Metodo para deletar uma loja*/
    public boolean delete(Integer id){
        if(getLojasById(id).isPresent()){
            rep.deleteById(String.valueOf(id));
            return true;
        }
        return false;
    }


}
