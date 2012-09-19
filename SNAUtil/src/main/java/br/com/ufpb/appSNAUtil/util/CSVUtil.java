package br.com.ufpb.appSNAUtil.util;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class CSVUtil {
	
	private static FileWriter fw;	
	
	public static void criaArquivo(String local, Boolean isConstant){
		try {
			fw= new FileWriter(local, isConstant);
			JOptionPane.showMessageDialog(null, "Arquivo gerado com sucesso",
					"Concluído", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção",
					JOptionPane.WARNING_MESSAGE);
		}
	}


	public static void escreveNoArquivo(String conteudo){
		try {
			fw.write(conteudo);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção",
					JOptionPane.WARNING_MESSAGE);
		}
	}
		
	public static void quebraLinha(int num){		
		try {
			for(int x=0; x<num; x++){
				fw.write("\n");
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public static void inserirTabulacao(int num){		
		try {
			for(int x=0; x<num; x++){
				fw.write("\r");
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public static void quebra(){		
		try {
			fw.write(";");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção",
					JOptionPane.WARNING_MESSAGE);
		}
	}
		
	public static void salvarArquivo(){
		try {
			fw.close();
			JOptionPane.showMessageDialog(null, "Arquivo gravado com sucesso",
					"Concluído", JOptionPane.INFORMATION_MESSAGE);
		}
		// em caso de erro apresenta mensagem abaixo
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção",
					JOptionPane.WARNING_MESSAGE);
		}
}


}
