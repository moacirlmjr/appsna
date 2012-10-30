package br.com.ufpb.appSNA.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ufpb.appSNA.model.beans.MyLink;
import br.com.ufpb.appSNA.model.beans.MyNode;
import br.com.ufpb.appSNAUtil.util.AccountCarrousel;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.TwitterUtil;
import br.com.ufpb.appSNAUtil.util.XMLUtil;

public class GerarGraphMl {

	public ArrayList<MyNode> listaNodes;
	public ArrayList<MyLink> listaLinks;
	private List<String> listaDeNomes = new ArrayList<String>();

	public GerarGraphMl() {

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

		listaNodes = new ArrayList<MyNode>();
	}

	public void gerarGraphMl() {
		try {
			Map<Long, Long> listaNodesAmigos = new HashMap<Long, Long>();

			Map<String, Long> mapUsers = TwitterUtil.retornarUserId(
					listaDeNomes, false);

			MyNode node = null;

			List<Long> listaAux = new ArrayList<Long>();
			MyNode nodeSerasa;

			for (String key : mapUsers.keySet()) {
				List<Long> listAmigoId = TwitterUtil
						.retornarListaAmigosIdsList(key, true);
				node = new MyNode(mapUsers.get(key), key, listAmigoId);
				listaNodes.add(node);
			}

			for (MyNode nodefor : listaNodes) {

				listaAux.add(nodefor.getId());

				XMLUtil.generateNodes(nodefor.getId(), nodefor.getNome());
				for (Long amigoId : nodefor.getListadeAmigos()) {
					if (!verificar(amigoId)) {
						listaNodesAmigos.put(amigoId, amigoId);
					}
				}

			}

			nodeSerasa = new MyNode(1, "serasa", listaAux);
			XMLUtil.generateNodes(1, "serasa");

			listaNodes.add(nodeSerasa);

			for (Long amigo : listaNodesAmigos.keySet()) {
				XMLUtil.generateNodes(amigo, amigo.toString());
			}

			// MyNode amigo = null;

			for (MyNode n : listaNodes) {
				for (Long amigoId : n.getListadeAmigos()) {
					// amigo = new MyNode(amigoId.intValue(), "A"+ amigoId);
					// g.addEdge(new MyLink(), n, amigo, EdgeType.DIRECTED);
					XMLUtil.generateEdges(n.getId(), amigoId);
				}
			}

			XMLUtil.fechaArquivo();
			XMLUtil.salvarXML("grafoRedeJoaoPessoa.graphml");
		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}
	}

	private boolean verificar(Long amigoId) {

		boolean resultado = false;
		for (MyNode teste : listaNodes) {
			if (amigoId.longValue() == teste.getId()) {
				resultado = true;
			}
		}

		return resultado;
	}

	public static void main(String[] args) {
		AccountCarrousel.startListReady();
		GerarGraphMl g;
		g = new GerarGraphMl();
		g.gerarGraphMl();
	}
}