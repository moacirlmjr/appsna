package br.com.ufpb.appSNAUtil;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import br.com.ufpb.appSNAUtil.model.beans.to.UserTO;
import br.com.ufpb.appSNAUtil.util.FileUtil;
import br.com.ufpb.appSNAUtil.util.TwitterUtil;

public class TesteCSV {

	public static void main(String[] args) throws TwitterException {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("agNRXrU45rAyrl89sDMXvQ")
				.setOAuthConsumerSecret(
						"cRpQSlhyksKtSGs6yEcMyDr7T41yZt1Vyjd1kyea9U")
				.setOAuthAccessToken(
						"107083501-mrt56eHcMFfhw4Cu3zRQYFqoEEltgnuc8k7jbj3k")
				.setOAuthAccessTokenSecret(
						"54DyuffiZBGwRD9WTBkZ5N6RQTs7DyZEHFI67GNdOG4");

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();


		User u = twitter.showUser("PadreDjacy");		
		UserTO uto = TwitterUtil.getUserData(u);
		
		List<UserTO> listaTO = new ArrayList<UserTO>();
		listaTO.add(uto);
		
		String local = "C:\\Users\\David\\Desktop\\teste3.csv";

		// o true significa q o arquivo será constante
		FileUtil.criaArquivo(local, true);
		
		
		
		String[] cabecalho = {"Nome", "Screename", "Biografia", "Localização", "TotalFollowers", "TotalFollowing", 
				"TotalTweets","URL", "TimeZone","Linguagem", "Data de Criacao", "URL Imagem"};
		
		FileUtil.criarCabecalho(cabecalho);		


		// quebra de linha basica
		FileUtil.quebraLinha(1);

		for (UserTO user : listaTO) {
			FileUtil.escreveNoArquivo(user.getNome());
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(user.getScreename());
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(user.getLocalização());
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(user.getBiografia());
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(user.getTotalFollowers());
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(user.getTotalFollowing());
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(user.getTotalTweets());
			FileUtil.quebra();			
			FileUtil.escreveNoArquivo(user.getURL());
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(user.getTimeZone());
			FileUtil.quebra();
			FileUtil.escreveNoArquivo(user.getLinguagem());
			FileUtil.quebraLinha(1);
		}

		// fecha o arquivo
		FileUtil.salvarArquivo();
		
	}
	// em caso de erro apresenta mensagem abaixo

}
