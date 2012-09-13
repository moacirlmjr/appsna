package br.com.ufpb.appSNAUtil.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class XMLUtil {
	
	public static StringBuffer arquivo = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").
			append("\n<!-- GraphML gerado automaticamente pela AppSNA -->").
			append("\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">");
	
	
	//M�todo utilizado para gerar o cabecalho do XML
	public void generateHeader(boolean isDirected){		
		arquivo.append("\n\t<graph edgedefault=\"").append(isDirected == true ? "Directed" : "Undirected").append("\">").
				append("\n\n\t\t<!-- data schema -->").
				append("\n\t\t<key id=\"name\" for=\"node\" attr.name=\"name\" attr.type=\"string\"/>").
				append("\n\t\t<key id=\"gender\" for=\"node\" attr.name=\"gender\" attr.type=\"string\"/>").
				append("\n\n\t\t<!-- nodes -->  ");			
	}

   

    //M�todo utilizado para gerar os nodos, ser� passado um id, para correla��o nas arestas.    
    public static void generateNodes(long userId, String name, String gender){    	
           arquivo.append("\n\t\t<node id=\"").append(userId).append("\">\n\t\t\t").
           append("<data key=\"name\">").append(name).append("</data>").
           append("\n\t\t\t<data key=\"gender\">").append(gender).append("</data>\n\t\t</node>");
    }
    
    //M�todo sobrescrito.    
    public static void generateNodes(long userId, String name){
    	   arquivo.append("\n\t\t<node id=\"").append(userId).append("\">").
    	   append("\n\t\t\t<data key=\"name\">").append(name).append("</data>").
    	   append("\n\t\t</node>");
    }

   

    //Nessa m�todo ser�o constru�dos as arestas correspondes ao id do usu�rio de origem (idSource) ao id do usu�rio de destino (idTarget).
    public static void generateEdges(long idSource, long idTarget){
           arquivo.append("\n\t\t<edge source=\"").append(idSource).append("\" target=\"").append(idTarget).
           append("\"></edge>\n\t\t\t");
    }

    
    //Metodo sobrescrito
    public static void generateEdges(int idSource, int idTarget, double weight, double capacity){
           arquivo.append("\n\t\t<edge source=\"").append(idSource).append("\" target=\"").append(idTarget)
           .append("<data key=\"weight\">").append(weight).append("</data>\n\t\t</node>")
           .append("<data key=\"capacity\">").append(capacity).append("</data>\n\t\t</node>")
           .append("\"></edge>\n\t\t\t");
    }

	//Metodo para fechar o arquivo
    public static void fechaArquivo(){
           arquivo.append("\n\n\t</graph>\n</graphml>");
    }

   

    //M�todo utilizado para salvar o arquivo no disco     
    public static void salvarXML(String path){

           try {
                   BufferedWriter out = new BufferedWriter(new FileWriter(path));
                   out.write(arquivo.toString());
                   out.flush();
                   out.close();
           } catch (IOException e) {
                   e.printStackTrace();
                   System.err.println("Erro ao salvar arquivo...");
                   System.exit(0);
           }

    }

}
