package com.cerner.jarretrieval.com;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.HttpStatusException;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UrlPathServlet
 */
@WebServlet("/urlpathservlet")
public class UrlPathServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getParameter("url");
		String path = request.getParameter("path");
		String Resp = "";
		System.out.println("url: " + url);
		System.out.println("path: " + path);
		try {
			File f = new File(path);
			File files[] = f.listFiles();
			if (!f.exists() && (!f.isDirectory())) {
				throw new NullPointerException();

			} else if (files.length == 0) {
				throw new EmptyDirectoryException("Empty Folder");
			}

			LatestJar ob = new LatestJar();
			Map<String, String> Jarmap = new TreeMap<String, String>();
			Jarmap = (Map<String, String>) ob.Mapyaml(url);
			YamlUpdate ob2 = new YamlUpdate();
			Resp = ob2.yamledit(path, Jarmap);

		} catch (HttpStatusException e) {
			response.setContentType("text/html");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/connection_error.html");
			view.include(request, response);
		} catch (NullPointerException e) {
			response.setContentType("text/html");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/incorrect_path.html");
			view.include(request, response);
		} catch (IllegalArgumentException e) {
			response.setContentType("text/html");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/invalid_url.html");
			view.include(request, response);
		} catch (UnknownHostException e) {
			response.setContentType("text/html");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/no_network.html");
			view.include(request, response);
		} catch (EmptyDirectoryException e) {
			response.setContentType("text/html");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/empty_directory.html");
			view.include(request, response);
		} catch (StringIndexOutOfBoundsException e) {
			response.setContentType("text/html");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/invalid_url.html");
			view.include(request, response);

		} catch (ConnectException e) {
			response.setContentType("text/html");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/timeout.html");
			view.include(request, response);

		}

		if (Resp.equals("successfully updated")) {
			response.setContentType("text/html");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/success.html");
			view.include(request, response);
		}

		else if (Resp.equals("empty folder")) {
			response.setContentType("text/html");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/empty_directory.html");
			view.include(request, response);

		}

	}

}
