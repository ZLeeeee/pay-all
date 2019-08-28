package com.gopay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
import java.util.LinkedHashMap;

/**
 * 
 * Example AccessToken:
 * pLx4agG19Ix7YHzmcC1GtECWXh4oNq0Z0xuBAmO9ui8lGuoEYDzqHxxxxxxx body:
 * {"trade_no":"d2b83654-e914-4f61-a3ce-0d508a3a5768","amount":500,"out_trade_no":"a90dc5bd-e2da-4548-91b2-c06ddefcd144","status":"success"}
 * X_GOPAY_SIGNATURE: Ywaf17R8Cj3mPRbOem6isFYBTtJOfZfV1dTz9JvbQVU=
 */
public class Hasher {
	private String accessToken = "";

	public Hasher setAccessToken(String accessToken) {
		this.accessToken = accessToken;

		return this;
	}

	public String make(LinkedHashMap<String, String> value)
			throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		value.remove("_signature");
		
		return make(JSON.encode(value));
	}

	public String make(String value)
			throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
		Base64.Encoder encoder = Base64.getEncoder();
		Mac sha256 = Mac.getInstance("HmacSHA256");
		sha256.init(new SecretKeySpec(accessToken.getBytes("UTF8"), "HmacSHA256"));

		return encoder.encodeToString(sha256.doFinal(value.getBytes("UTF8")));
	}

	public boolean check(String value, String hashedValue) {
		try {
			return hashedValue.equals(make(value));
		} catch (InvalidKeyException | NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean check(LinkedHashMap<String, String> value, String hashedValue) {
		try {
			return hashedValue.equals(make(value));
		} catch (InvalidKeyException | NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
