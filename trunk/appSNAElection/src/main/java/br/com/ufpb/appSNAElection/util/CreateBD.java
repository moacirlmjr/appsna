package br.com.ufpb.appSNAElection.util;

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
				String sqlTable1 = "create table monitorado( "
						+ "id bigint not null auto_increment,"
						+ "screen_name varchar(30) not null,"
						+ "twitter_id bigint not null," + "primary key(id)"
						+ ")";
				stmt.executeUpdate(sqlTable1);
				conn.commit();				
				AppSNALog.warn("Tabela monitorado criada com sucesso...");
				
			} catch (SQLException se1) {
				conn.rollback();				
				AppSNALog.error("Erro na criacao da tabela: " + se1.getMessage());
			}

			try {
				String sqlTable2 = "create table termo("
						+ "id bigint not null auto_increment,"
						+ "monitorado_id bigint not null,"
						+ "conteudo varchar(30),"
						+ "constraint primary key(id, monitorado_id),"
						+ "constraint foreign key(monitorado_id) references monitorado(id)"
						+ ")";
				stmt.executeUpdate(sqlTable2);
				conn.commit();				
				AppSNALog.warn("Tabela termo criada com sucesso...");
				
			} catch (SQLException se2) {
				AppSNALog.error("Erro na criacao da tabela: " + se2.getMessage());
			}
			
			try {
				String sqlTable3 = "create table resultado("
						+ "id bigint not null auto_increment,"
						+ "screen_name varchar(30) not null,"
						+ "termo_id bigint not null,"
						+ "data timestamp not null,"
						+ "latitude varchar(30),"
						+ "longitude varchar(30),"
						+ "monitorado_id bigint ,"
						+ "constraint primary key(id),"
						+ "constraint foreign key(termo_id) references termo(id),"
						+ "constraint foreign key(monitorado_id) references monitorado(id)"
						+ ")";
				stmt.executeUpdate(sqlTable3);
				conn.commit();
				AppSNALog.warn("Tabela resultado criada com sucesso...");
			} catch (SQLException se3) {
				conn.rollback();
				AppSNALog.error("Erro na criacao da tabela: " + se3.getMessage());
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
