package com.exist;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

public class FRSTokenizer {
	
	public ArrayList<String> createArrayListOfLines(String text) {
		
		File file;
		file = new File(text);
		Charset charset = Charset.forName("UTF-8");
		List<String> listOfLines;
		listOfLines = new ArrayList<String>();
		
		try {
			System.out.println("File: " + text);
			System.out.print("File exists");
			listOfLines = FileUtils.readLines(file, charset);
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + text);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error reading the file: " + text);
			e.printStackTrace();
		} 
		ArrayList<String> arrayListOfLines = new ArrayList<>(listOfLines);
		return arrayListOfLines;
	}
	
	public ArrayList<ArrayList<Object>> getKeyValueAndDimFromList(ArrayList<String> arrayListOfLines) {
		int tokenCounterPerRow;
		ArrayList<Object> extractedKeyValueArrayList;
		extractedKeyValueArrayList = new ArrayList<Object>();
		ArrayList<Object> dim;
		dim = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> keyValueDim;
		keyValueDim = new ArrayList<ArrayList<Object>>();
		for (int i = 0; i < arrayListOfLines.size(); i++) {
			tokenCounterPerRow = 0;
			StringTokenizer row = new StringTokenizer(arrayListOfLines.get(i), " ");
			while (row.hasMoreTokens()) {
				extractedKeyValueArrayList.add(row.nextToken());
				tokenCounterPerRow++;
			}
			dim.add(tokenCounterPerRow);
		}
		keyValueDim.add(extractedKeyValueArrayList);
		keyValueDim.add(dim);
		return keyValueDim;
	}
	
	public ArrayList<String> getKeyArrayList(ArrayList<String> extractedKeyValueArrayList, String text) {
		ArrayList<String> keyArrayList;
		keyArrayList = new ArrayList<String>();
		InvalidFileContents ifc;
		ifc = new InvalidFileContents();
		
		try {
			for (int i = 0; i < extractedKeyValueArrayList.size(); i++) {
				StringTokenizer cell = new StringTokenizer(extractedKeyValueArrayList.get(i), ":");
				while (cell.hasMoreTokens()) {
					keyArrayList.add(cell.nextToken());
					cell.nextToken();
				}
			}	
		} catch (NoSuchElementException e) {
			ifc.generateNewTable(text);
			
		} 
		return keyArrayList;
	}
	
	public ArrayList<String> getValueArrayList(ArrayList<String> extractedKeyValueArrayList) {
		ArrayList<String> valueArrayList;
		valueArrayList = new ArrayList<String>();
		for (int i = 0; i < extractedKeyValueArrayList.size(); i++) {
			StringTokenizer cell = new StringTokenizer(extractedKeyValueArrayList.get(i), ":");
			while (cell.hasMoreTokens()) {
				cell.nextToken();
				valueArrayList.add(cell.nextToken());
			}
		}
		return valueArrayList;
	}
	
	public LinkedHashMap<String, ArrayList<String>> 
			createMapFromKeyAndValueArrayLists(ArrayList<String> keyArrayList, ArrayList<String> valueArrayList) {
		LinkedHashMap<String, ArrayList<String>> map;
		map = new LinkedHashMap<String, ArrayList<String>>();
		for (int i = 0; i < keyArrayList.size(); i++) {
			String key = keyArrayList.get(i);
			String value = valueArrayList.get(i);
			map.put(key, new ArrayList<String>());
			map.get(key).add(key+value);
			map.get(key).add(value);
		}
		return map;
	}
}