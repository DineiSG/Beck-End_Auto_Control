package com.autoshopping.stock_control.api.liberacao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "liberacoes")
public class Liberacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "data_registro")
    private Timestamp dataRegistro;
    private String data_cadastro;
    private String marca;
    private String modelo;
    private String cor;
    private String placa;
    private String unidade;
    private String solicitante ;
    private String motivo;
    private String observacoes;
    private String renavan;
    private String ano_fabricacao;
    private String ano_modelo;
}

