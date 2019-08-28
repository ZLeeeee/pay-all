package com.gopay;

import java.util.LinkedHashMap;

public class JSON {
	public static String encode(LinkedHashMap<String, String> data) {
		StringBuilder json = new StringBuilder();

		json.append("{");
		for (Object key : data.keySet()) {
			json.append(getJSONValue((String) key) + ":");
			json.append(getJSONValue(data.get(key)));
			json.append(",");
		}
		json.deleteCharAt(json.length() - 1);
		json.append("}");

		return json.toString();
	}

	private static String getJSONValue(String s) {
		return "\"" + StringExtension.utf8ToUnicode(addSlashes(s)) + "\"";
	}

	private static String addSlashes(String s) {
		s = s.replaceAll("\\\\", "\\\\\\\\");
		s = s.replaceAll("\\n", "\\\\n");
		s = s.replaceAll("\\r", "\\\\r");
		s = s.replaceAll("\\00", "\\\\0");
		s = s.replaceAll("'", "\\\\'");

		return s;
	}
}
