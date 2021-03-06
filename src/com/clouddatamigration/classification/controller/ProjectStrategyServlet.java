package com.clouddatamigration.classification.controller;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.classification.model.CDMCriterion;
import com.clouddatamigration.classification.model.CDMCriterionPossibleValue;
import com.clouddatamigration.classification.model.Project;

public class ProjectStrategyServlet extends HttpServlet {

	private static final long serialVersionUID = 2909682309530795891L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setStatus(HttpServletResponse.SC_SEE_OTHER);

		Project project = new Project();
		if (req.getParameter("id") != null && !req.getParameter("id").isEmpty()) {
			project = project.findByID(req.getParameter("id"));
		}
		Set<CDMCriterionPossibleValue> cdmCriterionPossibleValues = new TreeSet<CDMCriterionPossibleValue>();
		CDMCriterionPossibleValue cdmCriterionPossibleValueService = new CDMCriterionPossibleValue();
		CDMCriterion cdmCriterionService = new CDMCriterion();

		for (CDMCriterion cdmCriterion : cdmCriterionService.findAll()) {
			if (req.getParameterValues(cdmCriterion.getId()) != null) {
				for (String cdmCriterionPossibleValue : req
						.getParameterValues(cdmCriterion.getId())) {
					cdmCriterionPossibleValues
							.add(cdmCriterionPossibleValueService
									.findByID(cdmCriterionPossibleValue));
				}
			}
		}
		project.setCdmCriterionPossibleValues(cdmCriterionPossibleValues);
		project = project.save(project);

		resp.setHeader("Location",
				"/classification/project.jsp?id=" + project.getId()
						+ "&info=added");
	}
}
