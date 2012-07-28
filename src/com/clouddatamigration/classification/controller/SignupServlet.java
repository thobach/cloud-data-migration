package com.clouddatamigration.classification.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.classification.model.User;

public class SignupServlet extends HttpServlet {

	private static final long serialVersionUID = 2909682309530795891L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setStatus(HttpServletResponse.SC_SEE_OTHER);

		// create new user
		User user = new User();
		user.setEmail(req.getParameter("email"));
		// user.setUsername(req.getParameter("username"));
		user.setUsername(req.getParameter("email"));
		user.setVerified(false);

		// persist new user
		user = user.save(user);

		// set password after ID was generated (since password hash uses user
		// ID)
		// user.setPassword(req.getParameter("password"));
		// user = user.save(user);

		// login new user and redirect to project list
		// if (user.login(req.getParameter("password"))) {
		// user = user.findByID(user.getId());
		// resp.setHeader(
		// "Location",
		// "/classification/projects.jsp?sessionToken="
		// + user.getSessionToken());
		// } else {
		// resp.setHeader("Location", "/index.jsp?error=signupFailed");
		// }

		resp.setHeader("Location", "/index.jsp?info=signedUp");
	}
}
