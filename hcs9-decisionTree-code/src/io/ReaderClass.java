package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReaderClass {

	private String currentFile;
	private BufferedReader reader;
	private boolean flagClose = false;

	public ReaderClass(String fileName) throws FileNotFoundException {

		currentFile = fileName;
		reader = new BufferedReader(new FileReader(fileName));
	}

	public void open(String fileName) throws IOException {
		if (flagClose) {
			flagClose = false;
			currentFile = fileName;
			reader = new BufferedReader(new FileReader(fileName));
		} else {
			throw new IOException("Already exists an open file '" + currentFile
					+ "'.");
		}
	}

	public String readLine() throws IOException {
		return reader.readLine();
	}

	public void close() throws IOException {
		if (!flagClose) {
			flagClose = true;
			reader.close();
		} else {
			throw new IOException("There is no file open.");
		}
	}

	public static void main(String[] args) {

		try {
			ReaderClass reader = new ReaderClass("base_dados_jogarTenis.csv");
			String line = reader.readLine();
			while (line != null) {
				
				System.out.println(line);
				line = reader.readLine();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void print(String s) {
		System.out.println(s);
	}
}
