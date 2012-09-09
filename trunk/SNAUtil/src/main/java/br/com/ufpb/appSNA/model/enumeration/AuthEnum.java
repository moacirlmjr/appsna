package br.com.ufpb.appSNA.model.enumeration;

public enum AuthEnum {
	
	MOACIR_KEY("9D8Q5IFh9KDSUpFgFwVEZg","amWG2soLvbc5Uk9hXYck185cHH4qVZ2vI803avAQw",
			"69753774-BlHvqdHCJHY82UNh4kyVGxdJRrrLexJxh6Abvq43Q","saKgrvdXtv5qjwtlFXAu62IvAGSrh4w2ICmjfMV1yvI"),
	MOACIR_KEY2("F8RHGcJRGR2XFqISA2z5hQ","IiwyOLiHgn38F2Rv5IhhCqlcu4JeUzgYaXHgm2jjo",
			"813772765-FzPTpVuTfXsyva4URjkIA15iEZHQ2fI3XgngVQW3","hHxu03Fqj1umMpWiZP91fTiECCjXpz27tlUnwZ5MGB0"),	
	DANYLLO_KEY("l7ONDm41KV2BVfTMEPQ","SdwHfyMFfNsevFYeoNCCOshHUZEqEcEq6BFqaAZIgw",
			"312660739-lZCZardTnDmFQR1AtmOOeEMO2kIsdtzgMJsRjxcA","n0p2ehUDDN2tfGGJxmfYA4ewtUv22kggWxplIussI"),
	DANYLLO_KEY2("X84jdsF1ngIEB9DONMY0Uw","HT463HXtE0D89UfJfq1qJ7blKwsdR7cqhtC48SJ1o",
			"153829744-cvtw2SmiiTxu153Qpd50AvumKJaYm3u1XApwbpNL","UpCaOka3zyADEXtbLe1mAbnyWbOZy1K5KscWUPh68"),
	ROBERTO_KEY("4oAq4OFALcmPYFfPIYtYw","kHOhrvkY9b8vSpSJsfVLZ1zhE8C8bQjwDVj6x5u6o",
			"308693165-DpE0xj7SC2N8B2WBM13QsEdcS1lkUhzUFW4kxTtk","2Hi6WBOtjy9NJwOOJwRDHVXIx9MJ4aCL0V52Qsj2A"),
	ALISSON_KEY("","","","");
	
	private String consumerToken;
	private String consumerSecret;
	private String accessToken;
	private String accessSecret;
	
	private AuthEnum(String consumerToken, String consumerSecret,
			String accessToken, String accessSecret) {
		this.consumerToken = consumerToken;
		this.consumerSecret = consumerSecret;
		this.accessToken = accessToken;
		this.accessSecret = accessSecret;
	}

	public String getConsumerToken() {
		return consumerToken;
	}

	public void setConsumerToken(String consumerToken) {
		this.consumerToken = consumerToken;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessSecret() {
		return accessSecret;
	}

	public void setAccessSecret(String accessSecret) {
		this.accessSecret = accessSecret;
	}
	
}
