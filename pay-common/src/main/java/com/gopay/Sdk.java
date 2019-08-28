package com.gopay;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Sdk {
	private String uri = "";
	private String accessToken = "";
	private HttpClient client;
	private Hasher hasher;

	public Sdk() {
		this("");
	}

	public Sdk(String uri) {
		this(uri, "");
	}

	public Sdk(String uri, String accessToken) {
		this(uri, accessToken, new HttpClient(), new Hasher());
	}

	public Sdk(String uri, String accessToken, HttpClient client, Hasher hasher) {
		this.uri = uri;
		this.accessToken = accessToken;
		this.client = client;
		this.hasher = hasher;
	}

	public Sdk setUri(String uri) {
		this.uri = uri;

		return this;
	}

	public Sdk setAccessToken(String accessToken) {
		this.accessToken = accessToken;

		return this;
	}

	public Sdk setHttpClient(HttpClient client) {
		this.client = client;

		return this;
	}

	public Sdk setHasher(Hasher hasher) {
		this.hasher = hasher;

		return this;
	}

	public String send(LinkedHashMap<String, String> data)
			throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Authorization", "Bearer " + accessToken);

		return client.post(uri + "/api/transaction", headers, JSON.encode(data));
	}

	public boolean validate(String value, String hashedValue) {
		return hasher.setAccessToken(accessToken).check(value, hashedValue);
	}

	public boolean validate(LinkedHashMap<String, String> value, String hashedValue) {
		return hasher.setAccessToken(accessToken).check(value, hashedValue);
	}
}
