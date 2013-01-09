package br.com.ufpb.appsna.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.ufpb.appSNA.model.beans.Relacionamento;
import br.com.ufpb.appSNA.model.beans.SNAElement;
import br.com.ufpb.appSNA.model.dao.RelacionamentoDAO;
import br.com.ufpb.appSNA.model.dao.RelacionamentoDAOImpl;
import br.com.ufpb.appSNA.model.dao.SNAElementDAO;
import br.com.ufpb.appSNA.model.dao.SNAElementDAOImpl;
import br.com.ufpb.appSNAUtil.util.AccountCarrousel;
import br.com.ufpb.appSNAUtil.util.TwitterUtil;

public class GeracaoGraphmlSerasa {

	public static void main(String[] args) {
		AccountCarrousel.startListReady();

		// List<String> listaDeNomes = new ArrayList<String>();
		//
		// listaDeNomes.add("AIRTONGTORRES");
		// listaDeNomes.add("alamorocha");
		// listaDeNomes.add("ale_patricio");
		// listaDeNomes.add("ALLYSONDINIZ");
		// listaDeNomes.add("DEZINHAJPA");
		// listaDeNomes.add("ARTHURFERRO");
		// listaDeNomes.add("atila_jp");
		// listaDeNomes.add("AYLTONJR");
		// listaDeNomes.add("ELVISREI");
		// listaDeNomes.add("evaldodesousa");
		// listaDeNomes.add("fabianovidaltur");
		// listaDeNomes.add("BRASILTONY");
		// listaDeNomes.add("CHIQUELMEBATERA");
		// listaDeNomes.add("FLUGARCEZ");
		// listaDeNomes.add("IVANILDOPB");
		// listaDeNomes.add("KellylopesLOPES");
		// listaDeNomes.add("GALVAOJPA");
		// listaDeNomes.add("luanadepaulane1");
		// listaDeNomes.add("lucasduartereal");
		// listaDeNomes.add("Mariacristin339");
		// listaDeNomes.add("ONAMEN");
		// listaDeNomes.add("jricardoamorim");
		// listaDeNomes.add("RINALDOPESSOA");
		// listaDeNomes.add("RIQUELSON");
		// listaDeNomes.add("NTURISMO_JPPB");
		// listaDeNomes.add("ThiagoADVJP");
		//
		// Map<String, Long> mapUsersTwitter = new LinkedHashMap<String,
		// Long>();
		// try {
		// mapUsersTwitter = TwitterUtil.retornarUserId(listaDeNomes, false);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		Map<String, SNAElement> mapUsers = new LinkedHashMap<String, SNAElement>();
		// for(Long keyUser : mapUsersTwitter.values()){
		//
		// }

		List<Relacionamento> relacionamentosList = new ArrayList<Relacionamento>();
		RelacionamentoDAO RelacionamentosDao = new RelacionamentoDAOImpl();
		try {
			relacionamentosList = RelacionamentosDao.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		SNAElementDAO SnaElementDao = new SNAElementDAOImpl();
		Long sourceAnterior = 0L;
		SNAElement element = new SNAElement();
		try {
			for (Relacionamento r : relacionamentosList) {
				if(sourceAnterior == 0L || sourceAnterior != r.getId_source()){
					element = SnaElementDao.findById(r.getId_source());
					mapUsers.put(element.getScreename(), element);
				}
				element = new SNAElement();
				element = SnaElementDao.findById(r.getId_target());
				mapUsers.put(element.getScreename(), element);
				sourceAnterior = r.getId_source();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("teste");

	}
}
