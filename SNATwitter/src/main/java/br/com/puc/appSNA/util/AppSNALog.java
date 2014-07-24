package br.com.puc.appSNA.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;


public class AppSNALog {
	private static Logger logger = null;

	private AppSNALog() {

	}

	static {
		try {
			logger = Logger.getLogger("br.com.puc");
		} catch (Exception e) {
			System.out.println("Não foi possível criar o mecanismo de log.");
		}
	}
	
	private static String tratarExcecao(Exception e){
		StringWriter strWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(strWriter);
		e.printStackTrace(writer);
		writer.close();
		return strWriter.getBuffer().toString();
	}

	public static void debug(String message) {
		logger.debug(message);
	}

	public static void info(Object message) {
		logger.info(message);
	}
	
	public static void info(String message) {
		logger.info(message);
	}

	public static void warn(String message) {
		logger.warn(message);
	}

	public static void error(String message) {
		logger.error(message);
	}

	public static void debug(Exception e) {
		logger.debug(tratarExcecao(e));
	}

	public static void info(Exception e) {
		logger.info(tratarExcecao(e));
	}

	public static void warn(Exception e) {
		logger.warn(tratarExcecao(e));
	}

	public static void error(Exception e) {
		logger.error(tratarExcecao(e));
	}

}
