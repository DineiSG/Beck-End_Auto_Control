package com.autoshopping.stock_control.api.vendedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendedorService {



    @Autowired
    private VendedorRepository rep;

    public Iterable<Vendedor> getVendedor(){return rep.findAll();};


    public Optional<Vendedor> getVendedorByNome(String nome) {return rep.findByNome(nome);}

    public Optional<Vendedor> getVendedorByUnidade(String unidade) {return rep.findByUnidade(unidade);}


    public Vendedor insert(Vendedor vendedor) {return rep.save(vendedor);}

    public boolean delete(String nome) {
        Optional<Vendedor> vendedor=getVendedorByNome(nome);
        if(vendedor.isPresent()){
            rep.deleteByNome(nome);
            return true;
        }
        return false;
    }
}

