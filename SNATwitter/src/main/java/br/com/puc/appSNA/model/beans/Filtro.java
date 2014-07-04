package br.com.puc.appSNA.model.beans;

import java.util.Date;

public class Filtro extends AppSNAEntityMaster {

	private Date dataCriacao;
	private String screenNames;
	private String biografias;
	private String localizacoes;
	private String termosStatus;
	private Date dataInicio;
	private Date dataFim;
	private String endGraphml;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}
