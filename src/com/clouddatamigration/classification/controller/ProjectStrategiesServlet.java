package com.clouddatamigration.classification.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.classification.model.CDMScenario;
import com.clouddatamigration.classification.model.Project;

public class ProjectStrategiesServlet extends HttpServlet {

	private static final long serialVersionUID = 2909682309530795891L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setStatus(HttpServletResponse.SC_SEE_OTHER);

		Project project = new Project();
		if (req.getParameter("id") != null && !req.getParameter("id").isEmpty()) {
			project = project.findByID(req.getParameter("id"));
		}
		Set<CDMScenario> scenarios = new HashSet<CDMScenario>();
		CDMScenario cdmScenarioService = new CDMScenario();
		for (String cdmScenario : req.getParameterValues("cdmScenario")) {
			scenarios.add(cdmScenarioService.findByID(cdmScenario));
		}
		project.setCdmScenarios(scenarios);
		project = project.save(project);

		resp.setHeader("Location",
				"/classification/project.jsp?id=" + project.getId()
						+ "&info=added");
	}
}
