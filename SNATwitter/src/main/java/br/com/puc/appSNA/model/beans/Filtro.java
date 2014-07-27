package br.com.puc.appSNA.model.beans;

import java.util.Date;

public class Filtro extends AppSNAEntityMaster {

	public enum TipoRankSize {
		RANKINGSIZEGRAU, RANKINGSIZEPAGERANK, RANKINGSIZECENTRAILIDADE;
	}

	public enum TipoRankColor {
		RANKINGCOLORGRAU, RANKINGCOLORPAGERANK, RANKINGCOLORCENTRAILIDADE, RANKINGCOLORMODULARITY;
	}

	private Date dataCriacao;
	private String screenNames;
	private String biografias;
	private String localizacoes;
	private String termosStatus;
	private Date dataInicio;
	private Date dataFim;
	private String endGraphml;
	private String status;

	private boolean grau;
	private boolean pageRank;
	private boolean centralidade;
	private boolean modularity;

	private TipoRankColor tipoRankColor;
	private TipoRankSize tipoRankSize;

	private boolean direcionado;

	public boolean isDirecionado() {
		return direcionado;
	}

	public void setDirecionado(boolean direcionado) {
		this.direcionado = direcionado;
	}

	public boolean isGrau() {
		return grau;
	}

	public void setGrau(boolean grau) {
		this.grau = grau;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getScreenNames() {
		return screenNames;
	}

	public void setScreenNames(String screenNames) {
		this.screenNames = screenNames;
	}

	public String getBiografias() {
		return biografias;
	}

	public void setBiografias(String biografias) {
		this.biografias = biografias;
	}

	public String getLocalizacoes() {
		return localizacoes;
	}

	public void setLocalizacoes(String localizacoes) {
		this.localizacoes = localizacoes;
	}

	public String getTermosStatus() {
		return termosStatus;
	}

	public void setTermosStatus(String termosStatus) {
		this.termosStatus = termosStatus;
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

	public String getEndGraphml() {
		return endGraphml;
	}

	public void setEndGraphml(String endGraphml) {
		this.endGraphml = endGraphml;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String toString() {
		return dataCriacao.toString() + " - Screeenames: " + screenNames
				+ " - Biografias: " + biografias + " - Loc: " + localizacoes
				+ " - Termos: " + termosStatus + " - Situacao: " + status;
	}

	public boolean isModularity() {
		return modularity;
	}

	public void setModularity(boolean modularity) {
		this.modularity = modularity;
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

}
