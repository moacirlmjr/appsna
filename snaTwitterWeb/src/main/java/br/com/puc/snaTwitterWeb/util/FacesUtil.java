/*
* Copyright (c) 2011 by Avaty! Tecnologia.
* Avaty! Tecnologia Confidential Proprietary
* This document and the information contained in it is
* CONFIDENTIAL INFORMATION of Avaty! Tecnologia, and shall not
* be used, or published, or disclosed, or disseminated
* outside of Avaty! Tecnologia in whole or in part without
* Avaty! Tecnologia's consent. This document contains trade
* secrets of Avaty! Tecnologia. Reverse engineering of any or
* all of the information in this document is prohibited.
* The copyright notice does not imply publication of
* this document.
*
*/

package br.com.puc.snaTwitterWeb.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIColumn;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FacesUtil {
	
	private static final String CHAVE_BEAN_ACIONADOR = "beanAcionador";

	public static void adicionarAtributoRequest(String chave, Object valor) {

		obterRequest().setAttribute(chave, valor);

	}

	public static void adicionarAtributoSessao(String chave, Object valor) {

		obterSessao().setAttribute(chave, valor);

	}

	public static Object obterAtributoRequest(String chave) {

		return obterRequest().getAttribute(chave);
	}

	public static Object obterAtributoSessao(String chave) {

		return obterSessao().getAttribute(chave);
	}

	public static String obterCaminhoReal(String caminho) {

		ServletContext sc = (ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext();
		return sc.getRealPath(caminho);
	}

	public static UIComponent obterComponente(String idComponente) {

		return obterFacesContext().getViewRoot().findComponent(idComponente);
	}

	public static FacesContext obterFacesContext() {

		return FacesContext.getCurrentInstance();
	}

	public static Object obterManagedBean(String nomeManagedBean) {

		return obterFacesContext().getApplication().createValueBinding(
				"#{" + nomeManagedBean + "}").getValue(obterFacesContext());
	}
	
	
	public static String obterAtributoComoString (String idAtributo){
				
		return (String)obterFacesContext().getExternalContext().
				getRequestParameterMap().get(idAtributo);
	}
	
	public static String obterNomeContexto() {

		return obterFacesContext().getExternalContext().getRequestContextPath();
	}

	private static ResourceBundle obterResourceBundle() {

		String bundleName = obterFacesContext().getApplication()
				.getMessageBundle();

		ResourceBundle bundle = ResourceBundle.getBundle(bundleName);

		return bundle;
	}

	public static HttpServletRequest obterRequest() {

		return (HttpServletRequest) obterFacesContext().getExternalContext()
				.getRequest();
	}

	public static HttpSession obterSessao() {

		return (HttpSession) obterFacesContext().getExternalContext()
				.getSession(false);
	}

	public static String obterTexto(String key) {

		String texto = null;

		try {
			texto = obterResourceBundle().getString(key);
		} catch (MissingResourceException e) {
			texto = key;
		}

		return texto;
	}

	public static String obterTexto(String key, Object params[]) {

		String texto = obterTexto(key);

		if (params != null) {
			MessageFormat mf = new MessageFormat(texto);
			texto = mf.format(params, new StringBuffer(), null).toString();
		}

		return texto;
	}
	
	
	public static List obterColunas(HtmlDataTable table) {
		List columns = new ArrayList();

		for (int i = 0; i < table.getChildCount(); i++) {
			UIComponent child = (UIComponent) table.getChildren().get(i);
			if (child instanceof UIColumn
			        && !(child.getChildren().size() == 0 || child.getChildren()
			                .get(0) instanceof EditableValueHolder)) {
				columns.add(child);
			}
		}
		return columns;
	} 

	public static void registrarAviso(String key) {

		String texto = obterTexto(key);
		registrarFacesMessage(texto, FacesMessage.SEVERITY_WARN);
	}

	public static void registrarAviso(String key, String... param) {

		String texto = obterTexto(key, param);
		registrarFacesMessage(texto, FacesMessage.SEVERITY_WARN);
	}

	public static String obterBeanAcionador() {

		return obterAtributoRequest(CHAVE_BEAN_ACIONADOR).toString();
	}

	public static void registrarBeanAcionador(ActionEvent evento) {

		UICommand acionador = (UICommand) evento.getSource();
		MethodBinding acao = acionador.getAction();
		String nomeManagedBeanAcionador;
		if (acao == null) {
			nomeManagedBeanAcionador = null;
		} else {
			String expressao = acionador.getAction().getExpressionString();
			nomeManagedBeanAcionador = obterNomeManagedBean(expressao);
			if (expressao.equals(nomeManagedBeanAcionador)) {
				nomeManagedBeanAcionador = (String) acionador.getAttributes()
						.get("nomeBean");
			}
		}
		adicionarAtributoRequest(CHAVE_BEAN_ACIONADOR, nomeManagedBeanAcionador);
	}

	public static String obterNomeManagedBean(String expressao) {

		String patternStr = "#\\{(.*)\\.";

		// Compile and use regular expression
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(expressao);
		boolean matchFound = matcher.find();

		if (matchFound) {
			return matcher.group(1);
		}
		return expressao;
	}

	public static void registrarErro(String key) {

		String texto = obterTexto(key);
		registrarFacesMessage(texto, FacesMessage.SEVERITY_ERROR);
	}

	public static void registrarErro(String key, String... param) {

		String texto = obterTexto(key, param);
		registrarFacesMessage(texto, FacesMessage.SEVERITY_ERROR);
	}

	public static void registrarErroFatal(String key, String... param) {

		String texto = obterTexto(key, param);
		registrarFacesMessage(texto, FacesMessage.SEVERITY_FATAL);
	}

	public static void registrarFacesMessage(String texto,
			FacesMessage.Severity severidade) {

		FacesMessage mensagem = new FacesMessage();

		mensagem.setSummary(texto);
		mensagem.setSeverity(severidade);

		obterFacesContext().addMessage(null, mensagem);
	}

	public static void registrarMensagem(String key) {

		String texto = obterTexto(key);
		registrarFacesMessage(texto, FacesMessage.SEVERITY_INFO);
	}

	public static void registrarMensagem(String key, String... param) {

		String texto = obterTexto(key, param);
		registrarFacesMessage(texto, FacesMessage.SEVERITY_INFO);
	}

	public static void removerAtributoSessao(String chave) {

		obterSessao().removeAttribute(chave);

	}

	public static HttpSession getSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) facesContext
				.getExternalContext().getRequest();
		HttpSession session = request.getSession();
		return session;
	}

	public static HttpServletResponse getResponse() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();
		return response;
	}

	public static HttpServletRequest getRequest() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) facesContext
				.getExternalContext().getRequest();
		return request;
	}

	public static String getPath() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) facesContext
				.getExternalContext().getRequest();
		String path = "";
		if(request.getLocalPort()==8080){
			path+="http://";
		}else if(request.getLocalPort()==8443){
			path+="https://";
		}

		path += request.getLocalAddr() + ":" + request.getLocalPort()
				+ request.getContextPath();

		return path;
	}
	
	
	public static String obterParametro(String nomeParametro){
		return  (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(nomeParametro);
	}
	
}
