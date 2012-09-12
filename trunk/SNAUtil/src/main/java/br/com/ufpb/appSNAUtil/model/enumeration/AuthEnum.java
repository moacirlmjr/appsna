package br.com.ufpb.appSNAUtil.model.enumeration;

public enum AuthEnum {
	
	MOACIR_KEY("9D8Q5IFh9KDSUpFgFwVEZg","amWG2soLvbc5Uk9hXYck185cHH4qVZ2vI803avAQw",
			"69753774-BlHvqdHCJHY82UNh4kyVGxdJRrrLexJxh6Abvq43Q","saKgrvdXtv5qjwtlFXAu62IvAGSrh4w2ICmjfMV1yvI"),
	MOACIR_KEY2("F8RHGcJRGR2XFqISA2z5hQ","IiwyOLiHgn38F2Rv5IhhCqlcu4JeUzgYaXHgm2jjo",
			"813772765-FzPTpVuTfXsyva4URjkIA15iEZHQ2fI3XgngVQW3","hHxu03Fqj1umMpWiZP91fTiECCjXpz27tlUnwZ5MGB0"),	
	DANYLLO_KEY("l7ONDm41KV2BVfTMEPQ","SdwHfyMFfNsevFYeoNCCOshHUZEqEcEq6BFqaAZIgw",
			"312660739-lZCZardTnDmFQR1AtmOOeEMO2kIsdtzgMJsRjxcA","n0p2ehUDDN2tfGGJxmfYA4ewtUv22kggWxplIussI"),
	DANYLLO_KEY2("X84jdsF1ngIEB9DONMY0Uw","HT463HXtE0D89UfJfq1qJ7blKwsdR7cqhtC48SJ1o",
			"153829744-cvtw2SmiiTxu153Qpd50AvumKJaYm3u1XApwbpNL","UpCaOka3zyADEXtbLe1mAbnyWbOZy1K5KscWUPh68"),
	DANYLLO_KEY3("PEwerwPBLopBrxrdWCdCHQ","oLGCU3gRTNboUPi1VjCORHKyp2h93YnodZpNqekIOOU",
			"312660739-pFFGuPbuZBzOn7yJZJkt6LpVMJs8jhz71eXrwke8","JY4MtWT4VFivqnKU0OZR3pa2KK6akRsFIvy94B3SY"),
	ROBERTO_KEY("4oAq4OFALcmPYFfPIYtYw","kHOhrvkY9b8vSpSJsfVLZ1zhE8C8bQjwDVj6x5u6o",
			"308693165-DpE0xj7SC2N8B2WBM13QsEdcS1lkUhzUFW4kxTtk","2Hi6WBOtjy9NJwOOJwRDHVXIx9MJ4aCL0V52Qsj2A"),
	ROBERTO_KEY2("lJQyCKlxDI24tVtGatUHCQ","GgqBUWdXeRNwAzUnhYE1xMs6eVe093Pp6OUYmZU",
			"817546802-GUTbKwNj4FsejBwEMX0RH8n7oerSRZaMZcrtTyTA","UbDkCEVeLP8OepEgoETvAaU9oyeqUBSsnoptgmaW4"),
	IVAN_KEY("OTiV7xMQlon6i3aO6f1Q","MEZMUNgU2lEnvlmsI3jNMHg8dICQhSfkdQGOyPbf8I",
			"21717777-tYPIJ47eaVg6aKoyFvt4IGjrntyetF5oIr9KFTlMo","iVE9imtDTqwVGz74HPFMFVH4qki8ZxPgg43DlwAU"),
	RICARDO_KEY("AXAltqg4jckIbaFNQpSHnA"," jTqrBExvjvdEVKawM9cbYRdNj8Dsop2CvYLzhgp9P8",
			"49152953-P3jFy2YULNOmvfNFIeCzK3SRIiRSHeZHHEMeuuRnv"," wlLSEwkRdREOsYWlodhqoSXvfMM6sJwAHDyfeRa3ViA"),	
	JANSE_KEY("JDPIydcJMK0kG373daImmA", "WrLFsVU45d9FDDPmyKZtNDXBrQEUfwEoI79uPmQYY",
			"803947891-BiJjpMKWL7jnQFhNyEIkRBPQ65kCh2Ax78tvXc0", "Ve4ORbCz1GEcwX2J42rKS8n6qXwPtKbRe5zAxSBpPYY"),	
	JANSE_KEY2("vQ7Y3oz5dhDjWSL1EjAAg", "CEheY2H5XWjXEpsCKHmhor4hxvRTug75391gTFP2P5w",
			"803947891-Ry4W7HLG7GCJgvAgoHCLsWRayJhhL89Mv2axWIIE", "ZUzTyhNwffRLryIRJGn7ZWCK1Bw43NOyNpKPWlQ4"),	
	JANSE_KEY3("7nnYMAfXqcqvOaKxrVpcA", "3kmzHby0e14zZZP3YghlArEg5ST4RsTJsamrFSb8U", 
			"817565840-lL3cN69UaEKvlfQlqL8dzI9bTQAM4YOTbAsboVlX", "RhzsL7Oxrl8wkFSCxiGznmKDu8d6C3ooOfKRWWQMsQ"),
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
