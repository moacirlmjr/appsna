package br.com.puc.snaTwitterWeb.controller.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import br.com.puc.appSNA.model.beans.Filtro;
import br.com.puc.appSNA.model.beans.Filtro.TipoDistribuicao;
import br.com.puc.appSNA.model.beans.Filtro.TipoRankColor;
import br.com.puc.appSNA.model.beans.Filtro.TipoRankSize;
import br.com.puc.appSNA.model.dao.FiltroDAO;
import br.com.puc.appSNA.model.dao.FiltroDAOImpl;
import br.com.puc.appSNA.util.AppSNALog;
import br.com.puc.snaTwitterWeb.threads.GerarGraphMLByFiltro;
import br.com.puc.snaTwitterWeb.util.FacesUtil;

@ManagedBean(name = "pesquisaController")
@SessionScoped
public class PesquisaController implements Serializable {

	private String screenName;
	private String biografia;
	private String localizacao;
	private String termo;
	private Date dataInicio;
	private Date dataFim;

	private boolean grau;
	private boolean pageRank;
	private boolean centralidade;
	private boolean direcionado;
	private boolean modularity;

	private TipoRankColor tipoRankColor;
	private TipoRankSize tipoRankSize;
	private TipoDistribuicao tipoDistribuicao;

	private Filtro filtro;

	private List<String> screenNames;
	private List<String> biografias;
	private List<String> localizacoes;
	private List<String> termos;

	private static final int ACTIVES_TASK = 2;
	private static final int NTHREADS = Runtime.getRuntime()
			.availableProcessors() * 8;
	private static final ExecutorService exec = Executors
			.newFixedThreadPool(NTHREADS);

	public PesquisaController() {
		screenNames = new ArrayList<>();
		biografias = new ArrayList<>();
		localizacoes = new ArrayList<>();
		termos = new ArrayList<>();
	}
	
	public List<SelectItem> getSelectRankByColor(){
		List<SelectItem> list = new ArrayList<>();
		for(TipoRankColor tp: TipoRankColor.values()){
			list.add(new SelectItem(tp,tp.name()));
		}
		return list;
	}
	
	public List<SelectItem> getSelectRankBySize(){
		List<SelectItem> list = new ArrayList<>();
		for(TipoRankSize tp: TipoRankSize.values()){
			list.add(new SelectItem(tp,tp.name()));
		}
		return list;
	}
	
	public List<SelectItem> getSelectDistribuicao(){
		List<SelectItem> list = new ArrayList<>();
		for(TipoDistribuicao tp: TipoDistribuicao.values()){
			list.add(new SelectItem(tp,tp.name()));
		}
		return list;
	}

	public void adicionar(ActionEvent ev) {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String action = params.get("action");

		if (action.equals("screenName")) {
			screenNames.add(screenName);
			screenName = "";
			FacesUtil.registrarFacesMessage("Screenname Adicionado",
					FacesMessage.SEVERITY_INFO);
		} else if (action.equals("biografia")) {
			biografias.add(biografia);
			biografia = "";
			FacesUtil.registrarFacesMessage("Biografia Adicionada",
					FacesMessage.SEVERITY_INFO);
		} else if (action.equals("localizacao")) {
			localizacoes.add(localizacao);
			localizacao = "";
			FacesUtil.registrarFacesMessage("Localização Adicionada",
					FacesMessage.SEVERITY_INFO);
		} else if (action.equals("termo")) {
			termos.add(termo);
			termo = "";
			FacesUtil.registrarFacesMessage("Termo Adicionado",
					FacesMessage.SEVERITY_INFO);
		}
	}

	public void remover(ActionEvent ev) {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String action = params.get("action");
		String valor = params.get("valor");

		if (action.equals("screenName")) {
			screenNames.remove(valor);
			FacesUtil.registrarFacesMessage("Screenname Removido",
					FacesMessage.SEVERITY_INFO);
		} else if (action.equals("biografia")) {
			biografias.remove(valor);
			FacesUtil.registrarFacesMessage("Biografia Removida",
					FacesMessage.SEVERITY_INFO);
		} else if (action.equals("localizacao")) {
			localizacoes.remove(valor);
			FacesUtil.registrarFacesMessage("Localização Removida",
					FacesMessage.SEVERITY_INFO);
		} else if (action.equals("termo")) {
			termos.remove(valor);
			FacesUtil.registrarFacesMessage("Termo Removido",
					FacesMessage.SEVERITY_INFO);
		}
	}

