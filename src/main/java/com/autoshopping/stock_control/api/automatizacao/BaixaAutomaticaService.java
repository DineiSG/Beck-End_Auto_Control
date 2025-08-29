package com.autoshopping.stock_control.api.automatizacao;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.autoshopping.stock_control.api.baixa.Baixas;
import com.autoshopping.stock_control.api.baixa.BaixasRepository;
import com.autoshopping.stock_control.api.liberacao.Liberacoes;
import com.autoshopping.stock_control.api.liberacao.LiberacoesRepository;
import com.autoshopping.stock_control.api.veiculo.Veiculos;
import com.autoshopping.stock_control.api.veiculo.VeiculosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BaixaAutomaticaService {

    @Autowired
    private LiberacoesRepository liberacoesRepository;
    @Autowired
    private BaixasRepository baixasRepository;
    @Autowired
    private VeiculosRepository veiculosRepository;

    @Scheduled(cron = "00 15 19 * * *")
    @Transactional
    public void processarBaixasAutomaticas(){
        System.out.println("Iniciando processo de baixas automáticas às " + LocalDateTime.now());

        LocalDate hoje = LocalDate.now();
        Timestamp inicioDoDia = Timestamp.valueOf(hoje.atStartOfDay());
        Timestamp fimDoDia = Timestamp.valueOf(hoje.plusDays(1).atStartOfDay().minusNanos(1));
        List<Liberacoes> liberacoes = liberacoesRepository.findByMotivoInAndDataRegistroBetween(
                List.of("DEVOLUCAO", "VENDA", "TRANSFERENCIA"),
                inicioDoDia,
                fimDoDia
        );

        for(Liberacoes liberacao: liberacoes){
            try{
                Veiculos veiculos = veiculosRepository.findByPlaca(liberacao.getPlaca())
                        .orElseThrow(()-> new IllegalArgumentException("Veiculo nao encontrado para a placa: "+ liberacao.getPlaca()));
                //Inserindo os registros na tabela baixas
                Baixas baixa = new Baixas();
                baixa.setPlaca(veiculos.getPlaca());
                baixa.setMarca(veiculos.getMarca());
                baixa.setModelo(veiculos.getModelo());
                baixa.setCor(veiculos.getCor());
                baixa.setRenavan(veiculos.getRenavan());
                baixa.setUnidade(veiculos.getUnidade());
                baixa.setMotivo(liberacao.getMotivo());
                baixa.setDataRegistro(liberacao.getDataRegistro());
                baixa.setData_cadastro(liberacao.getData_cadastro());
                baixa.setObservacoes("Baixa automatica");
                baixasRepository.save(baixa);

                veiculosRepository.deleteByPlaca(veiculos.getPlaca());

                System.out.println("Veículo baixado com sucesso: " + veiculos.getPlaca());
                System.out.println("Processos de baixas automaticas finalizado.");
            }catch(Exception e){
                System.err.println("Erro inesperado durante o processo de baixa automatica: " +e.getMessage());
                throw new RuntimeException("Falha na baixa automática para o veiculo de placa: "+ liberacao.getPlaca(), e);

            }
        }
    }



}
