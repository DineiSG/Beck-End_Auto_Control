package com.autoshopping.stock_control.api.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseConnectionMonitor {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionMonitor.class);
    private final HikariDataSource hikariDataSource;

    public DatabaseConnectionMonitor(DataSource dataSource){
        if (dataSource instanceof HikariDataSource){
            this.hikariDataSource = (HikariDataSource) dataSource;
        }else{
            throw new IllegalArgumentException("A aplicação deve usar HikariCP como DataSource");
        }
    }

    @Scheduled(fixedDelay = 300000) //Verifica a conexao a cada 5 minutos
    public void checkDatabaseConnection(){
        try (Connection connection = hikariDataSource.getConnection()){
            if (connection.isValid(2)){
                logger.info("Connexão com o banco de dados está ativa.");
            }else{
                logger.warn("Conexão com o banco de dados está inativa");
            }
        }catch (SQLException e){
            logger.error("Erro ao verificar a conexão com o banco de dados: ", e);
        }


    }
}
