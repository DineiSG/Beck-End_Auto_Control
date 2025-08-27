package com.autoshopping.stock_control.api.loja;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "unidade")
public class Lojas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    private String email;
    private String telefone;
    @Column(name = "qtd_veiculos")
    private String qtdVeiculos;
}
