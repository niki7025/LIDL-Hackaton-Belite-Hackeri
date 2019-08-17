package com.emaraic.ObjectRecognition;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JTextArea;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Calculations {

	public static void buildIndex(List<String> products, Map<String, List<Integer>> index) {
		for (int p = 0; p < products.size(); p++) {
			String product = products.get(p);
			StringTokenizer tokenizer = new StringTokenizer(product, " ,&()-:%\"\'.;/");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				List<Integer> places;
				if (index.containsKey(token) == true) {
					places = index.get(token);
				} else {
					places = new ArrayList<Integer>();
					index.put(token, places);
				}
				places.add(p);
			}
		}
	}

	public static void loadDataSet(List<String> products) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONArray array = (JSONArray) parser
				.parse(new FileReader("C:\\Users\\NikolayDimitrov\\Desktop\\small_product_dataset.json"));
		Iterator<JSONObject> iterator = array.iterator();
		while (iterator.hasNext()) {
			JSONObject current = iterator.next();
			products.add((current.get("ProductId") + " " + current.get("Name") + " " + current.get("Description"))
					.toLowerCase());
		}
	}

	public static void listMatches1(String word, List<String> products, JTextArea results) {
		word = word.toLowerCase();
		String[] list = word.split(" ");
		for (String product : products) {
			boolean found = false;
			for (String token : list) {
				if (product.indexOf(token) != -1) {
					found = true;
				}
			}
			if (found == true) {
				results.setText(product + "\n" + results.getText());
			}
		}
	}

	public static void listMatches2(String word, List<String> products, JTextArea results,
			Map<String, List<Integer>> index) {
		word = word.toLowerCase();
		String[] list = word.split(" ");
		Set<Integer> values = new HashSet<>();
		for (String token : list) {
			if (index.containsKey(token) == false) {
				continue;
			}
			values.addAll(index.get(token));
		}
		for (Integer k : values) {
			results.setText(products.get(k) + "\n" + results.getText());
		}
	}

	public static byte[] readAllBytesOrExit(Path path) {
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			System.err.println("Failed to read [" + path + "]: " + e.getMessage());
			System.exit(1);
		}

		return new byte[0];
	}

	public static List<String> readAllLinesOrExit(Path path) {
		try {
			return Files.readAllLines(path, Charset.forName("UTF-8"));
		} catch (IOException e) {
			System.err.println("Failed to read [" + path + "]: " + e.getMessage());
			System.exit(0);
		}

		return new ArrayList<String>();
	}
}
