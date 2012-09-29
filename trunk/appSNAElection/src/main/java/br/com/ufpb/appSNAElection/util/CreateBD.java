package br.com.ufpb.appSNAElection.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateBD {

	public static void criar() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Passo 1: Registrado o JDBC driver: existem duas formas!!!
			Class.forName(BDUtil.JDBC_DRIVER);

			// Paaso 2: Abrindo uma conexao
			System.out.println("Conectando com o Banco de Dados");
			conn = DriverManager.getConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);

			// Passo 3: Executando uma query
			stmt = conn.createStatement();

			String sqlBD = "CREATE DATABASE TESTE";
			stmt.executeUpdate(sqlBD);
			System.out.println("Database criado com sucesso...");
			
			String sqlUse = "USE TESTE";
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
				System.out.println("Tabela monitorado criada com sucesso...");
			} catch (SQLException se1) {
				conn.rollback();
				System.out.println("Erro na criacao da tabela: "+ se1.getMessage());
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
				System.out.println("Tabela termo criada com sucesso...");
			} catch (SQLException se2) {
				conn.rollback();
				System.out.println("Erro na criacao da tabela: " + se2.getMessage());
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
				System.out.println("Tabela resultado criada com sucesso...");
			} catch (SQLException se3) {
				conn.rollback();
				System.out.println("Erro na criacao da tabela: " + se3.getMessage());
			}

		} catch (SQLException se) {
			System.out.println("Erro na configuracao da conexao: " + se.getMessage());
		} catch (Exception e) {
			System.out.println("Erro na configuracao da conexao: " + e.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
					System.out.println("Encerrando o Statement...");

			} catch (SQLException se2) {
				System.out.println("Erro ao encerrar o Statement: " + se2.getMessage());

			}
			try {
				if (conn != null)
					conn.close();
					System.out.println("Fechando a conexao com o BD...");

			} catch (SQLException se) {
				System.out.println("Erro ao encerrar a conexao: " + se.getMessage());
			}
		}
		System.out.println("Concluido!");
	}
	
	
	public static void excluir() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Passo 1: Registrado o JDBC driver: existem duas formas!!!
			Class.forName(BDUtil.JDBC_DRIVER);

			// Paaso 2: Abrindo uma conexao
			System.out.println("Conectando com o Banco de Dados");
			conn = DriverManager.getConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);

			// Passo 3: Executando uma query
			stmt = conn.createStatement();

			String sqlBD = "DROP DATABASE TESTE";
			
			conn.setAutoCommit(false);
			
			try {
				stmt.executeUpdate(sqlBD);
				conn.commit();
				System.out.println("Database excluido com sucesso...");
			} catch (SQLException e) {
				conn.rollback();
				System.out.println("Erro na exclusao do BD: " + e.getMessage());
			}
			
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
					System.out.println("Encerrando o Statement...");

			} catch (SQLException se2) {
				System.out.println("Erro ao encerrar o Statement: " + se2.getMessage());
			}
			try {
				if (conn != null)
					conn.close();
					System.out.println("Fechando a conexao com o BD...");

			} catch (SQLException se) {
				System.out.println("Erro ao encerrar a conexao: " + se.getMessage());
			}
		}
		System.out.println("Concluido!");
	}

}
