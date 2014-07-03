package br.com.puc.appSNA.model.dao;

import java.io.Serializable;
import java.util.List;

public interface AppSnaDAO<T> extends Serializable {

	public abstract Long create(T objeto) throws Exception;
	
	public abstract void create(List<T> objeto) throws Exception;
	
	public abstract Long update(T objeto) throws Exception;

	public abstract T findById(Long id) throws Exception;

	public abstract List<T> list() throws Exception;

	public abstract void remove(T objeto) throws Exception;
	
}