package com.clouddatamigration.classification.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.classification.model.CDHSCriterionPossibleValue;
import com.clouddatamigration.classification.model.CloudDataHostingSolution;
import com.clouddatamigration.classification.model.Project;

public class ProjectCDHSServlet extends HttpServlet {

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
		CloudDataHostingSolution cloudDataHostingSolutionService = new CloudDataHostingSolution();
		Map<String, ArrayList<CloudDataHostingSolution>> cloudDataHostingSolutions = cloudDataHostingSolutionService
				.findAllByProject(project.getId());
		for (ArrayList<CloudDataHostingSolution> oldcloudDataHostingSolutions : cloudDataHostingSolutions
				.values()) {
			for (CloudDataHostingSolution oldcloudDataHostingSolution : oldcloudDataHostingSolutions) {
				oldcloudDataHostingSolution.delete(oldcloudDataHostingSolution);
			}
		}

		// save new settings
		CDHSCriterionPossibleValue cdhsCriterionPossibleValue = new CDHSCriterionPossibleValue();
		Map<String, ArrayList<CDHSCriterionPossibleValue>> cdhs = cdhsCriterionPossibleValue
				.findAllGrouped();

		// iterate over all potentials "answers", grouped by the "question"
		for (ArrayList<CDHSCriterionPossibleValue> cdhsList : cdhs.values()) {
			// iterate over all actual "answers" for the given "question"
			if (req.getParameterValues(cdhsList.get(0).getCdhsCriterion()
					.getId()) != null) {
				for (String checkedPossibleValueId : req
						.getParameterValues(cdhsList.get(0).getCdhsCriterion()
								.getId())) {
					// find the potential "answer" to the actual "answer"
					for (CDHSCriterionPossibleValue criterionPossibleValue : cdhsList) {
						if (criterionPossibleValue.getId().equals(
								checkedPossibleValueId)) {
							CloudDataHostingSolution cloudDataHostingSolution = new CloudDataHostingSolution();
							cloudDataHostingSolution
									.setCdhsCriterionPossibleValue(criterionPossibleValue);
							cloudDataHostingSolution.setProject(project);
							if (req.getParameter(checkedPossibleValueId+"-value") != null
									&& !req.getParameter(checkedPossibleValueId+"-value")
											.isEmpty()) {
								cloudDataHostingSolution.setValue(req
										.getParameter(checkedPossibleValueId+"-value"));
							}
							cloudDataHostingSolution = cloudDataHostingSolution
									.save(cloudDataHostingSolution);
						}
					}
				}
			}
		}

		resp.setHeader("Location",
				"/classification/project.jsp?id=" + project.getId()
						+ "&info=added");
	}
}
