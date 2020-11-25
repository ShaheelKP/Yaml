package com.cerner.jarretrieval.com;

import java.io.BufferedReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
//import java.util.logging.Logger;

public class YamlUpdate {

	/**
	 * Reads path of directory and map with latest jar values as an argument and
	 * update it to existing yaml file on given path
	 * 
	 * @param path:path      to exsting yaml files to be updated with new values
	 * @param map:Consisting of latest client-jar entry to be updated to.
	 * @return:Return final response or status
	 * @throws EmptyDirectoryEcxeption
	 */

	public String yamledit(String path, Map jmap) throws IOException, EmptyDirectoryException {
		// Logger logger = Logger.getLogger(LatestJar.class.getName());
		Logger LOGGER = LoggerFactory.getLogger(YamlUpdate.class);

		Map<String, String> Jarmap = new TreeMap<String, String>();
		Jarmap = (Map<String, String>) jmap;
		HashSet<String> Flag = new HashSet();
		File directoryPath = new File(path);

		File filesList[] = directoryPath.listFiles(); // Accessing each file in given directory path
		try {
			for (File file : filesList) {

				// try {
				FileReader fr = new FileReader(file);
				String s;
				String totalStr = "";
				try (BufferedReader br = new BufferedReader(fr)) {

					while ((s = br.readLine()) != null) {

						if (s.contains("$") && (!s.contains("remain unchanged")) && (!s.contains("remain same"))) {
							String[] arrOfStr = s.split("/", 5); // Splitting Yaml line using string split method
							String name = arrOfStr[3];

							for (Map.Entry<String, String> entry2 : Jarmap.entrySet()) {
								String Jarmapkey = entry2.getKey();

								if (name.equals(Jarmapkey)) {
									String two = entry2.getValue(); // Replacing Yaml file line Using pattern
																	// Matching
									String replacedstring = s.replaceAll(
											"\\+?\\d?\\d?\\d?\\.*\\d?\\d?\\d.?\\d?\\d?\\d.?[i-k][a-b][]q-t]", two);
									totalStr += replacedstring + System.lineSeparator();

								}

								if (!name.equals(Jarmapkey)) {
									Flag.add("True");
								}

								else {
									Flag.add("False");
								}
							}
						}
						if (Flag.size() <= 1) {
							totalStr += s + System.lineSeparator();
						}
						Flag.clear();
					}
					FileWriter fw = new FileWriter(file); // Updating Yaml file
					fw.write(totalStr);
					fw.close();
				}

			}

		} catch (NullPointerException e) {
			LOGGER.error(String.format("Input directory path doesnt exist"));
			throw e;
		}

		if (filesList.length == 0) {
			LOGGER.error(String.format("Input Directoryis Empty  :" + path));
			throw new EmptyDirectoryException("Empty Folder");
		} else {
			return "successfully updated";
		}

	}

}
