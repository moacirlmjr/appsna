package br.com.ufpb.appSNA.model.beans;

import java.util.List;


public class MyNode {

		private long id;
		private String nome;
		private List<Long> listadeAmigos;
		

		public MyNode(long id, String nome, List<Long> list ) {
			this.id = id;
			this.nome=nome;
			this.listadeAmigos = list;
		}
		
		public MyNode(int id, String nome) {
			this.id = id;
			this.nome=nome;			
		}
		
		public MyNode(int id ) {
			this.id = id;			
		}
		
		
		public long getId() {
			return id;
		}



		public void setId(long id) {
			this.id = id;
		}
		
		
		public String getNome() {
			return nome;
		}


		public void setNome(String nome) {
			this.nome = nome;
		}
				


		public List<Long> getListadeAmigos() {
			return listadeAmigos;
		}


		public void setListadeAmigos(List<Long> listadeAmigos) {
			this.listadeAmigos = listadeAmigos;
		}


		public String toString() {
			return "V" + id;
		}

	}

