package br.com.ufpb.appSNA.model.beans;

import br.com.ufpb.appSNAUtil.model.beans.comum.AppSNAEntityMaster;

public class Relacionamento extends AppSNAEntityMaster { 
	
	private static final long serialVersionUID = 1L;
	
	private long id_relacionamento;
	private long id_source;
	private long id_target;
		

	public long getId_relacionamento() {
		return id_relacionamento;
	}

	public void setId_relacionamento(long id_relacionamento) {
		this.id_relacionamento = id_relacionamento;
	}

	public long getId_source() {
		return id_source;
	}

	public void setId_source(long id_source) {
		this.id_source = id_source;
	}

	public long getId_target() {
		return id_target;
	}

	public void setId_target(long id_target) {
		this.id_target = id_target;
	}

}
