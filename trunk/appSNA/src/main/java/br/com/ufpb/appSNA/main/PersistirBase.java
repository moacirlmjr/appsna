package br.com.ufpb.appSNA.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import br.com.ufpb.appSNA.model.beans.Relacionamento;
import br.com.ufpb.appSNA.model.beans.SNAElement;
import br.com.ufpb.appSNA.model.dao.RelacionamentoDAO;
import br.com.ufpb.appSNA.model.dao.RelacionamentoDAOImpl;
import br.com.ufpb.appSNA.model.dao.SNAElementDAO;
import br.com.ufpb.appSNA.model.dao.SNAElementDAOImpl;
import br.com.ufpb.appSNA.util.ParseSNACSV;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.Constantes;
import br.com.ufpb.appSNAUtil.util.FileUtil;

public class PersistirBase {

	public static void main(String[] args) {
		
		
		//Povoamento da tabela SNAElement
//		try {
//
//			List<File> arquivosCsvSNAElem = FileUtil.listarArquivosDir(Constantes.DIR_APPSNA_ELEMENTOS);
//			List<SNAElement> listElements = new ArrayList<SNAElement>();
//			List<SNAElement> listElementsAux = new ArrayList<SNAElement>();			
//
//			for (File f : arquivosCsvSNAElem) {
//				listElements = ParseSNACSV.realizarParserArquivoCDR(f);
//				listElementsAux.addAll(listElements);
//			}
//			
//			SNAElementDAO snaDAO = new SNAElementDAOImpl();
//			snaDAO.create(listElementsAux);			
//
//		} catch (IOException e) {
//			AppSNALog.error("Erro no povoamento da tabela SNAElement: " + e.toString());
//		} catch (Exception e) {
//			AppSNALog.error("Erro no povoamento da tabela SNAElement: " + e.toString());
//		}
		
		
		//Povoamento da tabela Relacionamento
		try {
			
			List<File> arquivosCsvRel = FileUtil.listarArquivosDir(Constantes.DIR_APPSNA_RELACIONAMENTOS);			
			List<Relacionamento> listRel = new ArrayList<Relacionamento>();			
			List<Relacionamento> listRelAux = new ArrayList<Relacionamento>();
		
			for (File f : arquivosCsvRel) {
				listRel = ParseSNACSV.realizarParserArquivoRelacionamentoCDR(f);
				listRelAux.addAll(listRel);
			}

			RelacionamentoDAO relDAO = new RelacionamentoDAOImpl();
			relDAO.create(listRelAux);

		} catch (IOException e) {
			AppSNALog.error("Erro no povoamento da tabela Relacionamento: " + e.toString());
		} catch (Exception e) {
			AppSNALog.error("Erro no povoamento da tabela Relacionamento: " + e.toString());
		}

	}
}
