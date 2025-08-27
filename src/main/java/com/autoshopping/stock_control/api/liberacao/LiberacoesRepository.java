package com.autoshopping.stock_control.api.liberacao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LiberacoesRepository extends CrudRepository<Liberacoes, String> {

    Optional<Liberacoes> getLiberacoesByPlaca(String placa);

    Optional<Liberacoes> findByMotivo(String motivo);

    List<Liberacoes> findByMotivoInAndDataRegistro(List<String> motivos, Timestamp dataLiberacao);

    List<Liberacoes> findByMotivoInAndDataRegistroBetween(
            List<String> motivos,
            Timestamp inicio,
            Timestamp fim
    );
}
