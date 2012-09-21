package br.com.ufpb.appSNAUtil.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAOUtil {
	private static final String driver = "com.mysql.jdbc.Driver";

	public static Connection returnConnection(String url, String user,
			String senha) throws Exception {
		Connection conn = null;
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, user, senha);

		return conn;
	}
}
