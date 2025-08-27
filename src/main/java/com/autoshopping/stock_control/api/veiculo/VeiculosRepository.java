package com.autoshopping.stock_control.api.veiculo;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface VeiculosRepository extends JpaRepository<Veiculos, String> {


    Optional<Veiculos> getVeiculosByPlaca(String placa);

	Optional<Veiculos> findById(Integer id);

	Iterable<Veiculos> findByUnidade(String unidade);

	Iterable<Veiculos> findByModelo(String modelo);

	Optional<Veiculos> findByPlaca(String placa);

    void deleteByPlaca(String placa);
};
