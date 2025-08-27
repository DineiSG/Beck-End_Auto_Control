package com.autoshopping.stock_control.api.venda;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "venda")
public class Vendas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "data_registro")
    private Timestamp dataRegistro;
    private String unidade;
    private String placa;
    private String marca;
    private String modelo;
    private String cor;
    private String renavam;
    private String vendedor;
    private String comprador;
    @Column(name = "valor_venda")
    private String valorVenda;
    @Column(name = "tipo_venda")
    private String tipoVenda;
    private String instituicao;
    @Column(name = "valor_fipe")
    private String valorFipe;
    @Column(name = "valor_entrada")
    private String valorEntrada;
    @Column(name = "valor_financiamento")
    private String valorFinanciamento;
    private String observacoes;
    private String bairro;
    private String cep;
    private String cidade;
    private String cpf;
    private String email;
    private String endereco;
    private String uf;
    private String nascimento;
    private String rg;
    private String rua;
    private String telefone;
}
