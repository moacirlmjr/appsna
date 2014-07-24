package br.com.puc.snaTwitterWeb.controller.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.puc.appSNA.model.beans.Usuario;
import br.com.puc.appSNA.model.dao.UsuarioDAO;
import br.com.puc.appSNA.model.dao.UsuarioDAOImpl;
import br.com.puc.appSNA.util.AppSNALog;

@FacesConverter("userConverter")
public class UsuarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		try {
			if (value != null && value.trim().length() > 0) {
				UsuarioDAO uDAO = new UsuarioDAOImpl();
				Usuario u = uDAO.findByScreenName(value);
				return u;
			}
		} catch (Exception e) {
			AppSNALog.error(e.toString());
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Usuario) object).getScreename());
		} else {
			return null;
		}
	}
}
