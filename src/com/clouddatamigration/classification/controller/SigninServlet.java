package com.clouddatamigration.classification.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.classification.model.User;

@WebServlet("/signin")
public class SigninServlet extends HttpServlet {

	private static final long serialVersionUID = -8633427528261901935L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setStatus(HttpServletResponse.SC_SEE_OTHER);

		User user = new User();
		user = user.findByUsername(req.getParameter("username"));

		// login new user and redirect to project list
		if (user.login(req.getParameter("password"))) {
			resp.setHeader("Location",
					"projects.jsp?sessionToken=" + user.getSessionToken());
		} else {
			resp.setHeader("Location", "index.jsp?error=signinFailed");
		}
	}
}
