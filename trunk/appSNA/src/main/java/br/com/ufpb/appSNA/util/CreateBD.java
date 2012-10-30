package br.com.ufpb.appSNA.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.ufpb.appSNAUtil.util.AppSNALog;

public class CreateBD {

	public static void criar(String nomeBD) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Passo 1: Registrado o JDBC driver: existem duas formas!!!
			Class.forName(BDUtil.JDBC_DRIVER);

			// Paaso 2: Abrindo uma conexao
			AppSNALog.warn("Conectando com o Banco de Dados...");
			conn = DriverManager.getConnection(BDUtil.URL_LOCAL, BDUtil.USER, BDUtil.SENHA);

			// Passo 3: Executando uma query
			stmt = conn.createStatement();

			String sqlBD = "CREATE DATABASE " + nomeBD;
			stmt.executeUpdate(sqlBD);			
			AppSNALog.warn("Database " + nomeBD + " criado com sucesso...");
			
			String sqlUse = "USE " + nomeBD;
			stmt.executeUpdate(sqlUse);
			
			conn.setAutoCommit(false);

			try {
				String sqlTableUsuario = "CREATE  TABLE Usuario (" +
						  "id_usuario BIGINT NOT NULL ," + 
						  "nome VARCHAR(45) NOT NULL ," +
						  "screen_name VARCHAR(40) NOT NULL ," +
						  "biografia TEXT NULL ," +
						  "localizacao VARCHAR(30) NULL ," +
						  "total_following INT NULL ," +
						  "total_followers INT NULL ," +
						  "total_tweets INT NULL ," +
						  "URL TEXT NULL ," + 
						  "timezone VARCHAR(40) NULL ," +
						  "linguagem VARCHAR(40) NULL ," +
						  "data_criacao DATE NULL ," +
						  "url_imagem TEXT NULL ," +
						  "PRIMARY KEY (id_usuario) ," +
						  "UNIQUE INDEX screen_name_UNIQUE (screen_name ASC)" + 
						  ");";
				stmt.executeUpdate(sqlTableUsuario);
				conn.commit();				
				AppSNALog.warn("Tabela usuario criada com sucesso...");
				
			} catch (SQLException se1) {
				conn.rollback();				
				AppSNALog.error("Erro na criacao da tabela usuario: " + se1.getMessage());
			}

			try {
				String sqlTableRelacionamento = "CREATE TABLE Relacionamento (" +
						 " id_relacionamento BIGINT NOT NULL AUTO_INCREMENT, " +
						  "id_souce BIGINT NOT NULL , " +
						  "id_target BIGINT NOT NULL , " +
						  "PRIMARY KEY (id_relacionamento), " +
						  "UNIQUE KEY (id_souce, id_target), " +
						  "INDEX fk_Relacionamento_Usuario2 (id_target ASC), " +
						  "CONSTRAINT fk_Relacionamento_Usuario1 " +
						  "FOREIGN KEY (id_souce) " +
						  "REFERENCES Usuario (id_usuario), " +						  
						  "CONSTRAINT fk_Relacionamento_Usuario2 " +
						  "FOREIGN KEY (id_target) " +
						  "REFERENCES Usuario (id_usuario) " +
						  ");";
				stmt.executeUpdate(sqlTableRelacionamento);
				conn.commit();				
				AppSNALog.warn("Tabela relacionamento criada com sucesso...");
				
			} catch (SQLException se2) {
				AppSNALog.error("Erro na criacao da tabela relacionamento: " + se2.getMessage());
			}
			
			try {
				String sqlTableStatus = "CREATE  TABLE Status (" +
						  "id_status INT NOT NULL AUTO_INCREMENT, " +
						  "id_usuario BIGINT NOT NULL, " +
						  "data_criacao DATE NULL, " +
						  "texto VARCHAR(150) NOT NULL, " +
						  "latitude VARCHAR(20) NULL, " +
						  "longitude VARCHAR(20) NULL, " +
						  "total_retweet INT NULL, " +
						  "is_retweeted INT NULL, " +
						  "PRIMARY KEY (id_status, id_usuario), " +  
						  "CONSTRAINT fk_status_usuario " +
						  "FOREIGN KEY (id_usuario) " +
						  "REFERENCES Usuario(id_usuario) " +
						  ");";
				stmt.executeUpdate(sqlTableStatus);
				conn.commit();
				AppSNALog.warn("Tabela status criada com sucesso...");
			} catch (SQLException se3) {
				conn.rollback();
				AppSNALog.error("Erro na criacao da tabela status: " + se3.getMessage());
			}
			
			
			
