package br.com.ufpb.appSNA.model.beans;

import java.util.ArrayList;


public class MyNode {

		private int id;
		private String nome;
		private ArrayList<Long> listadeAmigos;

		public MyNode(int id, String nome ) {
			this.id = id;
			this.nome=nome;
			this.listadeAmigos = new ArrayList<Long>();
		}
		
		
		public int getId() {
			return id;
		}



		public void setId(int id) {
			this.id = id;
		}
		
		
		public String getNome() {
			return nome;
		}


		public void setNome(String nome) {
			this.nome = nome;
		}


		public String toString() {
			return "V" + id;
		}

	}


