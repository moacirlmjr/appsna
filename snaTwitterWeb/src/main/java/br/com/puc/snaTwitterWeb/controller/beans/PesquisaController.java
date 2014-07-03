package br.com.puc.snaTwitterWeb.controller.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "pesquisaController")
@ViewScoped
public class PesquisaController implements Serializable {

	private String screenName;
	private String biografia;
	private String localizacao;
	private String termo;
	private Calendar dataInicio;
	private Calendar dataFim;

	private List<String> screenNames;
	private List<String> biografias;
	private List<String> localizacoes;
	private List<String> termos;

	public PesquisaController() {
		screenNames = new ArrayList<>();
		biografias = new ArrayList<>();
		localizacoes = new ArrayList<>();
		termos = new ArrayList<>();
	}

	public void adicionar(ActionEvent ev) {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String action = params.get("action");

		if (action.equals("screenName")) {
			screenNames.add(screenName);
			screenName = "";
		} else if (action.equals("biografia")) {
			biografias.add(biografia);
			biografia = "";
		} else if (action.equals("localizacao")) {
			localizacoes.add(localizacao);
			localizacao = "";
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

		if (action.equals("screenName")) {
			screenNames.remove(valor);
		} else if (action.equals("biografia")) {
			biografias.remove(valor);
		} else if (action.equals("localizacao")) {
			localizacoes.remove(valor);
		} else if (action.equals("termo")) {
			termos.remove(valor);
		}
	}
	
	
	public void buscar(ActionEvent ev){
		
	}
	

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getTermo() {
		return termo;
	}

	public void setTermo(String termo) {
		this.termo = termo;
	}

	public Calendar getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Calendar getDataFim() {
		return dataFim;
	}

	public void setDataFim(Calendar dataFim) {
		this.dataFim = dataFim;
	}

	public List<String> getScreenNames() {
		return screenNames;
	}

	public void setScreenNames(List<String> screenNames) {
		this.screenNames = screenNames;
	}

	public List<String> getBiografias() {
		return biografias;
	}

	public void setBiografias(List<String> biografias) {
		this.biografias = biografias;
	}

	public List<String> getLocalizacoes() {
		return localizacoes;
	}

	public void setLocalizacoes(List<String> localizacoes) {
		this.localizacoes = localizacoes;
	}

	public List<String> getTermos() {
		return termos;
	}

	public void setTermos(List<String> termos) {
		this.termos = termos;
	}

}
