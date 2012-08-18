package com.clouddatamigration.migration.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.CloudDataMigrationContextListener;
import com.clouddatamigration.classification.model.User;
import com.clouddatamigration.migration.model.MigrationService;

public class MigrationServlet extends HttpServlet {

	private static final long serialVersionUID = 9190037057869593310L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setCharacterEncoding("UTF-8");

		User user = new User();
		String sessionToken = user.findSessionToken(req.getCookies());

		MigrationService migrationService = (MigrationService) getServletContext()
				.getAttribute(
						CloudDataMigrationContextListener.MIGRATION_SERVICE);

		if (req.getParameter("action") != null
				&& req.getParameter("action").equals("export")) {
			HashMap<String, String> sourceParams = new HashMap<String, String>();
			for (String connectionParam : migrationService
					.getSourceConnectionParams(req
							.getParameter("sourceSystemId"))) {
				sourceParams.put(
						connectionParam,
						req.getParameter(req.getParameter("sourceSystemId")
								+ "_" + connectionParam));
			}
			migrationService.exportData(sessionToken,
					req.getParameter("sourceSystemId"), sourceParams, resp);
		} else if (req.getParameter("action") != null
				&& req.getParameter("action").equals("import")) {
			HashMap<String, String> targetParams = new HashMap<String, String>();
			for (String connectionParam : migrationService
					.getTargetConnectionParams(req
							.getParameter("targetSystemId"))) {
				targetParams.put(
						connectionParam,
						req.getParameter(req.getParameter("targetSystemId")
								+ "_" + connectionParam));
			}
			migrationService.importData(sessionToken,
					req.getParameter("targetSystemId"), targetParams, resp);
		}

	}
}
