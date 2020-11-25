package com.cerner.jarretrieval.com;

import java.io.IOException;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatestJar {

	/**
	 * Reads Url as an argument and parse corresponding table in given Url
	 * 
	 * @param url:url of webpage to be parsed for data
	 * @return:Return Treemap of Client name and Latest jar version as key-value
	 *                pair based on parsed data.
	 * 
	 */
	public Map<String, String> Mapyaml(String url) throws IOException {
		Logger LOGGER = LoggerFactory.getLogger(LatestJar.class);
		TreeMap<String, String> LinkMap = new TreeMap<String, String>();
		Map<String, String> Jarmap = new TreeMap<String, String>();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements tabledata = doc.select("table tr");

			for (int i = 0; i < 2; i++) { // Removing unnecessary rows
				tabledata.remove(0);
			}
			for (Element tr : tabledata) {

				Elements Tableofa = tr.getElementsByTag("a");
				LinkMap.put(Tableofa.text(), Tableofa.attr("href"));
			}

			for (Map.Entry<String, String> entry : LinkMap.entrySet()) {
				String add = entry.getValue();
				String clientname = entry.getKey();
				clientname = clientname.substring(0, clientname.length() - 1);
				System.out.println("" + clientname);
				JarVersion obb = new JarVersion();
				String Version = obb.geturtl(add); // Passing url to geturl method()
				Version = Version.substring(0, Version.length() - 1);
				String LatjarVer = Version + ".jar";

				Jarmap.put(clientname, LatjarVer);

			}

		} catch (IllegalArgumentException i) {
			LOGGER.error(String.format("Input url is invalid  :" + url));
			throw i;

		} catch (HttpStatusException o) {
			LOGGER.error(String.format("Cannot fetch given Url  :" + url));
			throw o;

		} catch (UnknownHostException i) {
			LOGGER.error(String.format("No Network connection "));
			throw i;

		}

		return Jarmap;
	}

}
