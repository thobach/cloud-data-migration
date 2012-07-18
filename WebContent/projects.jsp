<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.clouddatamigration.classification.model.User"%>
<%@ page import="com.clouddatamigration.classification.model.Project"%>
<%@ page
	import="com.clouddatamigration.classification.model.CloudDataHostingSolution"%>
<%@ page
	import="com.clouddatamigration.classification.model.CDMScenario"%>
<%@ page import="java.util.Map"%>
<%@ include file="common/header.jsp"%>
<!-- Example row of columns -->
<div class="row">
	<div class="span12">
		<h2>Your Projects</h2>
		<table>
			<thead>
				<tr>
					<th>Name</th>
					<th>Scenario</th>
					<th>Cloud Data Hosting Solution</th>
					<th>Cloud Data Store</th>
				</tr>
			</thead>
			<tbody>
				<%
					User user = new User();
					user = user.findBySessionToken(request.getParameter("sessionToken"));
					Project projectService = new Project();
					for (Project project : projectService.findAllByUser(user.getId())) {
				%>
				<tr>
					<td><a href="project.jsp?id=<%=project.getId()%>"><%=project.getName()%></a></td>
					<td>
						<%
							String scenarios = "";
							for (CDMScenario scenario : project.getCdmScenarios()) {
								scenarios += (scenarios.isEmpty() ? "" : ", ")
										+ scenario.getName();
							}
							out.print(scenarios);
						%>
					</td>
					<td>
						<%
							CloudDataHostingSolution cloudDataHostingSolutionService = new CloudDataHostingSolution();
							Map<String, CloudDataHostingSolution> cdhs = cloudDataHostingSolutionService.findAllByProject(project.getId());
							String cdhsString = "";
							if (cdhs.get("CLOUD_COMPUTING_SERVICE_MODEL") != null) {
								cdhsString = cdhs.get("CLOUD_COMPUTING_SERVICE_MODEL")
										.getCdhsCriterionPossibleValue().getName();
							}
							if (cdhs.get("CLOUD_COMPUTING_DEPLOYMENT_MODEL") != null) {
								cdhsString += (cdhsString.isEmpty() ? "" : "-")
										+ cdhs.get("CLOUD_COMPUTING_DEPLOYMENT_MODEL")
												.getCdhsCriterionPossibleValue().getName();
							}
							if (cdhs.get("CLOUD_COMPUTING_DEPLOYMENT_MODEL") != null) {
								cdhsString += (cdhsString.isEmpty() ? "" : "-")
										+ cdhs.get("COMPATABILITY_PRODUCT_AND_VERSION")
												.getCdhsCriterionPossibleValue().getName();
							}
							if (cdhs.get("AVAILABILITY_REPLICATION_TYPE") != null) {
								cdhsString += (cdhsString.isEmpty() ? "" : "-")
										+ cdhs.get("AVAILABILITY_REPLICATION_TYPE")
												.getCdhsCriterionPossibleValue().getName();
							}
						%>
						<%=cdhsString %>
					</td>
					<td><%=project.getCloudDataStore() != null ? project
						.getCloudDataStore().getName() : ""%></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<p>
			<a class="btn" href="cloud-data-stores.jsp">View all &raquo;</a>
		</p>
		<p>
			<a class="btn btn-primary" href="add-cloud-data-store.jsp">Add
				New Cloud Data Store &raquo;</a>
		</p>
	</div>
</div>
<%@ include file="common/footer.jsp"%>
