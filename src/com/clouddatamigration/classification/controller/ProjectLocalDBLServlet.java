package com.clouddatamigration.classification.controller;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.classification.model.LocalDBLCriterionPossibleValue;
import com.clouddatamigration.classification.model.LocalDBLProperty;
import com.clouddatamigration.classification.model.Project;

public class ProjectLocalDBLServlet extends HttpServlet {

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

		// delete old settings
		LocalDBLProperty localDBLPropertyService = new LocalDBLProperty();
		Collection<LocalDBLProperty> localDBLProperties = localDBLPropertyService
				.findAllByProjectId(project.getId());
		for (LocalDBLProperty localDBLProperty : localDBLProperties) {
			localDBLPropertyService.delete(localDBLProperty);
		}

		// save new settings
		LocalDBLCriterionPossibleValue localDBLCriterionPossibleValueService = new LocalDBLCriterionPossibleValue();
		// iterate over all potentials "answers"
		for (LocalDBLCriterionPossibleValue localDBLCriterionPossibleValue : localDBLCriterionPossibleValueService
				.findAll()) {
			// iterate over all actual "answers" for the given "question"
			if (req.getParameterValues(localDBLCriterionPossibleValue
					.getLocalDBLCriterion().getId()) != null) {
				// find the potential "answer" to the actual "answer"
				for (String checkedPossibleValueId : req
						.getParameterValues(localDBLCriterionPossibleValue
								.getLocalDBLCriterion().getId())) {
					if (localDBLCriterionPossibleValue.getId().equals(
							checkedPossibleValueId)) {
						LocalDBLProperty localDBLProperty = new LocalDBLProperty();
						localDBLProperty
								.setLocalDBLCriterionPossibleValue(localDBLCriterionPossibleValue);
						localDBLProperty.setProject(project);
						localDBLProperty = localDBLPropertyService
								.save(localDBLProperty);
					}
				}
			}
		}

		resp.setHeader("Location",
				"/classification/project.jsp?id=" + project.getId()
						+ "&info=added");
	}
}
