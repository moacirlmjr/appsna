package br.com.puc.snaTwitterWeb.controller.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import br.com.puc.appSNA.model.beans.Filtro;
import br.com.puc.appSNA.model.beans.Usuario;
import br.com.puc.appSNA.model.dao.UsuarioDAO;
import br.com.puc.appSNA.model.dao.UsuarioDAOImpl;
import br.com.puc.appSNA.model.enumeration.AuthEnum;
import br.com.puc.appSNA.model.listener.SNATwitterStatusListenerArquivo;
import br.com.puc.appSNA.util.AppSNALog;
import br.com.puc.appSNA.util.Constantes;
import br.com.puc.appSNA.util.TwitterUtil;

@ManagedBean(name = "streamTempoRealController")
@SessionScoped
public class StreamTempoRealController implements Serializable {

	private Usuario user;
	private String termo;

	private Filtro filtro;

	private List<Usuario> users;
	private List<String> termos;

	private TwitterStream twitterStream;
	private Twitter twitter;
	private String stream;

	private boolean renderizarStream;

	private StreamedContent arquivo;

	public StreamTempoRealController() {
		users = new ArrayList<>();
		termos = new ArrayList<>();
		renderizarStream = false;
		try {
			twitterStream = new TwitterStreamFactory(
					TwitterUtil
							.createConfigurationBuilder(AuthEnum.MOACIR_KEY2))
					.getInstance();
			twitter = new TwitterFactory(
					TwitterUtil
							.createConfigurationBuilder(AuthEnum.MOACIR_KEY3))
					.getInstance();
		} catch (Exception e) {
			AppSNALog.error(e);
		}
	}

	public void adicionar(ActionEvent ev) {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String action = params.get("action");

		if (action.equals("usuario")) {
			if(!users.contains(user)){
				users.add(user);
			}
			user = new Usuario();
		} else if (action.equals("termo")) {
			termos.add(termo);
			termo = "";
		}
	}

	public void remover(ActionEvent ev) {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String action = params.get("action");
		String valor = params.get("valor");
		try {
			if (action.equals("usuario")) {
				UsuarioDAO uDAO = new UsuarioDAOImpl();
				Usuario u = uDAO.findByScreenName(valor);
				users.remove(u);
			} else if (action.equals("termo")) {
				termos.remove(valor);
			}
		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}
	}

	public List<Usuario> completeUser(String query) {
		try {
			List<Usuario> listUsers = TwitterUtil.retornarUsers(query, twitter);
			if (listUsers != null) {
				UsuarioDAO uDAO = new UsuarioDAOImpl();
				for (Usuario u : listUsers) {
					if (uDAO.findById(u.getId_usuario()).getId_usuario() == null) {
						uDAO.create(u);
					}
				}
			}
			return listUsers;
		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}
		return null;
	}

	public void filtroTempoReal() {
		try {
			renderizarStream = true;
			StatusListener listener = new SNATwitterStatusListenerArquivo();
			File file = new File(Constantes.DIR + "stream.txt");
			if (file.exists()) {
				file.delete();
				file = new File(Constantes.DIR + "stream.txt");
				file.createNewFile();
			}

			file = new File(Constantes.DIR + "streamCompleto.txt");
			if (file.exists()) {
				file.delete();
				file = new File(Constantes.DIR + "streamCompleto.txt");
				file.createNewFile();
			}
			twitterStream.addListener(listener);
			if (termos.size() > 0 || users.size() > 0) {
				FilterQuery fq = new FilterQuery();
				if (termos.size() > 0) {
					String[] array = new String[termos.size()];
					int i = 0;
					for (String termo : termos) {
						array[i++] = termo;
					}
					fq.track(array);
				}

				if (users.size() > 0) {
					long[] array = new long[users.size()];
					int i = 0;
					for (Usuario user : users) {
						array[i++] = user.getId_usuario();
					}
					fq.follow(array);
				}

				twitterStream.filter(fq);
			} else {
				twitterStream.sample();
			}

			users = new ArrayList<>();
			termos = new ArrayList<>();
		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}
	}

	public void stopFiltroTempoReal() {
		twitterStream.shutdown();
		renderizarStream = false;
		try {
			arquivo = new DefaultStreamedContent(new FileInputStream(
					Constantes.DIR + "streamCompleto.txt"), "application/txt",
					"StreamCompleto.txt");
		} catch (FileNotFoundException e) {
			AppSNALog.error(e.toString());
		}
	}

	public void verStream() {
		File file = new File(Constantes.DIR + "stream.txt");
		try {
			InputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String linha = "";
			stream = "";
			while ((linha = br.readLine()) != null) {
				stream += linha + "<br />";

			}
		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public String getTermo() {
		return termo;
	}

	public void setTermo(String termo) {
		this.termo = termo;
	}

	public List<String> getTermos() {
		return termos;
	}

	public void setTermos(List<String> termos) {
		this.termos = termos;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public TwitterStream getTwitterStream() {
		return twitterStream;
	}

	public void setTwitterStream(TwitterStream twitterStream) {
		this.twitterStream = twitterStream;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public boolean isRenderizarStream() {
		return renderizarStream;
	}

	public void setRenderizarStream(boolean renderizarStream) {
		this.renderizarStream = renderizarStream;
	}

	public List<Usuario> getUsers() {
		return users;
	}

	public void setUsers(List<Usuario> users) {
		this.users = users;
	}

	public StreamedContent getArquivo() {
		return arquivo;
	}

	public void setArquivo(StreamedContent arquivo) {
		this.arquivo = arquivo;
	}

}
