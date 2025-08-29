package com.autoshopping.stock_control.api.registro;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "acessos")
public class Registro {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="equipamento")
    private String deviceName;

    @Column(name="ip")
    private String ipaddr;

    private String placa;

    @CreationTimestamp
    private Timestamp data_registro;
}
