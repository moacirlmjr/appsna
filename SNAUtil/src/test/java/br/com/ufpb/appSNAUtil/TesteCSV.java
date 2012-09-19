package br.com.ufpb.appSNAUtil;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import br.com.ufpb.appSNAUtil.util.FileUtil;
import br.com.ufpb.appSNAUtil.util.TwitterUtil;

public class TesteCSV {

	public static void main(String[] args) throws TwitterException {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("PEwerwPBLopBrxrdWCdCHQ")
				.setOAuthConsumerSecret(
						"oLGCU3gRTNboUPi1VjCORHKyp2h93YnodZpNqekIOOU")
				.setOAuthAccessToken(
						"312660739-pFFGuPbuZBzOn7yJZJkt6LpVMJs8jhz71eXrwke8")
				.setOAuthAccessTokenSecret(
						"JY4MtWT4VFivqnKU0OZR3pa2KK6akRsFIvy94B3SY");

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		User u = twitter.showUser(312660739);
		
		LinkedHashMap<String, String> dadosDoUsuario = TwitterUtil.getUserData(u);

		String local = "C:\\Users\\User\\Desktop\\teste3.csv";

		// o true significa q o arquivo será constante
		FileUtil.criaArquivo(local, true);
		
		
		
		String[] cabecalho = {"Nome", "Screename", "Biografia", "Localização", "TotalFollowers", "TotalFollowing", 
				"TotalTweets","Status", "URL", "TimeZone","Linguagem"};
		
		FileUtil.criarCabecalho(cabecalho);		


		// quebra de linha basica
		FileUtil.quebraLinha(1);

		// varre o LinkedHashMap com os valores
		for (Entry<String, String> entry : dadosDoUsuario.entrySet()) {
			FileUtil.escreveNoArquivo(entry.getValue());
			FileUtil.quebra();
		}

		// fecha o arquivo
		FileUtil.salvarArquivo();
		
	}
	// em caso de erro apresenta mensagem abaixo

}