	public String buscar() {
		filtro = new Filtro();
		filtro.setDataCriacao(new Date());
		filtro.setEndGraphml("rede_" + filtro.getDataCriacao().getTime()
				+ ".gexf");
		filtro.setDataInicio(dataInicio);
		filtro.setDataFim(dataFim);
		filtro.setStatus("CONSULTANDO");

		try {
			if (screenNames.size() != 0) {
				filtro.setScreenNames(screenNames.toString()
						.replaceAll(", ", ",").replaceAll("\\[", "")
						.replaceAll("\\]", ""));
			}

			if (biografias.size() != 0) {
				filtro.setBiografias(biografias.toString()
						.replaceAll(", ", ",").replaceAll("\\[", "")
						.replaceAll("\\]", ""));
			}

			if (localizacoes.size() != 0) {
				filtro.setLocalizacoes(localizacoes.toString()
						.replaceAll(", ", ",").replaceAll("\\[", "")
						.replaceAll("\\]", ""));
			}

			if (termos.size() != 0) {
				filtro.setTermosStatus(termos.toString().replaceAll(", ", ",")
						.replaceAll("\\[", "").replaceAll("\\]", ""));
			}
			filtro.setGrau(grau);
			filtro.setCentralidade(centralidade);
			filtro.setPageRank(pageRank);
			filtro.setModularity(modularity);
			filtro.setDirecionado(direcionado);
			filtro.setTipoRankColor(tipoRankColor);
			filtro.setTipoRankSize(tipoRankSize);
			filtro.setTipoDistribuicao(tipoDistribuicao);

			screenNames = new ArrayList<>();
			biografias = new ArrayList<>();
			localizacoes = new ArrayList<>();
			termos = new ArrayList<>();
			
			grau = false;
			centralidade = false;
			pageRank = false;
			direcionado = false;
			modularity = false;
			tipoDistribuicao = null;
			tipoRankColor = null;
			tipoRankSize = null;
			tipoDistribuicao = null;
			
			dataInicio = null;
			dataFim = null;
			
			FiltroDAO filtroDAO = new FiltroDAOImpl();
			Long id = filtroDAO.create(filtro);
			filtro.setId(id);


			GerarGraphMLByFiltro parser = new GerarGraphMLByFiltro();
			parser.setFiltro(filtro);
			exec.submit(parser);


			FacesUtil.registrarFacesMessage(
					"Filtro Salvo com Sucesso. Em breve sua rede será gerada",
					FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			FacesUtil.registrarFacesMessage(
					"Ocorreu um erro ao salvar o filtro",
					FacesMessage.SEVERITY_ERROR);
			AppSNALog.error(e);
		}
		return "paginas/listFiltros.jsf";
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

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
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

	private static Integer getQteThreadsRunning() {
		return Integer.parseInt(exec.toString().split(",")[ACTIVES_TASK]
				.split("=")[1].replace(" ", ""));
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public boolean isGrau() {
		return grau;
	}

	public void setGrau(boolean grau) {
		this.grau = grau;
	}

	public boolean isPageRank() {
		return pageRank;
	}

	public void setPageRank(boolean pageRank) {
		this.pageRank = pageRank;
	}

	public boolean isCentralidade() {
		return centralidade;
	}

	public void setCentralidade(boolean centralidade) {
		this.centralidade = centralidade;
	}

	public boolean isDirecionado() {
		return direcionado;
	}

	public void setDirecionado(boolean direcionado) {
		this.direcionado = direcionado;
	}

	public TipoRankColor getTipoRankColor() {
		return tipoRankColor;
	}

	public void setTipoRankColor(TipoRankColor tipoRankColor) {
		this.tipoRankColor = tipoRankColor;
	}

	public TipoRankSize getTipoRankSize() {
		return tipoRankSize;
	}

	public void setTipoRankSize(TipoRankSize tipoRankSize) {
		this.tipoRankSize = tipoRankSize;
	}

	public boolean isModularity() {
		return modularity;
	}

	public void setModularity(boolean modularity) {
		this.modularity = modularity;
	}

	public TipoDistribuicao getTipoDistribuicao() {
		return tipoDistribuicao;
	}

	public void setTipoDistribuicao(TipoDistribuicao tipoDistribuicao) {
		this.tipoDistribuicao = tipoDistribuicao;
	}
}
