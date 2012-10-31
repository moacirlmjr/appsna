package br.com.ufpb.appSNA.util;

import br.com.ufpb.appSNA.model.beans.Relacionamento;
import br.com.ufpb.appSNA.model.beans.SNAElement;
import br.com.ufpb.appSNA.model.dao.RelacionamentoDAO;
import br.com.ufpb.appSNA.model.dao.RelacionamentoDAOImpl;
import br.com.ufpb.appSNA.model.dao.SNAElementDAO;
import br.com.ufpb.appSNA.model.dao.SNAElementDAOImpl;
import br.com.ufpb.appSNAUtil.util.AppSNALog;

public class Sincronizacao {
	
	public static boolean verificarChaveUsuario (Long id_usuario){
		SNAElementDAO snaDAO = new SNAElementDAOImpl();
		SNAElement elem = null;
		try {
			elem = snaDAO.findById(id_usuario);
		} catch (Exception e) {
			AppSNALog.error(e.toString());
			e.printStackTrace();			
		}
		
		if(elem!=null){
			return true;
		}else{
			return false;
		}
		
	}
	
	public static boolean sincronizaUsuario (SNAElement usuario){
		SNAElementDAO snaDAO = new SNAElementDAOImpl();
		boolean teste=false;
		
		if(!verificarChaveUsuario(usuario.getId_usuario())){
			try {
				snaDAO.create(usuario);
				teste=true;
			} catch (Exception e) {
				teste=false;
				AppSNALog.error(e.toString());
				e.printStackTrace();
			}			
		}else if(verificarChaveUsuario(usuario.getId_usuario())){
			return teste=true;
		}else{
			teste=false;
		}
		return teste;
	}
	
	
	
	public static boolean verificarRelacionamento (Long id_relacionamento){
		RelacionamentoDAO relDAO = new RelacionamentoDAOImpl();
		Relacionamento rel = null;
		try {
			rel = relDAO.findById(id_relacionamento);
		} catch (Exception e) {
			AppSNALog.error(e.toString());
			e.printStackTrace();			
		}
		
		if(rel!=null){
			return true;
		}else{
			return false;
		}
		
	}
	
	
	public static boolean verificarRelacionamento (Long id_source, Long id_target){
		RelacionamentoDAO relDAO = new RelacionamentoDAOImpl();
		Relacionamento rel = null;
		try {
			rel = relDAO.findByIds(id_source, id_target);
		} catch (Exception e) {
			AppSNALog.error(e.toString());
			e.printStackTrace();			
		}
		
		if(rel!=null){
			return true;
		}else{
			return false;
		}
		
	}
	
	public static boolean sincronizaRelacionamento (Relacionamento rel){
		RelacionamentoDAO relDAO = new RelacionamentoDAOImpl();
		boolean teste=false;
		
		if(!verificarChaveUsuario(rel.getId_relacionamento())){
			try {
				relDAO.create(rel);
				teste=true;
			} catch (Exception e) {
				teste=false;
				AppSNALog.error(e.toString());
				e.printStackTrace();
			}			
		}else if(verificarChaveUsuario(rel.getId_relacionamento())){
			return teste=true;
		}else{
			teste=false;
		}
		return teste;
	}


}
