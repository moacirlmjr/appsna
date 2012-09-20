package br.com.ufpb.appSNAUtil;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import br.com.ufpb.appSNAUtil.model.beans.to.UserTO;
import br.com.ufpb.appSNAUtil.util.TwitterUtil;

public class TesteTwitterUtil {

	public static void main(String[] args) throws TwitterException {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("pKPaLdk6DofC7bfNNA1qw")
				.setOAuthConsumerSecret(
						"Ks5QO1enyC8Co5My1BVoSN9HVFDhFi8PKsfivr0Xs")
				.setOAuthAccessToken(
						"131686365-iWIbKNxKlgUK4Wa2gMx6Ojjsu62aKZNUDvNfbBN2")
				.setOAuthAccessTokenSecret(
						"2kPLmaJiDNEAplKIAlJz8Jhxf7JXgp0E00EoCjQi0");		
		
		

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		User u = twitter.showUser("@Danyllo_Wagner");
		UserTO uto = TwitterUtil.getUserData(u);
		String local = "C:\\Users\\David\\Desktop\\teste3.csv";
		
		List<UserTO> listaTO = new ArrayList<UserTO>();
		listaTO.add(uto);
		
		try {
			// o true significa q o arquivo ser� constante
			FileWriter x = new FileWriter(local, true);
			
			//cria o cabe�alho do arquivo
			x.write("Nome"+ ";" +"Screename"+ ";" +"Biografia"+ ";" +"Localiza��o"+ ";" +"TotalFollowers"+ ";" +
				"TotalFollowing"+ ";" +"TotalTweets"+ ";" + "URL"+ ";" +"TimeZone"+ ";" +"Linguagem" + 
				";" + "Data de Criacao"+ ";" + "URL Imagem");
			
			//quebra de linha basica
			x.write("\n");
			x.flush();
			
			//varre o LinkedHashMap com os valores
			for (UserTO user : listaTO) {
				x.write(user.getNome());
				x.write(";");
				x.flush();
				String sc = user.getScreename().replace("\r", "$").replace("\n", "$");
				x.write(sc);
				x.write(";");
				x.flush();
				String controle = user.getBiografia().replace("\r", "$").replace("\n", "$");
				x.write(controle);
				x.write(";");
				x.flush();
				String lc = user.getLocaliza��o().replace("\r", "$").replace("\n", "$");
				x.write(lc);
				x.write(";");
				x.flush();
				x.write(user.getTotalFollowers());
				x.write(";");
				x.write(user.getTotalFollowing());
				x.write(";");
				x.write(user.getTotalTweets());
				x.write(";");				
				x.write(user.getURL());
				x.write(";");
				x.write(user.getTimeZone());
				x.write(";");
				x.write(user.getLinguagem());
				x.write(";");
				x.write(user.getDataDeCriacao());
				x.write(";");
				x.write(user.getURLImage());
			}
			
			// cria o arquivo
			x.close(); 
			JOptionPane.showMessageDialog(null, "Arquivo gravado com sucesso", "Conclu�do", JOptionPane.INFORMATION_MESSAGE);
		}
		// em caso de erro apresenta mensagem abaixo
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Aten��o", JOptionPane.WARNING_MESSAGE);
		}
	}

}







