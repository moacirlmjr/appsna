package br.com.ufpb.appSNA.main;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.ufpb.appSNA.model.beans.Relacionamento;
import br.com.ufpb.appSNA.model.beans.SNAElement;
import br.com.ufpb.appSNA.util.ParseSNACSV;
import br.com.ufpb.appSNAUtil.util.Constantes;
import br.com.ufpb.appSNAUtil.util.FileUtil;



public class PersistirBase {
	
	public static void main(String[] args) {
		try {
			List<File> arquivosCsvSNAElem = FileUtil.listarArquivosDir(Constantes.DIR_APPSNA + "elementos");
			List<File> arquivosCsvRel = FileUtil.listarArquivosDir(Constantes.DIR_APPSNA + "relacionamento");
			
			List<SNAElement> listElements = new ArrayList<SNAElement>();
			List<Relacionamento> listRelacionamento = new ArrayList<Relacionamento>();

			for(File f: arquivosCsvSNAElem){
				listElements = ParseSNACSV.realizarParserArquivoCDR(f);
				Monitorado monitorado = tDAO.getMonitoradoByTermo(electionList.get(0).getTermo());
				if(monitorado != null){
					for(ElectionTO eto : electionList){
						Resultado result = new Resultado();
						Termo termo = tDAO.getTermoByConteudo(eto.getTermo());
						result.setScreen_name(eto.getScreen_name());
						result.setLatitude(eto.getLatitude());
						result.setLongitude(eto.getLongitude());
						result.setData(eto.getData());
						if(termo == null){
							System.out.println("teste");
						}
						result.setTermoId(termo.getId());
						result.setMonitorado_id(monitorado.getId());
						listResultado.add(result);
					}
					
				}
			}
			
			ResultadoDAO resultDAO = new ResultadoDAOImpl();
			
			resultDAO.create(listResultado);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
