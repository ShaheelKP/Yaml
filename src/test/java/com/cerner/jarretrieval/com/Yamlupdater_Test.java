package com.cerner.jarretrieval.com;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;

//Test class for Yaml Update Class
public class Yamlupdater_Test {
	// Test method to update yaml files using directory path arguement
	@Test
	public void Success_Directory() throws IOException, EmptyDirectoryException {
		YamlUpdate ob = new YamlUpdate();
		Map<String, String> Jarmap = new TreeMap<String, String>();
		Jarmap.put("accco", "1.1");
		String actual = "successfully updated";
		String expected = ob.yamledit("C:\\Demo", Jarmap);
		assertEquals(expected, actual);
	}

	// Test method for Empty directory
	@Test(expected = EmptyDirectoryException.class)
	public void empty_Directory() throws IOException, EmptyDirectoryException {
		YamlUpdate ob = new YamlUpdate();
		Map<String, String> Jarmap = new TreeMap<String, String>();
		Jarmap.put("accco", "1.1");
		ob.yamledit("C:\\Demo2", Jarmap);

	}

	// Test method for Invalid Directory path
	@Test(expected = NullPointerException.class)
	public void invalid_urls() throws IOException, EmptyDirectoryException {
		YamlUpdate ob = new YamlUpdate();
		Map<String, String> Jarmap = new TreeMap<String, String>();
		Jarmap.put("accco", "1.1");
		ob.yamledit("C:\\De", Jarmap);

	}

}
