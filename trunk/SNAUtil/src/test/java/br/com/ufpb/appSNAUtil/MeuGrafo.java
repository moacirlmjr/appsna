package br.com.ufpb.appSNAUtil;

import br.com.ufpb.appSNAUtil.util.XMLUtil;

public class MeuGrafo {

	// Alguns amigos 
	private String[] amigos = { "Samuel", "Alba", "Tux", "Mili", "Duke", "KK", "Sun", "Lua" };

	// Alguns famosos
	private String[] famosos = { "Dijk", "Tannen", "Linu", "Richard", "Bill", "C.S.Lewi" };

	public MeuGrafo(String nomeDoMeuGrafo, String path, boolean direcionado) {
		criaCabecalho(direcionado);
		criarNodos();
		criarArestas();
		criaArquivo(nomeDoMeuGrafo);
		salvarArquivo(path);
	}
	
	
	public void criaCabecalho(boolean direcionado){
		XMLUtil.generateHeader(direcionado);
	}

	private void criarNodos() {
		
		

		// Vamos criar os nodos dos "amigos"
		for (int i = 0; i < amigos.length; i++) {
			XMLUtil.generateNodes(((i * 100) + 1), amigos[i], i % 2 == 0 ? "M" : "F");
		}
		
		XMLUtil.addSpace(2);
		
		// Vamos criar os nodos dos "famosos"
		for (int i = 0; i < famosos.length; i++) {
			XMLUtil.generateNodes(i * 100 + 5, famosos[i], "M");
		}

	}

	private void criarArestas() {

		for (int i = 0; i < amigos.length; i++) {
			for (int j = 0; j < famosos.length; j++) {
				XMLUtil.generateEdges(i * 100 + 1, j * 100 + 5);
			}
		}
	}
	
	private void criaArquivo(String nomeDoMeuGrafo) {
		XMLUtil.fechaArquivo();
		XMLUtil.salvarXML(nomeDoMeuGrafo);

	}
	
	private void salvarArquivo(String path){
		XMLUtil.salvarXML(path);
	}

	public static void main(String[] args) {
		
		new MeuGrafo("grafo.xml", "C:\\Users\\David\\Desktop\\grafo.xml", false);

	}
}








