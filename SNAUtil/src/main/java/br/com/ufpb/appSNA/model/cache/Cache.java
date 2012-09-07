package br.com.ufpb.appSNA.model.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import redis.clients.jedis.Jedis;
import br.com.ufpb.appSNA.util.Constantes;

public class Cache<T> {
	private static final byte[] APP_SNA_KEY_RESULTS = "appSna:keyResults".getBytes();
	private Jedis jedis = new Jedis(Constantes.ipRedis,443);
	
	
	public void gravarProdutosPrincipais(List<T> list) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream outObject = new ObjectOutputStream(out);
		outObject.writeObject(list);
		outObject.flush();
		outObject.close();
		
		this.jedis.set(APP_SNA_KEY_RESULTS, out.toByteArray());
		
	}
	
	public List<T> lerProdutosPrincipais() throws IOException, ClassNotFoundException{
		byte[] dados = this.jedis.get(APP_SNA_KEY_RESULTS);
		if(dados == null){
			return null;
		}
		ByteArrayInputStream input = new ByteArrayInputStream(dados);
		ObjectInputStream objectInput = new ObjectInputStream(input);
		return (List<T>) objectInput.readObject();
	}
	
	public void limparProdutos(){
		this.jedis.del(APP_SNA_KEY_RESULTS);
	}
}
