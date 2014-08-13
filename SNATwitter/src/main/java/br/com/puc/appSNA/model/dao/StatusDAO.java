package br.com.puc.appSNA.model.dao;

import java.util.List;

import br.com.puc.appSNA.model.beans.Filtro;
import br.com.puc.appSNA.model.beans.Status;
import br.com.puc.appSNA.model.beans.to.MencaoTO;

public interface StatusDAO extends AppSnaDAO<Status> {
	
	public List<MencaoTO> listByFiltro(Filtro filtro) throws Exception;
	
	public List<Status> listStatusByFiltro(Filtro filtro) throws Exception;

}
