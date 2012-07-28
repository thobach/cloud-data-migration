package com.clouddatamigration.classification.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.classification.model.User;

public class SignoutServlet extends HttpServlet {

	private static final long serialVersionUID = -8633427528261901935L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setStatus(HttpServletResponse.SC_SEE_OTHER);
		resp.setHeader("Location", "/index.jsp");

		String sessionToken = null;
		for (Cookie cookie : req.getCookies()) {
			if (cookie.getName().equals("sessionToken")) {
				sessionToken = cookie.getValue();
				cookie.setMaxAge(0);
			}
		}

		if (sessionToken != null) {
			User userService = new User();
			User user = userService.findBySessionToken(sessionToken);
			user.setSessionExpiryDate(null);
			user.setSessionToken(null);
			user = user.save(user);
		}
	}
}
