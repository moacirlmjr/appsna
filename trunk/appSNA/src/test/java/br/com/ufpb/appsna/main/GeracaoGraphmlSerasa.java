package br.com.ufpb.appsna.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.ufpb.appSNA.model.beans.Relacionamento;
import br.com.ufpb.appSNA.model.beans.SNAElement;
import br.com.ufpb.appSNA.model.beans.to.InadimplenciaTO;
import br.com.ufpb.appSNA.model.dao.RelacionamentoDAO;
import br.com.ufpb.appSNA.model.dao.RelacionamentoDAOImpl;
import br.com.ufpb.appSNA.model.dao.SNAElementDAO;
import br.com.ufpb.appSNA.model.dao.SNAElementDAOImpl;
import br.com.ufpb.appSNAUtil.model.beans.to.XmlTO;
import br.com.ufpb.appSNAUtil.model.enumeration.TypeEnum;
import br.com.ufpb.appSNAUtil.util.AccountCarrousel;
import br.com.ufpb.appSNAUtil.util.TwitterUtil;
import br.com.ufpb.appSNAUtil.util.XMLUtil;

public class GeracaoGraphmlSerasa {

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
		
		 Map<String, Long> mapUsersTwitter = new LinkedHashMap<String, Long>();
		 try {
			 mapUsersTwitter = TwitterUtil.retornarUserId(listaDeNomes, false);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }

		Map<String, SNAElement> mapUsers = new LinkedHashMap<String, SNAElement>();
		 for(Long keyUser : mapUsersTwitter.values()){
		
		 }

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
				if (sourceAnterior == 0L || sourceAnterior != r.getId_source()) {
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

		XmlTO x1 = new XmlTO("nome", true, "nome", TypeEnum.STRING_TYPE);
		XmlTO x2 = new XmlTO("id_label", true, "id_label", TypeEnum.INT_TYPE);
		XmlTO x3 = new XmlTO("id_twitter", true, "id_twitter",
				TypeEnum.INT_TYPE);
		XmlTO x4 = new XmlTO("tipo", true, "tipo", TypeEnum.INT_TYPE);
		XmlTO x5 = new XmlTO("negatividade", true, "negatividade",
				TypeEnum.INT_TYPE);
		XmlTO x6 = new XmlTO("vizinhanca_all", true, "vizinhanca_all",
				TypeEnum.INT_TYPE);
		XmlTO x7 = new XmlTO("vizinhanca_simple", true, "vizinhanca_simple",
				TypeEnum.INT_TYPE);
		XmlTO x8 = new XmlTO("neg_vizinhanca", true, "neg_vizinhanca",
				TypeEnum.INT_TYPE);
		XmlTO x9 = new XmlTO("inadimplencia", true, "inadimplencia",
				TypeEnum.DOUBLE_TYPE);

		List<XmlTO> listXmlTO = new ArrayList<XmlTO>();

		listXmlTO.add(x1);
		listXmlTO.add(x2);
		listXmlTO.add(x3);
		listXmlTO.add(x4);
		listXmlTO.add(x5);
		listXmlTO.add(x6);
		listXmlTO.add(x7);
		listXmlTO.add(x8);
		listXmlTO.add(x9);

		XMLUtil.generateHeader(listXmlTO, true);

		for (SNAElement elem : mapUsers.values()) {
			try {
				InadimplenciaTO ina = SnaElementDao.verificarDadosInadimplencia(elem.getId_usuario());
				elem.setInadimplenciaTO(ina);
				elem.setQteAmigosNegativados(SnaElementDao.retornarQtdeAmigosNegativos(elem.getId_usuario()));

				XMLUtil.generateNodes(
						elem.getId_usuario(), 
						elem.getId_label(),
						elem.getNome(), 
						elem.getNegativado(), 
						elem.getInadimplenciaTO().getNegatividade() == null ? 0 : elem.getInadimplenciaTO().getNegatividade(), 
						elem.getTotalFollowing(), 
						elem.getQteAmigosNegativados() == null ? 0 : elem.getQteAmigosNegativados(), 
						elem.getInadimplenciaTO().getVizinhanca() == null ? 0 : elem.getInadimplenciaTO().getVizinhanca(), 
						elem.getInadimplenciaTO().getInadimplencia() == null ? 0f : elem.getInadimplenciaTO().getInadimplencia()
						
				);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("teste");

		for (Relacionamento r : relacionamentosList) {
			XMLUtil.generateEdges(r.getId_source(), r.getId_target());
		}
		
		XMLUtil.fechaArquivo();
		XMLUtil.salvarXML("grafoSerafa2345678.graphml");

	}
}
