package com.emaraic.ObjectRecognition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextArea;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Calculations {
	

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

	public static void listMatches(String word, List<String> products,JTextArea results) {
		word = word.toLowerCase();
		for (String product : products) {
			if (product.indexOf(word) == -1) {
				continue;
			}
			results.setText(product + "\n" + results.getText());
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
