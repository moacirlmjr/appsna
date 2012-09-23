package br.com.ufpb.appSNAElection.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.ufpb.appSNAElection.model.beans.Monitorado;
import br.com.ufpb.appSNAElection.model.beans.Resultado;
import br.com.ufpb.appSNAElection.model.beans.Termo;
import br.com.ufpb.appSNAElection.model.beans.to.ElectionTO;
import br.com.ufpb.appSNAElection.model.dao.MonitoradoDAO;
import br.com.ufpb.appSNAElection.model.dao.MonitoradoDAOImpl;
import br.com.ufpb.appSNAElection.model.dao.ResultadoDAO;
import br.com.ufpb.appSNAElection.model.dao.ResultadoDAOImpl;
import br.com.ufpb.appSNAElection.model.dao.TermoDAO;
import br.com.ufpb.appSNAElection.model.dao.TermoDAOImpl;
import br.com.ufpb.appSNAElection.util.EntradaConfiguration;
import br.com.ufpb.appSNAElection.util.ParserElectionCsv;
import br.com.ufpb.appSNAUtil.util.Constantes;
import br.com.ufpb.appSNAUtil.util.FileUtil;

public class PersistirBase {
	public static void main(String[] args) {
		try {
			EntradaConfiguration ec = new EntradaConfiguration();			
			Monitorado m = new Monitorado();
			Termo t = new Termo();
			
			List<Termo> termos = new ArrayList<Termo>();
			
			MonitoradoDAO mDAO = new MonitoradoDAOImpl();
			TermoDAO tDAO = new TermoDAOImpl();
			
			int count = 1;
			for (String key : ec.getKeys()) {
				
				String valor = ec.getEntrada(key);
				if (key.contains("screenName")) {
					m.setScreen_name(valor);
					t = new Termo();
					t.setConteudo(valor);
					termos.add(t);
				} else if (key.contains("userid")) {
					m.setTwitterId(Long.valueOf(valor));
				} else if (key.contains("termos")) {
					String termosArray[] = valor.split(",");
					for(String ter : termosArray){
						t = new Termo();
						t.setConteudo(ter);
						termos.add(t);
					}
				}
				
				if(count % 3 == 0){
					Long idMonitoramento = mDAO.create(m);
					for(Termo termoBd : termos){
						termoBd.setMonitorado_id(idMonitoramento);
					}
					tDAO.create(termos);
					m = new Monitorado();
					termos = new ArrayList<Termo>();
				}
				count++;
			}
			
			List<File> arquivosCsv = FileUtil.listarArquivosDir(Constantes.DIR_APPSNA);
			List<Resultado> listResultado = new ArrayList<Resultado>();
			for(File f: arquivosCsv){
				List<ElectionTO> electionList = ParserElectionCsv.realizarParserArquivoCDR(f);
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
