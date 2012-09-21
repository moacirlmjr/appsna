package br.com.ufpb.appSNAUtil.model.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public abstract class AppSnaDAO<T> implements Serializable {

	private static final String driver = "com.mysql.jdbc.Driver";

	public abstract T create(T objeto) throws Exception;
	
	public abstract T update(T objeto) throws Exception;

	public abstract T findById(Long id) throws Exception;

	public abstract List<T> list() throws Exception;

	public abstract void remove(T objeto) throws Exception;

	public static Connection returnConnection(String url, String user,
			String senha) throws Exception {
		Connection conn = null;
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, user, senha);

		return conn;
	}

}
