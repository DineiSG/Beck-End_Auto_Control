package com.autoshopping.stock_control.api.venda;

import com.autoshopping.stock_control.api.veiculo.Veiculos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendasService {

    @Autowired
    private VendasRepository rep;
    private Iterable<Vendas> optional;

    public Iterable<Vendas> getVendas() {return rep.findAll();}

    public Optional<Vendas> getVendasByPlaca(String placa) {return rep.findByPlaca(placa);}

    public Optional<Vendas> getVendasByVendedor(String vendedor) {return rep.findByVendedor(vendedor);}

    public Optional<Vendas> getVendasByUnidade(String unidade) {return rep.findByUnidade(unidade);}

    private Optional<Object> getVendasById(Integer id) {return Optional.of(rep.findById(String.valueOf(id)));  }

    public Vendas insert(Vendas venda) {return rep.save(venda);}

    /*Metodo para atualizar uma venda*/
    public Vendas update(Vendas venda, String placa){
        Optional<Vendas> optional = getVendasByPlaca(placa);
        if (optional.isPresent()) {
            Vendas vendas = optional.get();
            rep.save(venda);
            return venda;
        } else {
            throw new RuntimeException("Nao foi possivel atualizar o registro");
        }
    }

    public boolean delete(Integer id) {
        if(getVendasById(id).isPresent()){
            rep.deleteById(String.valueOf(id));
            return true;
        }
        return false;
    }




}
