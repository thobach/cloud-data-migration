package com.clouddatamigration.classification.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clouddatamigration.classification.model.CDHSCriterionPossibleValue;
import com.clouddatamigration.classification.model.CloudDataStore;
import com.clouddatamigration.classification.model.CloudDataStoreProperty;

@WebServlet("/cloudDataStore")
public class CloudDataStoreServlet extends HttpServlet {

	private static final long serialVersionUID = 2909682309530795891L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setStatus(HttpServletResponse.SC_SEE_OTHER);

		CloudDataStore cloudDataStore = new CloudDataStore();
		if (req.getParameter("id") != null && !req.getParameter("id").isEmpty()) {
			cloudDataStore = cloudDataStore.findByID(req.getParameter("id"));
			CloudDataStoreProperty cloudDataStoreProperty = new CloudDataStoreProperty();
			for (ArrayList<CloudDataStoreProperty> oldCloudDataStoreProperties : cloudDataStoreProperty
					.findAllByCDS(cloudDataStore.getId()).values()) {
				for (CloudDataStoreProperty oldCloudDataStoreProperty : oldCloudDataStoreProperties) {
					oldCloudDataStoreProperty.delete(oldCloudDataStoreProperty);
				}
			}
		}
		cloudDataStore.setName(req.getParameter("name"));
		cloudDataStore.setDescription(req.getParameter("description"));
		cloudDataStore.setWebsite(req.getParameter("website"));
		cloudDataStore.setProvider(req.getParameter("provider"));
		cloudDataStore = cloudDataStore.save(cloudDataStore);

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
							CloudDataStoreProperty cloudDataStoreProperty = new CloudDataStoreProperty();
							cloudDataStoreProperty
									.setCdhsCriterionPossibleValue(criterionPossibleValue);
							cloudDataStoreProperty
									.setCloudDataStore(cloudDataStore);
							if (req.getParameter(checkedPossibleValueId) != null
									&& !req.getParameter(checkedPossibleValueId)
											.isEmpty()) {
								cloudDataStoreProperty.setInputValue(req
										.getParameter(checkedPossibleValueId));
							}
							cloudDataStoreProperty = cloudDataStoreProperty
									.save(cloudDataStoreProperty);
						}
					}
				}
			}
		}

		resp.setHeader("Location",
				"cloud-data-store.jsp?id=" + cloudDataStore.getId()
						+ "&info=added");
	}
}
