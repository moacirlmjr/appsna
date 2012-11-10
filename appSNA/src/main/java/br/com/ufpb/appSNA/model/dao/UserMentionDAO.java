package br.com.ufpb.appSNA.model.dao;

import java.util.List;

import br.com.ufpb.appSNA.model.beans.UserMention;
import br.com.ufpb.appSNA.model.beans.to.MentionTO;
import br.com.ufpb.appSNAUtil.model.dao.AppSnaDAO;

public interface UserMentionDAO extends AppSnaDAO<UserMention> {
	
	public List<MentionTO> listarMencionados(long id_usuario) throws Exception;

}
