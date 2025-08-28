package com.autoshopping.stock_control.api.veiculo;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "veiculo", schema = "vaga", uniqueConstraints = {@UniqueConstraint(name = "uk_placa__veiculo", columnNames = {"placa"})})

public class Veiculos extends RepresentationModel<Veiculos> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Timestamp data_registro;
    private Timestamp data_alteracao;
    private String marca;
    private String modelo;
    private String cor;
    private String placa;
    private String ano_fabricacao;
    private String ano_modelo;
    private String renavan;
    private String unidade;
    private String observacao;
    private String fipe;

    public void save(Veiculos veiculo) {
    }

    public boolean isEmpty() {
        return false;
    }

    public Object get() {
        return null;
    }

}