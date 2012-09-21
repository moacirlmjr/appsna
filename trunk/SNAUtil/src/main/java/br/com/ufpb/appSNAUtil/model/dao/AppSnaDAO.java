package br.com.ufpb.appSNAUtil.model.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public interface AppSnaDAO<T> extends Serializable {

	public abstract void create(T objeto) throws Exception;
	
	public abstract void create(List<T> objeto) throws Exception;
	
	public abstract void update(T objeto) throws Exception;

	public abstract T findById(Long id) throws Exception;

	public abstract List<T> list() throws Exception;

	public abstract void remove(T objeto) throws Exception;
	
}
