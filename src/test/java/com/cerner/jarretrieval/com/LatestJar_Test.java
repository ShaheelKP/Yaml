package com.cerner.jarretrieval.com;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import java.util.Properties;

import org.jsoup.HttpStatusException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.springframework.util.ResourceUtils;
import static org.junit.Assert.*;

//Test class for Latestjar class
public class LatestJar_Test {

	// Test method for Network connection error
	@Test(expected = HttpStatusException.class)
	public void url_connection_error() throws IOException {
		FileReader reader = new FileReader("src/main/resources/Jarretrieval.properties");
		Properties p = new Properties();
		p.load(reader);
		String url = p.getProperty("url");
		url = url.substring(0, url.length() - 2);
		LatestJar ob = new LatestJar();
		ob.Mapyaml(url);
	}

	// Test method for Invalid Url
	@Test(expected = IllegalArgumentException.class)
	public void url_invalid() throws IOException {
		LatestJar ob1 = new LatestJar();
		ob1.Mapyaml("abc");

	}

}
