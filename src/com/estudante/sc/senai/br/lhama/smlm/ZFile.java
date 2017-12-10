package com.estudante.sc.senai.br.lhama.smlm;

import org.json.simple.JSONValue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Marcelo Vogt on 06/08/2017.
 */
public class ZFile {

	public static String readFile(String path) throws InvalidPathException {

		InputStream is = ZFile.class.getClassLoader().getResourceAsStream(path);

		Scanner s = new Scanner(is).useDelimiter("\\A");

		return s.hasNext() ? s.next() : "";

	}

	public static void writeFile(String path, String content) throws FileNotFoundException, UnsupportedEncodingException {
		/*PrintWriter writer = new PrintWriter(path, "UTF-8");
		writer.print(content);
		writer.close();*/
		try {
			Files.write(Paths.get(path), content.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> readWrittenFile(String path) {
		try {
			return Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object readJSON(String path) {
		try {

			String json = ZFile.readFile(path);

			return JSONValue.parse(json);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		return null;
	}

}
