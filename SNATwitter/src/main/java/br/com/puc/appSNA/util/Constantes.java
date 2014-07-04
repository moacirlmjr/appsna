package br.com.puc.appSNA.util;


public class Constantes {
	
//	public static final String IP = "139.82.24.187";
	
	public static final String IP = "localhost";
	
	public static final String URL_LOCAL = "jdbc:mysql://"+IP;	

//	public static final String LANG = "en";
	
	public static final String LANG = "pt";
	
	public static final String DATABASE_NAME = "appsnatwitter_" + LANG;
	
	public static String URL = URL_LOCAL + "/" + DATABASE_NAME;

	public static String USER = "root";

//	public static String SENHA = "mv13wavaty";
	
	public static String SENHA = "";
	
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  

//	public static final String DIR_GRAPHML = "/usr/local/appSNA/graphmls/";
	public static final String DIR_GRAPHML = "E:\\DESENVOLVIMENTO\\appSNA\\";
}
