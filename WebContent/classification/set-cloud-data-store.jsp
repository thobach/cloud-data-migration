<%@page import="java.util.HashMap"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page
	import="com.clouddatamigration.store.model.CloudDataStoreProperty"%>
<%@page
	import="com.clouddatamigration.classification.model.CloudDataHostingSolution"%>
<%@page import="com.clouddatamigration.store.model.CloudDataStore"%>
<%@page import="com.clouddatamigration.classification.model.Project"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%
	request.setAttribute("pageName", "projects.jsp");
%>
<%@ include file="../common/header.jsp"%>
<%
	Project project = new Project();
	if (request.getParameter("id") != null) {
		project = project.findByID(request.getParameter("id"));
	}
	if (project == null || project.getId() == null
			|| project.getId().isEmpty()) {
		response.setStatus(HttpServletResponse.SC_SEE_OTHER);
		response.setHeader("Location", "/classification/projects.jsp");
		return;
	}
%>
<h1>Step 3: Select Cloud Data Store</h1>
<p class="lead">Based on your requirements for a Cloud data hosting
	solution you can select a matching Cloud data store.</p>
<form class="form-horizontal well" method="post"
	action="/classification/projectCloudDataStore">
	<input type="hidden" name="id" value="<%=project.getId()%>" />

	<fieldset>
		<legend>Cloud Data Store</legend>
		<%
			CloudDataStore cloudDataStoreService = new CloudDataStore();
			for (CloudDataStore cloudDataStore : cloudDataStoreService
					.findAll()) {
		%>
		<div class="control-group">
			<label class="control-label"><%=cloudDataStore.getName()%> (<%=cloudDataStore.getProvider()%>)</label>
			<div class="controls">
				<label class="radio inline"> <input type="radio" name="cds"
					value="<%=cloudDataStore.getId()%>"
					<%if (project.getCloudDataStore() != null
						&& project.getCloudDataStore().getId()
								.equals(cloudDataStore.getId())) {%>
					checked="checked" <%}%>> <%=cloudDataStore.getDescription()%>
				</label>
				<div style="margin-left: 18px; margin-top: 10px;">
					<%
						ArrayList<String> matches = new ArrayList<String>();
							ArrayList<String> conflicts = new ArrayList<String>();
							ArrayList<String> additionals = new ArrayList<String>();

							CloudDataHostingSolution cloudDataHostingSolutionService = new CloudDataHostingSolution();
							CloudDataStoreProperty cloudDataStorePropertyService = new CloudDataStoreProperty();

							HashMap<String, ArrayList<CloudDataStoreProperty>> actualCDSValues = cloudDataStorePropertyService
									.findAllByCDS(cloudDataStore.getId());

							for (CloudDataHostingSolution cloudDataHostingSolution : cloudDataHostingSolutionService
									.findAllByProjectId(project.getId())) {
								boolean foundMatch = false;
								for (CloudDataStoreProperty cloudDataStoreProperty : cloudDataStorePropertyService
										.findAllByCDSId(cloudDataStore.getId())) {
									// matches
									if (cloudDataHostingSolution
											.getCdhsCriterionPossibleValue()
											.getId()
											.equals(cloudDataStoreProperty
													.getCdhsCriterionPossibleValue()
													.getId())) {
										matches.add(cloudDataHostingSolution
												.getCdhsCriterionPossibleValue()
												.getCdhsCriterion().getCdhsCategory()
												.getName()
												+ " - "
												+ cloudDataHostingSolution
														.getCdhsCriterionPossibleValue()
														.getCdhsCriterion().getName()
												+ ": "
												+ cloudDataHostingSolution
														.getCdhsCriterionPossibleValue()
														.getName()
												+ (cloudDataHostingSolution.getValue() != null
														&& !cloudDataHostingSolution
																.getValue().isEmpty() ? " (you selected: "
														+ cloudDataHostingSolution
																.getValue()
														+ ", "
														+ cloudDataStore.getName()
														+ " has: "
														+ cloudDataStoreProperty
																.getInputValue() + ")"
														: ""));
										foundMatch = true;
									}
								}

								// conflicts
								if (!foundMatch) {
									String actualCDSValuesString = "";
									if (actualCDSValues.get(cloudDataHostingSolution
											.getCdhsCriterionPossibleValue()
											.getCdhsCriterion().getKey()) != null) {
										for (CloudDataStoreProperty actualCloudDataStoreProperty : actualCDSValues
												.get(cloudDataHostingSolution
														.getCdhsCriterionPossibleValue()
														.getCdhsCriterion().getKey())) {
											actualCDSValuesString += actualCloudDataStoreProperty
													.getCdhsCriterionPossibleValue()
													.getName()
													+ (actualCloudDataStoreProperty
															.getInputValue() != null
															&& !actualCloudDataStoreProperty
																	.getInputValue()
																	.isEmpty() ? " ("
															+ actualCloudDataStoreProperty
																	.getInputValue() + ")"
															: "") + ", ";
										}
										conflicts
												.add(cloudDataHostingSolution
														.getCdhsCriterionPossibleValue()
														.getCdhsCriterion()
														.getCdhsCategory().getName()
														+ " - "
														+ cloudDataHostingSolution
																.getCdhsCriterionPossibleValue()
																.getCdhsCriterion()
																.getName()
														+ ": <em>"
														+ cloudDataHostingSolution
																.getCdhsCriterionPossibleValue()
																.getName()
														+ (cloudDataHostingSolution
																.getValue() != null
																&& !cloudDataHostingSolution
																		.getValue()
																		.isEmpty() ? " ("
																+ cloudDataHostingSolution
																		.getValue() + ")"
																: "")
														+ "</em> - "
														+ cloudDataStore.getName()
														+ " has: <em>"
														+ actualCDSValuesString + "</em>");
									}
								}
							}

							// additionals
							for (CloudDataStoreProperty cloudDataStoreProperty : cloudDataStorePropertyService
									.findAllByCDSId(cloudDataStore.getId())) {
								boolean foundAdditional = true;
								for (CloudDataHostingSolution cloudDataHostingSolutionAdditional : cloudDataHostingSolutionService
										.findAllByProjectId(project.getId())) {
									if (cloudDataHostingSolutionAdditional
											.getCdhsCriterionPossibleValue()
											.getCdhsCriterion()
											.getId()
											.equals(cloudDataStoreProperty
													.getCdhsCriterionPossibleValue()
													.getCdhsCriterion().getId())) {
										foundAdditional = false;
									}
								}
								if (foundAdditional) {
									additionals
											.add(cloudDataStoreProperty
													.getCdhsCriterionPossibleValue()
													.getCdhsCriterion().getCdhsCategory()
													.getName()
													+ " - "
													+ cloudDataStoreProperty
															.getCdhsCriterionPossibleValue()
															.getCdhsCriterion().getName()
													+ ": "
													+ cloudDataStoreProperty
															.getCdhsCriterionPossibleValue()
															.getName()
													+ (cloudDataStoreProperty
															.getInputValue() != null
															&& !cloudDataStoreProperty
																	.getInputValue()
																	.isEmpty() ? " ("
															+ cloudDataStoreProperty
																	.getInputValue() + ")"
															: ""));
								}
							}
					%>
					<strong>This Cloud data store matches the following of
						your requirements:</strong>
					<ul>
						<%
							if (matches.size() > 0) {
									for (String match : matches) {
						%><li><%=match%></li>
						<%
							}
								} else {
						%><li>none</li>
						<%
							}
						%>
					</ul>
					<strong>The following of your requirements were not
						matched:</strong>
					<ul>
						<%
							if (conflicts.size() > 0) {
									for (String conflict : conflicts) {
						%><li><%=conflict%></li>
						<%
							}
								} else {
						%><li>none</li>
						<%
							}
						%>
					</ul>
					<strong><%=cloudDataStore.getName()%> provides
						additionally:</strong> <a href="#"
						onclick="if(this.innerHTML=='show'){$('#showAdditional').removeClass('hide'); this.innerHTML='hide';} else {$('#showAdditional').addClass('hide'); this.innerHTML='show';}">show</a>
					<div class="hide" id="showAdditional">
						<ul>
							<%
								if (additionals.size() > 0) {
										for (String additional : additionals) {
							%><li><%=additional%></li>
							<%
								}
									} else {
							%><li>none</li>
							<%
								}
							%>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<%
			}
		%>

	</fieldset>

	<div class="form-actions">
		<a href="/classification/projects.jsp" class="btn">Cancel</a>
		<button type="submit" class="btn btn-primary"
			onclick="$('#loadingModal').modal({backdrop:'static', keyboard: false}); $('#loadingBar').delay(100).animate({'width':'25%'},1000).animate({'width':'50%'},1000).animate({'width':'75%'},1000).animate({'width':'100%'},1000); return true;">Save
			&raquo;</button>
	</div>
</form>
<%@ include file="../common/footer.jsp"%>
