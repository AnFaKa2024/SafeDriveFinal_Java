package org.SafeDrive.InfraEstrutura;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USUARIO = "rm558274";
    private static final String SENHA = "150805";

    private static final Logger logger = LogManager.getLogger(ConexaoBanco.class);

    public static Connection getConnection() {
        Connection conexao = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            logger.info("Conexão estabelecida com sucesso!");
        } catch (ClassNotFoundException e) {
            logger.error("Driver JDBC não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            logger.error("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
        return conexao;
    }
}
