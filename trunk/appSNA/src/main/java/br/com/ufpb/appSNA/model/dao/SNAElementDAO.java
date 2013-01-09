package br.com.ufpb.appSNA.model.dao;

import br.com.ufpb.appSNA.model.beans.SNAElement;
import br.com.ufpb.appSNA.model.beans.to.InadimplenciaTO;
import br.com.ufpb.appSNAUtil.model.dao.AppSnaDAO;

public interface SNAElementDAO extends AppSnaDAO<SNAElement> {

	public InadimplenciaTO verificarDadosInadimplencia(Long id_usuario)
			throws Exception;

	public Integer retornarQtdeAmigosNegativos(Long id_usuario)
			throws Exception;

}
