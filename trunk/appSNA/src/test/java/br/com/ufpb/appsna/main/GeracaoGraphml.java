package br.com.ufpb.appsna.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.com.ufpb.appSNA.model.beans.SNAElement;
import br.com.ufpb.appSNA.model.beans.to.MentionTO;
import br.com.ufpb.appSNA.model.dao.SNAElementDAO;
import br.com.ufpb.appSNA.model.dao.SNAElementDAOImpl;
import br.com.ufpb.appSNA.model.dao.UserMentionDAO;
import br.com.ufpb.appSNA.model.dao.UserMentionDAOImpl;
import br.com.ufpb.appSNAUtil.model.beans.to.XmlTO;
import br.com.ufpb.appSNAUtil.model.enumeration.TypeEnum;
import br.com.ufpb.appSNAUtil.util.AccountCarrousel;
import br.com.ufpb.appSNAUtil.util.TwitterUtil;
import br.com.ufpb.appSNAUtil.util.XMLUtil;

public class GeracaoGraphml {

	public static void main(String[] args) {
		AccountCarrousel.startListReady();

		List<String> listaDeNomes = new ArrayList<String>();

		listaDeNomes.add("AIRTONGTORRES");
		listaDeNomes.add("alamorocha");
		listaDeNomes.add("ale_patricio");
		listaDeNomes.add("ALLYSONDINIZ");
		listaDeNomes.add("DEZINHAJPA");
		listaDeNomes.add("ARTHURFERRO");
		listaDeNomes.add("atila_jp");
		listaDeNomes.add("AYLTONJR");
		listaDeNomes.add("ELVISREI");
		listaDeNomes.add("evaldodesousa");
		listaDeNomes.add("fabianovidaltur");
		listaDeNomes.add("BRASILTONY");
		listaDeNomes.add("CHIQUELMEBATERA");
		listaDeNomes.add("FLUGARCEZ");
		listaDeNomes.add("IVANILDOPB");
		listaDeNomes.add("KellylopesLOPES");
		listaDeNomes.add("GALVAOJPA");
		listaDeNomes.add("luanadepaulane1");
		listaDeNomes.add("lucasduartereal");
		listaDeNomes.add("Mariacristin339");
		listaDeNomes.add("ONAMEN");
		listaDeNomes.add("jricardoamorim");
		listaDeNomes.add("RINALDOPESSOA");
		listaDeNomes.add("RIQUELSON");
		listaDeNomes.add("NTURISMO_JPPB");
		listaDeNomes.add("ThiagoADVJP");

		Map<String, Long> mapUsers = null;
		try {
			mapUsers = TwitterUtil.retornarUserId(listaDeNomes, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		UserMentionDAO userDAO = new UserMentionDAOImpl();
		Map<Long, String> mapNodes = new LinkedHashMap<Long, String>();
		Map<Long, List<MentionTO>> mapEdges = new LinkedHashMap<Long, List<MentionTO>>();

		try {
			for (String chave : mapUsers.keySet()) {

				List<MentionTO> listaMencionados = new LinkedList<MentionTO>();
				listaMencionados = userDAO.listarMencionados(mapUsers
						.get(chave));
				
				mapNodes.put(mapUsers.get(chave), chave);
				
				mapEdges.put(mapUsers.get(chave), listaMencionados);
				
				for(MentionTO mto : listaMencionados){
					if(!mapNodes.containsKey(mto.getId_target()))
						mapNodes.put(mto.getId_target(), mto.getId_target()+"");
				}

			}
			XmlTO x1 = new XmlTO("name", true, "name", TypeEnum.STRING_TYPE);
			XmlTO x2 = new XmlTO("id_label", true, "id_label", TypeEnum.STRING_TYPE);
			XmlTO x3 = new XmlTO("total_mencoes", false, "total_mencoes", TypeEnum.INT_TYPE);
			
			List<XmlTO> listXmlTO = new ArrayList<XmlTO>();
			
			listXmlTO.add(x1);
			listXmlTO.add(x2);
			listXmlTO.add(x3);

			XMLUtil.generateHeader(listXmlTO, true);
			
			for (Long chave : mapNodes.keySet()) {
				SNAElementDAO snaDAO = new SNAElementDAOImpl();
				SNAElement snaElem = new SNAElement();
				snaElem=snaDAO.findById(chave);
				XMLUtil.generateNodes(chave, snaElem.getId_label(), mapNodes.get(chave));
			}
			
			for (Long chave : mapEdges.keySet()) {
				for(MentionTO mto : mapEdges.get(chave)){
					XMLUtil.generateEdges(chave+"", mto.getId_target()+"", mto.getTotal_mention());
				}
			}
			
			XMLUtil.fechaArquivo();
			XMLUtil.salvarXML("grafoMentionades.graphml");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

	}
}
