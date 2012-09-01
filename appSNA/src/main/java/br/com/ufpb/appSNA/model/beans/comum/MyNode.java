package br.com.ufpb.appSNA.model.beans.comum;


public class MyNode {

		private int id;

		public MyNode(int id) {
			this.id = id;
		}
		
		
		public int getId() {
			return id;
		}



		public void setId(int id) {
			this.id = id;
		}



		public String toString() {
			return "V" + id;
		}

	}


