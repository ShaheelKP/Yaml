package com.cerner.jarretrieval.com;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

//Test class for Urlpathservlet servlet
public class UrlPathServlet_Test {
	private UrlPathServlet servlet;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Before
	public void setUp() {
		servlet = new UrlPathServlet();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

	// Test method yaml updation using retrived jar versions
	@Test
	public void correct_Arguements() throws ServletException, IOException {
		request.addParameter("url",
				"http://repo.release.cerner.corp/nexus/content/repositories/main-repo/com/cerner/synapse/rules/");
		request.addParameter("path", "C:\\Demo");

		servlet.doGet(request, response);

		assertEquals("/WEB-INF/success.html", response.getIncludedUrl());
	}

	// Test method for invalid error
	@Test
	public void Invalid_url() throws ServletException, IOException {
		request.addParameter("url", "abc");
		request.addParameter("path", "C:\\Demo");

		servlet.doGet(request, response);

		assertEquals("/WEB-INF/invalid_url.html", response.getIncludedUrl());

	}

	// Test method for empty directory exception
	@Test
	public void Empty_Directory() throws ServletException, IOException {
		request.addParameter("url",
				"http://repo.release.cerner.corp/nexus/content/repositories/main-repo/com/cerner/synapse/rules/");
		request.addParameter("path", "C:\\Demo2");

		servlet.doGet(request, response);

		assertEquals("/WEB-INF/empty_directory.html", response.getIncludedUrl());

	}

	// Test method for no network connection
	@Test
	public void No_Network() throws ServletException, IOException {
		request.addParameter("url", "http://acbvxz.com/");
		request.addParameter("path", "C:\\Demo");

		servlet.doGet(request, response);

		assertEquals("/WEB-INF/no_network.html", response.getIncludedUrl());

	}

	// Test method for cannot establish connection
	@Test
	public void Connection_Error() throws ServletException, IOException {
		request.addParameter("url",
				"http://repo.release.cerner.corp/nexus/content/repositories/main-repo/com/cerner/synapse/rule");
		request.addParameter("path", "C:\\Demo");

		servlet.doGet(request, response);

		assertEquals("/WEB-INF/connection_error.html", response.getIncludedUrl());

	}

	// Test method for incorrect directory path
	@Test
	public void Incorrect_Path() throws ServletException, IOException {
		request.addParameter("url",
				"http://repo.release.cerner.corp/nexus/content/repositories/main-repo/com/cerner/synapse/rules/");
		request.addParameter("path", "C:\\Dem");

		servlet.doGet(request, response);

		assertEquals("/WEB-INF/incorrect_path.html", response.getIncludedUrl());

	}

}
