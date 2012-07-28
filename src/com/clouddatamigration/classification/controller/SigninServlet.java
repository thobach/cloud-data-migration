package com.clouddatamigration.classification.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.classification.model.User;

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
			Date expdate = new Date();
			expdate.setTime(expdate.getTime() + (24 * 3600 * 1000));
			DateFormat df = new SimpleDateFormat("dd MMM yyyy kk:mm:ss z");
			df.setTimeZone(TimeZone.getTimeZone("GMT"));
			resp.setHeader("Set-Cookie",
					"sessionToken=" + user.getSessionToken() + "; Expires="
							+ df.format(expdate) + "; Path=/; HTTPOnly");

			resp.setHeader("Location", "/classification/projects.jsp");
		} else {
			resp.setHeader("Location", "/index.jsp?error=signinFailed");
		}
	}
}
