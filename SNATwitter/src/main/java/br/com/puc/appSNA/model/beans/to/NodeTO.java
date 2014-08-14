package br.com.puc.appSNA.model.beans.to;

public class NodeTO {

	private String id_node;
	private String nome;
	private Integer grau;
	private Integer grauEntrada;
	private Integer grauSaida;
	private Double pageRank;
	private Double betweenness;
	private Double closeness;
	private Double eccentricity;
	private Integer Modularidade;

	public NodeTO() {

	}

	public String getId_node() {
		return id_node;
	}

	public void setId_node(String id_node) {
		this.id_node = id_node;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getGrau() {
		return grau;
	}

	public void setGrau(Integer grau) {
		this.grau = grau;
	}

	public Integer getGrauEntrada() {
		return grauEntrada;
	}

	public void setGrauEntrada(Integer grauEntrada) {
		this.grauEntrada = grauEntrada;
	}

	public Integer getGrauSaida() {
		return grauSaida;
	}

	public void setGrauSaida(Integer grauSaida) {
		this.grauSaida = grauSaida;
	}

	public Double getPageRank() {
		return pageRank;
	}

	public void setPageRank(Double pageRank) {
		this.pageRank = pageRank;
	}

	public Double getBetweenness() {
		return betweenness;
	}

	public void setBetweenness(Double betweenness) {
		this.betweenness = betweenness;
	}

	public Double getCloseness() {
		return closeness;
	}

	public void setCloseness(Double closeness) {
		this.closeness = closeness;
	}

	public Double getEccentricity() {
		return eccentricity;
	}

	public void setEccentricity(Double eccentricity) {
		this.eccentricity = eccentricity;
	}

	public Integer getModularidade() {
		return Modularidade;
	}

	public void setModularidade(Integer modularidade) {
		Modularidade = modularidade;
	}

}
