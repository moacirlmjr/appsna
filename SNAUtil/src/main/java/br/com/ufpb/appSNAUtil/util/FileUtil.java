package br.com.ufpb.appSNAUtil.util;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

	private static FileWriter fw;

	public static void criaArquivo(String local, Boolean isConstant) {
		try {
			fw = new FileWriter(local, isConstant);
			AppSNALog.info("Arquivo gerado com sucesso");
		} catch (IOException e) {
			AppSNALog.error(e.toString());
		}
	}

	public static void escreveNoArquivo(String conteudo) {
		try {
			fw.write(conteudo);
		} catch (IOException e) {
			AppSNALog.error(e.toString());
		}
	}

	public static void criarCabecalho(String[] conteudo) {
		for (String n : conteudo) {
			FileUtil.escreveNoArquivo(n);
			FileUtil.quebra();
		}
		FileUtil.quebraLinha(1);
	}

	public static void quebraLinha(int num) {
		try {
			for (int x = 0; x < num; x++) {
				fw.write("\n");
			}
		} catch (IOException e) {
			AppSNALog.error(e.toString());
		}
	}

	public static void inserirTabulacao(int num) {
		try {
			for (int x = 0; x < num; x++) {
				fw.write("\r");
			}
		} catch (IOException e) {
			AppSNALog.error(e.toString());
		}
	}

	public static void quebra() {
		try {
			fw.write(";");
		} catch (IOException e) {
			AppSNALog.error(e.toString());
		}
	}

	public static void salvarArquivo() {
		try {
			fw.close();
			AppSNALog.info("Arquivo gravado com sucesso");
		}
		// em caso de erro apresenta mensagem abaixo
		catch (Exception e) {
			AppSNALog.error(e.toString());
		}
	}

}
