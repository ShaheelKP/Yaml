package com.cerner.jarretrieval.com;

import static org.junit.Assert.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;
import com.google.gson.Gson;

import org.jsoup.HttpStatusException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.springframework.util.ResourceUtils;

import org.junit.Test;

//Test class for Jarversion class
public class JarVersion_Test {
	@Mock
	public JarVersion ob = new JarVersion();
	public MockDetailsRetrieval us = new MockDetailsRetrieval();

	// Test method for retrieve latest jarversion using url
	@Test
	public void good_url() throws IOException {
		Gson gson = new Gson();
		JarVersion test = mock(JarVersion.class);
		File file = ResourceUtils.getFile("classpath:MockGoal.json");
		String fileContent = new String(Files.readAllBytes(file.toPath()));

		MockDetailsRetrieval mockArguements = gson.fromJson(fileContent, MockDetailsRetrieval.class);
		String url = (mockArguements.geturl());
		String response = (mockArguements.getresponse());
		when(test.geturtl(url)).thenReturn(response);
		String mockResponse = test.geturtl(url);
		String actualResponse = ob.geturtl(
				"http://repo.release.cerner.corp/nexus/content/repositories/main-repo/com/cerner/synapse/rules/aco-2015-rules-test/");
		assertEquals(mockResponse, actualResponse);
	}

	// Test method for invalid url
	@Test(expected = IllegalArgumentException.class)
	public void invalid_urls() throws IOException {
		JarVersion ob = new JarVersion();
		ob.geturtl("abc");

	}

}
