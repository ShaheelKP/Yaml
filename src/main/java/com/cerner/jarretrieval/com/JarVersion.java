package com.cerner.jarretrieval.com;
import java.io.IOException;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class JarVersion {
	
	/**
	 * Reads Url as an argument and parse corresponding table in given Url
	 * 
	 * @param url:Url to connect to document and parse the table
	 * @return:Return latest jar version based on date rom parsed data
	 * @throws IOException
	 * 
	 */
	
	public String geturtl(String url) throws IOException {
		Logger LOGGER = LoggerFactory.getLogger(JarVersion.class);
		TreeMap<Date, String> hMap = new TreeMap<Date, String>();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements tabledata = doc.select("table tr");
			/**
			 * Removing Unnecessary Rows
			 */

			for (int i = 0; i <= 1; i++) {
				tabledata.remove(0);
			}
			for (int i = 0; i <= 2; i++) {
				tabledata.remove(tabledata.size() - 1);
			}
			for (Element row : tabledata) {
				Elements tableoftd = row.getElementsByTag("td");
				Elements tableofa = row.getElementsByTag("a");
				Element td = tableoftd.first();
				tableoftd.remove(0);
				/**
				 * Converting string to Date Format
				 */

				SimpleDateFormat DateFor = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
				try {
					Date date = DateFor.parse(tableoftd.text());
					hMap.put(date, tableofa.text());
				} catch (ParseException e) {
					LOGGER.error(String.format("Unable to parse String"));
					e.printStackTrace();
					;
				}
			}

		} catch (IllegalArgumentException i) {
			LOGGER.error(String.format("Invalid Url:  " + url));
			throw i;
		} catch (HttpStatusException i) {
			LOGGER.error(String.format("Cannot Fetch Given Url:  " + url));
			throw i;
		} catch (UnknownHostException i) {
			LOGGER.error(String.format("No Network connection "));
			throw i;

		}
		/**
		 * Returning Latest Jar Version
		 */
		String Jarversion = ((TreeMap<Date, String>) hMap).lastEntry().getValue();
		return Jarversion;
	}


}
