package com.cerner.jarretrieval.com;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Updatemain {

	public static void main(String[] args) throws IOException, EmptyDirectoryException {
		Logger LOGGER = LoggerFactory.getLogger(Updatemain.class);

		FileReader reader = new FileReader("src/main/resources/Jarretrieval.properties");
		Properties p = new Properties();
		p.load(reader);

		String Url = p.getProperty("url");
		String path = p.getProperty("Directorypath");

		File f = new File(path);
		File files[] = f.listFiles();
		if (!f.exists() && (!f.isDirectory())) {
			LOGGER.error(String.format("Input Directory Path doesnt Exist  :" + path));
			throw new NullPointerException();

		} else if (files.length == 0) {
			throw new EmptyDirectoryException("Error:Empty Directory");
		}

		LatestJar jar = new LatestJar();
		Map<String, String> Jarmapnew = new TreeMap<String, String>();
		Jarmapnew = (Map<String, String>) jar.Mapyaml(Url);
		YamlUpdate Update = new YamlUpdate();
		String response = Update.yamledit(path, Jarmapnew);

		if (response.equals("successfully updated")) {
			LOGGER.info(String.format("Successfully updated yaml files"));

		}

	}

}
