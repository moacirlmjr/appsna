package br.com.ufpb.appSNAUtil;

import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import br.com.ufpb.appSNAUtil.util.TwitterUtil;

public class TesteTwitterUtil {

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

		LinkedHashMap<String, String> dadosDoUsuario = TwitterUtil
				.getUserData(u);

		for (Entry<String, String> entry : dadosDoUsuario.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}

		
		String local = "C:\\Users\\User\\Desktop\\teste.csv";
		
		try {
			// o true significa q o arquivo será constante
			FileWriter x = new FileWriter(local, true);
			
			//cria o cabeçalho do arquivo
			x.write("Nome"+ ";" +"Screename"+ ";" +"Biografia"+ ";" +"Localização"+ ";" +"TotalFollowers"+ ";" +
			"TotalFollowing"+ ";" +"TotalTweets"+ ";" +"Status"+ ";" +"URL"+ ";" +"TimeZone"+ ";" +"Linguagem");
			
			//quebra de linha basica
			x.write("\n");
			
			//varre o LinkedHashMap com os valores
			for (Entry<String, String> entry : dadosDoUsuario.entrySet()) {
				x.write(entry.getValue());
				x.write(";");
			}
			
			// cria o arquivo
			x.close(); 
			JOptionPane.showMessageDialog(null, "Arquivo gravado com sucesso", "Concluído", JOptionPane.INFORMATION_MESSAGE);
		}
		// em caso de erro apresenta mensagem abaixo
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
		}
	}

}
