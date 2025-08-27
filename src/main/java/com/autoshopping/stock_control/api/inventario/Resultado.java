package com.autoshopping.stock_control.api.inventario;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "resultado_inventario", schema = "gestao_de_estoque")
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private Integer id_inventario;
    private String unidade;
    private Timestamp data_abertura;
    private Timestamp data_fechamento;
    private Integer qtd_divergencias ;
    private String acuracidade;
    private String observacoes;
}
