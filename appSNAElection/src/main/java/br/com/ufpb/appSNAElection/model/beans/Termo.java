package br.com.ufpb.appSNAElection.model.beans;

import br.com.ufpb.appSNAUtil.model.beans.comum.AppSNAEntityMaster;

public class Termo extends AppSNAEntityMaster {

	private Long monitorado_id;
	private String conteudo;

	public Long getMonitorado_id() {
		return monitorado_id;
	}

	public void setMonitorado_id(Long monitorado_id) {
		this.monitorado_id = monitorado_id;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

}
