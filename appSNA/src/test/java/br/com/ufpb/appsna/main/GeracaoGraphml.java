package br.com.ufpb.appsna.main;

import java.util.LinkedList;
import java.util.List;

import br.com.ufpb.appSNA.model.beans.SNAElement;
import br.com.ufpb.appSNA.model.dao.SNAElementDAO;
import br.com.ufpb.appSNA.model.dao.SNAElementDAOImpl;

public class GeracaoGraphml {
	
	public static void main(String[] args) {
		
		SNAElementDAO snaDAO = new SNAElementDAOImpl();
		List<SNAElement> listaElem = new LinkedList<SNAElement>();
		
		try {
			listaElem = snaDAO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
