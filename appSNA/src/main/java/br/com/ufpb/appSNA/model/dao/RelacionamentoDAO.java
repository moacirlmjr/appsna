package br.com.ufpb.appSNA.model.dao;

import br.com.ufpb.appSNA.model.beans.Relacionamento;
import br.com.ufpb.appSNAUtil.model.dao.AppSnaDAO;

public interface RelacionamentoDAO extends AppSnaDAO<Relacionamento> {

	Relacionamento findByIds(Long id_source, Long id_target) throws Exception;

}