			try {
				String sqlTableUrlmention = "CREATE  TABLE URLMention ( " +
						  "id_usuario BIGINT NOT NULL, " +
						  "id_status INT NOT NULL, " +
						  "id_urlmention INT NOT NULL AUTO_INCREMENT, " +
						  "url TEXT NULL, " +
						  "PRIMARY KEY (id_urlmention), " + 
						  "UNIQUE KEY (id_usuario, id_status, id_urlmention), " +						  
						  "CONSTRAINT fk_URLMention_Status " +
						  "FOREIGN KEY (id_status, id_usuario) " +
						  "REFERENCES Status(id_status, id_usuario) " +
						  ");";
				stmt.executeUpdate(sqlTableUrlmention);
				conn.commit();
				AppSNALog.warn("Tabela urlmention criada com sucesso...");
			} catch (SQLException se3) {
				conn.rollback();
				AppSNALog.error("Erro na criacao da tabela urlmention: " + se3.getMessage());
			}
			
			
			
			try {
				String sqlTableUsermention = "CREATE  TABLE UserMention (" +
						  "id_usuario BIGINT NOT NULL, " +
						  "id_status INT NOT NULL, " +
						  "id_usermention INT NOT NULL AUTO_INCREMENT, " +
						  "usuario VARCHAR(45) NULL, " +
						  "PRIMARY KEY (id_usermention), " +
						  "UNIQUE KEY (id_usuario, id_status, id_usermention), " +
						  "CONSTRAINT fk_UserMention_Status " +
						  "FOREIGN KEY (id_status, id_usuario) " +
						  "REFERENCES Status(id_status, id_usuario) " +
						  ");";
				stmt.executeUpdate(sqlTableUsermention);
				conn.commit();
				AppSNALog.warn("Tabela Usermention criada com sucesso...");
			} catch (SQLException se3) {
				conn.rollback();
				AppSNALog.error("Erro na criacao da tabela Usermention: " + se3.getMessage());
			}
			
			
			try {
				String sqlTableUsermention = "CREATE TABLE hashtagmention (" +
						  "id_usuario BIGINT NOT NULL, " +
						  "id_status INT NOT NULL, " +
						  "id_hashtagmention INT NOT NULL AUTO_INCREMENT, " +
						  "usuario VARCHAR(45) NULL, " +
						  "PRIMARY KEY (id_hashtagmention), " +
						  "UNIQUE KEY (id_usuario, id_status, id_hashtagmention), " +
						  "CONSTRAINT fk_HashtagMention_Status " +
						  "FOREIGN KEY (id_status, id_usuario) " +
						  "REFERENCES Status(id_status, id_usuario) " +
						  ");";
				stmt.executeUpdate(sqlTableUsermention);
				conn.commit();
				AppSNALog.warn("Tabela HashtagMention criada com sucesso...");
			} catch (SQLException se3) {
				conn.rollback();
				AppSNALog.error("Erro na criacao da tabela Usermention: " + se3.getMessage());
			}
			
			
			
			

		} catch (SQLException se) {			
			AppSNALog.error("Erro na configuracao da conexao: " + se.getMessage());
		} catch (Exception e) {
			AppSNALog.error("Erro na configuracao da conexao: " + e.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
					AppSNALog.warn("Encerrando o Statement...");

			} catch (SQLException se2) {				
				AppSNALog.error("Erro ao encerrar o Statement: " + se2.getMessage());
			}
			try {
				if (conn != null)
					conn.close();					
					AppSNALog.warn("Fechando a conexao com o BD...");
			} catch (SQLException se) {
				AppSNALog.error("Erro ao encerrar a conexao: " + se.getMessage());
			}
		}
		AppSNALog.warn("Concluido!");
	}
	
	
	public static void excluir(String nomeBD) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Passo 1: Registrado o JDBC driver: existem duas formas!!!
			Class.forName(BDUtil.JDBC_DRIVER);

			// Paaso 2: Abrindo uma conexao
			AppSNALog.warn("Conectando com o Banco de Dados...");
			conn = DriverManager.getConnection(BDUtil.URL_LOCAL, BDUtil.USER, BDUtil.SENHA);

			// Passo 3: Executando uma query
			stmt = conn.createStatement();
			String sqlBD = "DROP DATABASE " + nomeBD;			
			conn.setAutoCommit(false);
			
			try {
				stmt.executeUpdate(sqlBD);
				conn.commit();
				AppSNALog.warn("Database excluido com sucesso...");
			} catch (SQLException e) {
				conn.rollback();
				AppSNALog.error("Erro na exclusao do BD: " + e.getMessage());
			}
			
		} catch (SQLException se) {
			AppSNALog.error("Erro na configuracao da conexao: " + se.getMessage());
		} catch (Exception e) {
			AppSNALog.error("Erro na configuracao da conexao: " + e.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
					AppSNALog.warn("Encerrando o Statement...");

			} catch (SQLException se2) {
				AppSNALog.error("Erro ao encerrar o Statement: " + se2.getMessage());
			}
			try {
				if (conn != null)
					conn.close();
					AppSNALog.warn("Fechando a conexao com o BD...");

			} catch (SQLException se) {
				AppSNALog.error("Erro ao encerrar a conexao: " + se.getMessage());
			}
		}
		AppSNALog.warn("Concluido");
	}

}
