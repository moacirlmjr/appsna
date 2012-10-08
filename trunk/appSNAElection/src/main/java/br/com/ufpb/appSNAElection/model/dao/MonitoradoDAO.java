package br.com.ufpb.appSNAElection.model.dao;

import java.util.List;

import br.com.ufpb.appSNAElection.model.beans.Monitorado;
import br.com.ufpb.appSNAElection.model.beans.to.RelatorioOcorrenciasTO;
import br.com.ufpb.appSNAUtil.model.dao.AppSnaDAO;

public interface MonitoradoDAO extends AppSnaDAO<Monitorado> {

	public List<RelatorioOcorrenciasTO> listRelatorioOcorrencia() throws Exception;
	public List<Monitorado> listMonitorandos() throws Exception;

}
