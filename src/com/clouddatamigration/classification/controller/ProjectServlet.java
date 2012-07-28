package com.clouddatamigration.classification.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.classification.model.Project;
import com.clouddatamigration.classification.model.User;

public class ProjectServlet extends HttpServlet {

	private static final long serialVersionUID = 2909682309530795891L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setStatus(HttpServletResponse.SC_SEE_OTHER);

		// get user via session id from cookie value
		User user = new User();
		for (Cookie cookie : req.getCookies()) {
			if (cookie.getName().equals("sessionToken")) {
				user = user.findBySessionToken(cookie.getValue());
			}
		}

		if (user == null || user.getId() == null) {
			resp.setHeader("Location", "/index.jsp?error=signinFailed");
		} else {
			Project project = new Project();
			if (req.getParameter("id") != null
					&& !req.getParameter("id").isEmpty()) {
				project = project.findByID(req.getParameter("id"));
			}
			project.setName(req.getParameter("name"));
			project.setDescription(req.getParameter("description"));
			project.setUrl(req.getParameter("url"));
			project.setDepartment(req.getParameter("department"));
			project.setUser(user);
			project = project.save(project);

			resp.setHeader("Location", "/classification/project.jsp?id="
					+ project.getId() + "&info=added");
		}
	}
}
