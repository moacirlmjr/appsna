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
		String local = "C:\\Users\\User\\Desktop\\testeMil.csv";
		
		List<UserTO> listaTO = new ArrayList<UserTO>();
		listaTO.add(uto);
		
		try {
			// o true significa q o arquivo será constante
			FileWriter x = new FileWriter(local, true);
			
			//cria o cabeçalho do arquivo
			x.write("Nome"+ ";" +"Screename"+ ";" +"Biografia"+ ";" +"Localização"+ ";" +"TotalFollowers"+ ";" +
			"TotalFollowing"+ ";" +"TotalTweets"+ ";" +"Status"+ ";" +"URL"+ ";" +"TimeZone"+ ";" +"Linguagem");
			
			//quebra de linha basica
			x.write("\n");
			
			//varre o LinkedHashMap com os valores
			for (UserTO user : listaTO) {
				x.write(user.getNome());
				x.write(";");
				x.write(user.getScreename());
				x.write(";");
				x.write(user.getLocalização());
				x.write(";");
				x.write(user.getBiografia());
				x.write(";");
				x.write(user.getTotalFollowers());
				x.write(";");
				x.write(user.getTotalFollowing());
				x.write(";");
				x.write(user.getTotalTweets());
				x.write(";");
				x.write(user.getStatus());
				x.write(";");
				x.write(user.getURL());
				x.write(";");
				x.write(user.getTimeZone());
				x.write(";");
				x.write(user.getLinguagem());
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







