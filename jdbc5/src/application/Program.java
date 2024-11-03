package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import db.DbIntegrityException;

public class Program {

    // ATIVIDADE: O PROGRAMA ESTABELECE UMA CONEXÃO COM O BANCO DE DADOS,
    // EXCLUI UM REGISTRO DA TABELA "DEPARTMENT" E EXIBE O NÚMERO DE LINHAS AFETADAS.
    public static void main(String[] args) {

        Connection conn = null; // INICIALIZAÇÃO DA CONEXÃO COM O BANCO DE DADOS
        PreparedStatement st = null; // INICIALIZAÇÃO DO PREPARED STATEMENT

        try {
            // OBTÉM A CONEXÃO COM O BANCO DE DADOS
            conn = DB.getConnection();
	
            // PREPARA UMA INSTRUÇÃO SQL PARA DELETAR UM DEPARTAMENTO BASEADO NO ID
            st = conn.prepareStatement(
                    "DELETE FROM department " 
                    + "WHERE "
                    + "Id = ?"); // INSTRUÇÃO SQL DE EXCLUSÃO COM PARÂMETRO DE ID

            // DEFINE O VALOR DO PARÂMETRO (ID DO DEPARTAMENTO A SER EXCLUÍDO)
            st.setInt(1, 5); // DEFINE O ID COMO 5

            // EXECUTA A INSTRUÇÃO SQL DE DELEÇÃO
            int rowsAffected = st.executeUpdate();
            
            // EXIBE O NÚMERO DE LINHAS AFETADAS PELA EXCLUSÃO
            System.out.println("Done! Rows affected: " + rowsAffected);
        }
        // CAPTURA EXCEÇÕES DE SQL E LANÇA UMA EXCEÇÃO PERSONALIZADA DE INTEGRIDADE DO BANCO DE DADOS
        catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        } 
        finally {
            // FECHA O STATEMENT E A CONEXÃO COM O BANCO DE DADOS
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}
