package com.gopay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class HttpClient {
	public String post(String uri, Map<String, String> headers, String body)
			throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
		TrustManager[] tm = { new NonAuthenticationX509TrustManager() };
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		SSLSocketFactory ssf = sslContext.getSocketFactory();

		HttpsURLConnection connection = (HttpsURLConnection) createConnection(uri);
		connection.setSSLSocketFactory(ssf);
		connection.setRequestMethod("GET");
		addHeaders(connection, headers);

		if (body != null) {
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(body.getBytes("UTF-8"));
			outputStream.flush();
		}

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

		StringBuffer buffer = new StringBuffer();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			buffer.append(new String(line.getBytes(), "UTF-8") + "\n");
		}
		bufferedReader.close();

		return buffer.toString();
	}

	private HttpURLConnection createConnection(String uri) throws IOException {
		URL url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);

		return connection;
	}

	private void addHeaders(HttpURLConnection connection, Map<String, String> headers) {
		if (headers == null) {
			return;
		}

		for (Entry<String, String> header : headers.entrySet()) {
			connection.addRequestProperty(header.getKey(), header.getValue());
		}
	}
}
