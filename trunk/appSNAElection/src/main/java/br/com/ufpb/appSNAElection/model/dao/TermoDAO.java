package br.com.ufpb.appSNAElection.model.dao;

import br.com.ufpb.appSNAElection.model.beans.Monitorado;
import br.com.ufpb.appSNAElection.model.beans.Termo;
import br.com.ufpb.appSNAUtil.model.dao.AppSnaDAO;

public interface TermoDAO extends AppSnaDAO<Termo> {

	public Monitorado getMonitoradoByTermo(String termo) throws Exception;
	public Termo getTermoByConteudo(String conteudo) throws Exception;
	
}
