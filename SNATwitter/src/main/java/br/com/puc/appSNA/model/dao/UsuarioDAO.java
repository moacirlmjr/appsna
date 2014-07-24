package br.com.puc.appSNA.model.dao;

import br.com.puc.appSNA.model.beans.Usuario;


public interface UsuarioDAO extends AppSnaDAO<Usuario> {
	
	public Usuario findByScreenName(String screenName) throws Exception;

}
