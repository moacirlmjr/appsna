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
		UserTO uto = TwitterUtil.getUserData(u);
		
		List<UserTO> listaTO = new ArrayList<UserTO>();
		listaTO.add(uto);
		
		String local = "C:\\Users\\User\\Desktop\\teste3.csv";

		// o true significa q o arquivo será constante
		FileUtil.criaArquivo(local, true);
		
		
		
		String[] cabecalho = {"Nome", "Screename", "Biografia", "Localização", "TotalFollowers", "TotalFollowing", 
				"TotalTweets","Status", "URL", "TimeZone","Linguagem"};
		
		FileUtil.criarCabecalho(cabecalho);		


		// quebra de linha basica
		FileUtil.quebraLinha(1);

		for (UserTO user : listaTO) {
			FileUtil.escreveNoArquivo(user.getNome());
			FileUtil.quebraLinha(1);
			FileUtil.escreveNoArquivo(user.getScreename());
			FileUtil.quebraLinha(1);
			FileUtil.escreveNoArquivo(user.getLocalização());
			FileUtil.quebraLinha(1);
			FileUtil.escreveNoArquivo(user.getBiografia());
			FileUtil.quebraLinha(1);
			FileUtil.escreveNoArquivo(user.getTotalFollowers());
			FileUtil.quebraLinha(1);
			FileUtil.escreveNoArquivo(user.getTotalFollowing());
			FileUtil.quebraLinha(1);
			FileUtil.escreveNoArquivo(user.getTotalTweets());
			FileUtil.quebraLinha(1);
			FileUtil.escreveNoArquivo(user.getStatus());
			FileUtil.quebraLinha(1);
			FileUtil.escreveNoArquivo(user.getURL());
			FileUtil.quebraLinha(1);
			FileUtil.escreveNoArquivo(user.getTimeZone());
			FileUtil.quebraLinha(1);
			FileUtil.escreveNoArquivo(user.getLinguagem());
		}

		// fecha o arquivo
		FileUtil.salvarArquivo();
		
	}
	// em caso de erro apresenta mensagem abaixo

}
