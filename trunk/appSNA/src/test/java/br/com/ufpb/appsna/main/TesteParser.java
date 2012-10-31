package br.com.ufpb.appsna.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import br.com.ufpb.appSNA.model.beans.Relacionamento;
import br.com.ufpb.appSNA.model.beans.SNAElement;
import br.com.ufpb.appSNA.util.ParseSNACSV;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.Constantes;
import br.com.ufpb.appSNAUtil.util.FileUtil;

public class TesteParser {
	
	public static void main(String[] args) {
		
		//Listando da tabela SNAElement
		//Lembrar de retirar o cabecalho do arquivo e de retirar as virgulas do CSV
		try {

			List<File> arquivosCsvSNAElem = FileUtil.listarArquivosDir(Constantes.DIR_APPSNA_ELEMENTOS);
			List<SNAElement> listElements = new ArrayList<SNAElement>();
			List<SNAElement> listElementsAux = new ArrayList<SNAElement>();			

			for (File f : arquivosCsvSNAElem) {
				listElements = ParseSNACSV.realizarParserArquivoCDR(f);
				listElementsAux.addAll(listElements);
			}
			
			for (SNAElement elem : listElementsAux){
				System.out.println("ID: " + elem.getId_usuario());
				System.out.println("Nome: " + elem.getNome());
				System.out.println("ScreenName: " + elem.getScreename());
			}
				

		} catch (IOException e) {
			AppSNALog.error("Erro na tabela SNAElement: " + e.toString());
		} catch (Exception e) {
			AppSNALog.error("Erro na tabela SNAElement: " + e.toString());
		}
		
		
		//Listando da tabela Relacionamento
		//Lembrar de retirar o cabecalho do arquivo e de retirar as virgulas do CSV
		try {
			
			List<File> arquivosCsvRel = FileUtil.listarArquivosDir(Constantes.DIR_APPSNA_RELACIONAMENTOS);			
			List<Relacionamento> listRel = new ArrayList<Relacionamento>();			
			List<Relacionamento> listRelAux = new ArrayList<Relacionamento>();
		
			for (File f : arquivosCsvRel) {
				listRel = ParseSNACSV.realizarParserArquivoRelacionamentoCDR(f);
				listRelAux.addAll(listRel);
			}

			for(Relacionamento rel : listRelAux){
				System.out.println("id_source: " + rel.getId_source());
				System.out.println("id_target: " + rel.getId_target());
			}

		} catch (IOException e) {
			AppSNALog.error("Erro na tabela Relacionamento: " + e.toString());
		} catch (Exception e) {
			AppSNALog.error("Erro na tabela Relacionamento: " + e.toString());
		}

	}
		
}


