package br.com.puc.appSNA.model.beans.to;

import java.io.Serializable;

public class MencaoTO implements Serializable{

	private Long idTwitterOrigem;
	private Long idTwitterDestino;
	private Integer qteMencoes;

	public Long getIdTwitterOrigem() {
		return idTwitterOrigem;
	}

	public void setIdTwitterOrigem(Long idTwitterOrigem) {
		this.idTwitterOrigem = idTwitterOrigem;
	}

	public Long getIdTwitterDestino() {
		return idTwitterDestino;
	}

	public void setIdTwitterDestino(Long idTwitterDestino) {
		this.idTwitterDestino = idTwitterDestino;
	}

	public Integer getQteMencoes() {
		return qteMencoes;
	}

	public void setQteMencoes(Integer qteMencoes) {
		this.qteMencoes = qteMencoes;
	}

}
