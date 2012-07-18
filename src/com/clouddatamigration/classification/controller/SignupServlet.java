package com.clouddatamigration.classification.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.classification.model.CDHSCategory;
import com.clouddatamigration.classification.model.CDHSCriterion;
import com.clouddatamigration.classification.model.CDHSCriterionPossibleValue;
import com.clouddatamigration.classification.model.CDHSCriterionPossibleValue.Type;
import com.clouddatamigration.classification.model.CloudDataHostingSolution;
import com.clouddatamigration.classification.model.CloudDataStore;
import com.clouddatamigration.classification.model.Project;
import com.clouddatamigration.classification.model.User;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

	private static final long serialVersionUID = 2909682309530795891L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setStatus(HttpServletResponse.SC_SEE_OTHER);

		// create new user
		User user = new User();
		user.setEmail(req.getParameter("email"));
		user.setCreated(new Date());
		user.setUsername(req.getParameter("username"));
		user.setVerified(false);

		// persist new user
		user = user.save(user);

		// set password after ID was generated (since password hash uses user
		// ID)
		user.setPassword(req.getParameter("password"));
		user = user.save(user);

		// login new user and redirect to project list
		if (user.login(req.getParameter("password"))) {
			user = user.findByID(user.getId());

			CloudDataStore cloudDataStore = new CloudDataStore();
			cloudDataStore.setName("CDS");
			cloudDataStore = cloudDataStore.save(cloudDataStore);

			CDHSCategory cdhsCategory = new CDHSCategory();
			cdhsCategory.setName("NAME");
			cdhsCategory = cdhsCategory.save(cdhsCategory);

			CDHSCriterion cdhsCriterion = new CDHSCriterion();
			cdhsCriterion.setKey("KEY");
			cdhsCriterion.setName("NAME");
			cdhsCriterion.setCdhsCategory(cdhsCategory);
			cdhsCriterion = cdhsCriterion.save(cdhsCriterion);

			CDHSCriterionPossibleValue cdhsCriterionPossibleValue = new CDHSCriterionPossibleValue();
			cdhsCriterionPossibleValue.setKey("KEY");
			cdhsCriterionPossibleValue.setName("NAME");
			cdhsCriterionPossibleValue.setType(Type.SELECT);
			cdhsCriterionPossibleValue.setCdhsCriterion(cdhsCriterion);
			cdhsCriterionPossibleValue = cdhsCriterionPossibleValue
					.save(cdhsCriterionPossibleValue);

			Project project = new Project();
			project.setUser(user);
			project.setName("ProjectName");
			project.setCloudDataStore(cloudDataStore);
			// don't save project here, otherwise it is persisted twice

			CloudDataHostingSolution cloudDataHostingSolution = new CloudDataHostingSolution();
			cloudDataHostingSolution.setProject(project);
			cloudDataHostingSolution
					.setCdhsCriterionPossibleValue(cdhsCriterionPossibleValue);
			cloudDataHostingSolution = cloudDataHostingSolution
					.save(cloudDataHostingSolution);

			resp.setHeader("Location",
					"projects.jsp?sessionToken=" + user.getSessionToken());
		} else {
			resp.setHeader("Location", "index.jsp?error=signupFailed");
		}
	}
}
