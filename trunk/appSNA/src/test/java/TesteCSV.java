import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.com.ufpb.appSNAUtil.model.beans.to.UserTO;
import br.com.ufpb.appSNAUtil.util.AccountCarrousel;
import br.com.ufpb.appSNAUtil.util.Constantes;
import br.com.ufpb.appSNAUtil.util.FileUtil;
import br.com.ufpb.appSNAUtil.util.TwitterUtil;

public class TesteCSV {

	public static void main(String[] args) throws Exception {

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
		
		AccountCarrousel.startListReady();

		Map<String, Long> mapUsers = TwitterUtil.retornarUserId(listaDeNomes);

		List<Long> listaCsv = new LinkedList<Long>();

		for (String key : mapUsers.keySet()) {
			List<Long> listAmigoId = TwitterUtil
					.retornarListaAmigosIdsList(key);
			listaCsv = verificar(listaCsv, listAmigoId);
		}
		
		FileUtil.criaArquivo(Constantes.DIR_APPSNA + "\\arquivo.csv", true);
		
		String[] cabecalho = {"Nome", "Screename", "Biografia", "Localização", "TotalFollowers", "TotalFollowing", 
				"TotalTweets", "URL", "TimeZone","Linguagem"};
		FileUtil.criarCabecalho(cabecalho);	
		FileUtil.refreash();
		
		for (Long amigoId : listaCsv) {
			UserTO amigo = TwitterUtil.getUserData(amigoId);
			
			FileUtil.escreveNoArquivo(amigo.getNome());
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(amigo.getScreename());
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(amigo.getBiografia().replaceAll(";", " "));
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(amigo.getLocalização().replaceAll(";", " "));
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(amigo.getTotalFollowers());
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(amigo.getTotalFollowing());
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(amigo.getTotalTweets());
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(amigo.getURL());
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(amigo.getTimeZone());
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(amigo.getLinguagem());
			FileUtil.quebraLinha(1);
			FileUtil.refreash();
		}

		FileUtil.salvarArquivo();

	}

	private static List<Long> verificar(List<Long> listCsv, List<Long> listAmigos) {
		List<Long> listAux = new ArrayList<Long>();
		listAux.addAll(listCsv);
		for (Long amigoId : listAmigos) {
			if(listAux.size() != 0){
				for (Long idCsv : listAux) {
					if (amigoId.longValue() != idCsv.longValue()) {
						listCsv.add(amigoId);
						break;
					}
				}
			}else{
				listCsv.addAll(listAmigos);
				break;
			}
			
			
		}
		return listCsv;
	}

}
