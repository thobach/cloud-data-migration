package com.clouddatamigration.classification.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.classification.model.Project;
import com.clouddatamigration.store.model.CloudDataStore;

public class ProjectCloudDataStoreServlet extends HttpServlet {

	private static final long serialVersionUID = 2909682309530795891L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setStatus(HttpServletResponse.SC_SEE_OTHER);

		Project project = new Project();
		if (req.getParameter("id") != null && !req.getParameter("id").isEmpty()) {
			project = project.findByID(req.getParameter("id"));
		} else {
			resp.setHeader("Location", "/index.jsp");
			return;
		}

		CloudDataStore cloudDataStore = new CloudDataStore();
		if (req.getParameter("cds") != null
				&& !req.getParameter("cds").isEmpty()) {
			cloudDataStore = cloudDataStore.findByID(req.getParameter("cds"));
		} else {
			resp.setHeader("Location", "/index.jsp");
			return;
		}
		project.setCloudDataStore(cloudDataStore);
		project = project.save(project);

		resp.setHeader("Location",
				"/classification/project.jsp?id=" + project.getId()
						+ "&info=added");
	}
}
