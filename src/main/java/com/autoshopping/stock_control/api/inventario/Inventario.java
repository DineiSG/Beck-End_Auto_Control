package com.autoshopping.stock_control.api.inventario;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@Entity
@Table(name = "inventario", schema = "gestao_de_estoque")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private Integer idInventario;
    private String unidade;
    private String marca;
    private String modelo;
    private String cor;
    private String placa;
    private String renavan;
    private Timestamp data_registro;
}
