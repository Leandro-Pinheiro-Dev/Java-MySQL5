package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

    // VARIÁVEL ESTÁTICA DE CONEXÃO
    private static Connection conn = null;
    
    // MÉTODO PARA OBTER A CONEXÃO COM O BANCO DE DADOS
    public static Connection getConnection() {
        // VERIFICA SE A CONEXÃO JÁ EXISTE
        if (conn == null) {
            try {
                // CARREGA AS PROPRIEDADES DO ARQUIVO DE CONFIGURAÇÃO DO BANCO
                Properties props = loadProperties();
                String url = props.getProperty("dburl"); // OBTÉM A URL DO BANCO
                conn = DriverManager.getConnection(url, props); // ESTABELECE A CONEXÃO
            }
            catch (SQLException e) {
                throw new DbException(e.getMessage()); // LANÇA EXCEÇÃO PERSONALIZADA EM CASO DE ERRO
            }
        }
        return conn; // RETORNA A CONEXÃO ESTABELECIDA
    }
    
    // MÉTODO PARA FECHAR A CONEXÃO COM O BANCO DE DADOS
    public static void closeConnection() {
        if (conn != null) { // VERIFICA SE A CONEXÃO EXISTE
            try {
                conn.close(); // FECHA A CONEXÃO
            } catch (SQLException e) {
                throw new DbException(e.getMessage()); // LANÇA EXCEÇÃO PERSONALIZADA EM CASO DE ERRO AO FECHAR
            }
        }
    }
    
    // MÉTODO PARA CARREGAR AS PROPRIEDADES DO BANCO A PARTIR DO ARQUIVO
    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties(); // INICIALIZA OBJETO PARA PROPRIEDADES
            props.load(fs); // CARREGA AS PROPRIEDADES DO ARQUIVO db.properties
            return props; // RETORNA AS PROPRIEDADES CARREGADAS
        }
        catch (IOException e) {
            throw new DbException(e.getMessage()); // LANÇA EXCEÇÃO PERSONALIZADA EM CASO DE ERRO DE I/O
        }
    }
    
    // MÉTODO PARA FECHAR UM STATEMENT
    public static void closeStatement(Statement st) {
        if (st != null) { // VERIFICA SE O STATEMENT NÃO É NULO
            try {
                st.close(); // FECHA O STATEMENT
            } catch (SQLException e) {
                throw new DbException(e.getMessage()); // LANÇA EXCEÇÃO PERSONALIZADA EM CASO DE ERRO AO FECHAR
            }
        }
    }

    // MÉTODO PARA FECHAR UM RESULTSET
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) { // VERIFICA SE O RESULTSET NÃO É NULO
            try {
                rs.close(); // FECHA O RESULTSET
            } catch (SQLException e) {
                throw new DbException(e.getMessage()); // LANÇA EXCEÇÃO PERSONALIZADA EM CASO DE ERRO AO FECHAR
            }
        }
    }
}
