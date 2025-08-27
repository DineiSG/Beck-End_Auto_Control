package com.autoshopping.stock_control.api.acesso;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "acessos")
public class Acessos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Timestamp data_registro;
    private String equipamento;
    private String ip;
    private String placa;
}
