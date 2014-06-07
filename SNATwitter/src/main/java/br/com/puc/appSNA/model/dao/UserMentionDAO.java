package br.com.puc.appSNA.model.dao;

import java.util.List;

import br.com.puc.appSNA.model.beans.UserMention;
import br.com.puc.appSNA.model.beans.to.MentionTO;

public interface UserMentionDAO extends AppSnaDAO<UserMention> {
	
	public List<MentionTO> listarMencionados(long id_usuario) throws Exception;

}
