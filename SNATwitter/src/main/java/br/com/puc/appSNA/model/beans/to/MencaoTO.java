package br.com.puc.appSNA.model.beans.to;

import java.io.Serializable;

public class MencaoTO implements Serializable {

	private Long idTwitterOrigem;
	private String origem;
	private Long idTwitterDestino;
	private String destino;
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

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

}
