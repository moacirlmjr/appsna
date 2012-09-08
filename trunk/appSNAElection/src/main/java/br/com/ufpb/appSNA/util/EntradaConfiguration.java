package br.com.ufpb.appSNA.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

public class EntradaConfiguration {
	private ResourceBundle resource;

	public EntradaConfiguration() throws IOException {
		resource = ResourceBundle.getBundle("entrada");

	}

	public List<String> getKeys() {
		Enumeration<String> e = resource.getKeys();
		List<String> list = new ArrayList<String>();
		while (e.hasMoreElements()) {
			list.add(e.nextElement());
		}
		Collections.sort(list);
		return list;
	}

	public String getEntrada(String key) {
		return resource.getString(key);
	}

}
