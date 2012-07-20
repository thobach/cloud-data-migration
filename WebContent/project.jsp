<%@page import="com.clouddatamigration.classification.model.Solution"%>
<%@page
	import="com.clouddatamigration.classification.model.LocalDBLCriterionPossibleValue"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@page
	import="com.clouddatamigration.classification.model.LocalDBLProperty"%>
<%@page
	import="com.clouddatamigration.classification.model.CloudDataHostingSolution"%>
<%@page import="com.clouddatamigration.classification.model.CDMStrategy"%>
<%@page import="com.clouddatamigration.classification.model.Project"%>
<%@page import="com.clouddatamigration.classification.model.CDMScenario"%>
<%@page
	import="com.clouddatamigration.classification.model.CloudDataStore"%>
<%@page
	import="com.clouddatamigration.classification.model.CDHSCriterion"%>
<%@page
	import="com.clouddatamigration.classification.model.CDMCriterion"%>
<%@page
	import="com.clouddatamigration.classification.model.CDMCriterionPossibleValue"%>
<%@page
	import="com.clouddatamigration.classification.model.CDHSCriterionPossibleValue"%>
<%@page
	import="com.clouddatamigration.classification.model.CloudDataStoreProperty"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%
	User user = new User();
	String sessionToken = user.findSessionToken(request.getCookies());
	user = user.findBySessionToken(sessionToken);
	if (sessionToken == null || user == null) {
		response.setStatus(HttpServletResponse.SC_SEE_OTHER);
		response.setHeader("Location", "index.jsp?error=sessionTimeout");
		return;
	}
%>
<%
	request.setAttribute("pageName", "projects.jsp");
%>
<%@ include file="common/header.jsp"%>
<form class="form-horizontal">
	<%
		Project projectService = new Project();
		Project project = projectService.findByID(request
				.getParameter("id"));
	%>
	<h2 style="float: left;"><%=project.getName()%>
		(<a href="<%=project.getUrl()%>"><%=project.getDepartment()%></a>)
	</h2>
	<p style="float: right;">
		<a class="btn" href="project-edit.jsp?<%=project.getId()%>">Edit
			&raquo;</a>
	</p>
	<p style="clear: both;"><%=project.getDescription()%></p>
	<fieldset>
		<legend>
			Migration Strategy <a class="btn btn-small" style="float: right"
				href="strategy-edit.jsp?<%=project.getId()%>">Edit &raquo;</a>
		</legend>
		<div class="control-group">
			<%
				for (CDMScenario cdmScenario : project.getCdmScenarios()) {
			%>
			<label class="control-label">Scenario</label>
			<div class="controls">
				<label class="checkbox inline"><input type="checkbox"
					id="optionsCheckbox" checked="checked" disabled="disabled"><%=cdmScenario.getName()%></label>
				<p class="help-block"><%=cdmScenario.getDescription()%></p>
			</div>
			<%
				}
			%>
		</div>
	</fieldset>
</form>
<form class="form-horizontal">
	<fieldset>
		<legend>
			Cloud Data Store <a class="btn btn-small" style="float: right"
				href="data-store-edit.jsp?<%=project.getId()%>">Edit &raquo;</a>
		</legend>
		<div class="control-group">
			<label class="control-label">Name</label>
			<div class="controls">
				<label class="checkbox" style="padding-left: 0"><%=project.getCloudDataStore().getName()%></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Provider</label>
			<div class="controls">
				<label class="checkbox" style="padding-left: 0"><%=project.getCloudDataStore().getProvider()%></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Description</label>
			<div class="controls">
				<label class="checkbox" style="padding-left: 0"><%=project.getCloudDataStore().getDescription()%></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Website</label>
			<div class="controls">
				<label class="checkbox" style="padding-left: 0"><a
					href="<%=project.getCloudDataStore().getWebsite()%>"><%=project.getCloudDataStore().getWebsite()%></a></label>
			</div>
		</div>
	</fieldset>
</form>
<form class="form-horizontal">
	<fieldset>
		<legend>
			Cloud Data Migration Strategy <a class="btn btn-small"
				style="float: right"
				href="migration-strategy-edit.jsp?<%=project.getId()%>">Edit
				&raquo;</a>
		</legend>
		<%
			String lastCriterion = "";

			CDMStrategy cdmStrategyService = new CDMStrategy();
			Map<String, ArrayList<CDMStrategy>> strategy = cdmStrategyService
					.findAllByProject(project.getId());
			for (ArrayList<CDMStrategy> properties : strategy.values()) {
		%>

		<%
			if (!properties.get(0).getCdmCriterionPossibleValue()
						.getCdmCriterion().getId().equals(lastCriterion)) {
		%><div class="control-group">
			<label class="control-label"><%=properties.get(0).getCdmCriterionPossibleValue()
							.getCdmCriterion().getName()%></label>
			<%
				}
			%><div class="controls">
				<%
					Collection<CDMCriterionPossibleValue> possibleValues = properties
								.get(0)
								.getCdmCriterionPossibleValue()
								.getPossibleValues(
										properties.get(0)
												.getCdmCriterionPossibleValue()
												.getCdmCriterion().getId());
						for (CDMCriterionPossibleValue possibleValue : possibleValues) {
				%>
				<label class="checkbox inline"> <input type="checkbox"
					id="optionsCheckbox" value="<%=possibleValue.getId()%>"
					<%boolean checked = false;
					String inputValue = "";
					for (CDMStrategy property : properties) {
						if (possibleValue.getId()
								.equals(property.getCdmCriterionPossibleValue()
										.getId())) {
							checked = true;
						}
					}%>
					<%if (checked) {%> checked="checked" <%}%> disabled="disabled">
					<%=possibleValue.getName()%> <%%>
				</label>
				<%
					}
				%>
			</div>
			<%
				if (!properties.get(0).getCdmCriterionPossibleValue()
							.getCdmCriterion().getId().equals(lastCriterion)) {
			%>
		</div>
		<%
			}
		%>
		<%
			lastCriterion = properties.get(0)
						.getCdmCriterionPossibleValue().getCdmCriterion()
						.getId();
			}
		%>
	</fieldset>
</form>
<form class="form-horizontal">
	<fieldset>
		<legend>
			Cloud Data Hosting Solution <a class="btn btn-small"
				style="float: right"
				href="cloud-data-hosting-solution-edit.jsp?<%=project.getId()%>">Edit
				&raquo;</a>
		</legend>
		<br />
		<%
			String lastCategory = "";
			CloudDataHostingSolution cloudDataHostingSolutionService = new CloudDataHostingSolution();
			Map<String, ArrayList<CloudDataHostingSolution>> cdhs = cloudDataHostingSolutionService
					.findAllByProject(project.getId());
			for (ArrayList<CloudDataHostingSolution> properties : cdhs.values()) {
		%>
		<%
			if (!properties.get(0).getCdhsCriterionPossibleValue()
						.getCdhsCriterion().getCdhsCategory().getId()
						.equals(lastCategory)) {
		%>
		<p style="font-size: 1.2em;">
			<strong> <%=properties.get(0).getCdhsCriterionPossibleValue()
							.getCdhsCriterion().getCdhsCategory().getName()%></strong>
		</p>
		<%
			}
		%>
		<%
			if (!properties.get(0).getCdhsCriterionPossibleValue()
						.getCdhsCriterion().getId().equals(lastCriterion)) {
		%><div class="control-group">
			<label class="control-label"><%=properties.get(0).getCdhsCriterionPossibleValue()
							.getCdhsCriterion().getName()%></label>
			<%
				}
			%><div class="controls">
				<%
					Collection<CDHSCriterionPossibleValue> possibleValues = properties
								.get(0)
								.getCdhsCriterionPossibleValue()
								.getPossibleValues(
										properties.get(0)
												.getCdhsCriterionPossibleValue()
												.getCdhsCriterion().getId());
						for (CDHSCriterionPossibleValue possibleValue : possibleValues) {
				%>
				<label class="checkbox inline"> <input type="checkbox"
					id="optionsCheckbox" value="<%=possibleValue.getId()%>"
					<%boolean checked = false;
					String inputValue = "";
					for (CloudDataHostingSolution property : properties) {
						if (possibleValue.getId().equals(
								property.getCdhsCriterionPossibleValue()
										.getId())) {
							checked = true;
						}
						inputValue = property.getValue();
					}%>
					<%if (checked) {%> checked="checked" <%}%> disabled="disabled">
					<%=possibleValue.getName()%> <%
 	if (possibleValue.getType() == CDHSCriterionPossibleValue.Type.INPUT) {
 %>: <input name="<%=possibleValue.getId()%>-value"
					value="<%=inputValue != null ? inputValue : ""%>"
					disabled="disabled"
					style="width: <%=inputValue != null ? inputValue.length() * 7 + 10
								: 100%>px;" />
					<%
						}
					%>
				</label>
				<%
					}
				%>
			</div>
			<%
				if (!properties.get(0).getCdhsCriterionPossibleValue()
							.getCdhsCriterion().getId().equals(lastCriterion)) {
			%>
		</div>
		<%
			}
		%>
		<%
			lastCriterion = properties.get(0)
						.getCdhsCriterionPossibleValue().getCdhsCriterion()
						.getId();
				lastCategory = properties.get(0)
						.getCdhsCriterionPossibleValue().getCdhsCriterion()
						.getCdhsCategory().getId();
			}
		%>
	</fieldset>
</form>
<form class="">
	<fieldset>
		<legend>
			Local Data Layer Description <a class="btn btn-small"
				style="float: right"
				href="local-data-layer-description-edit.jsp?<%=project.getId()%>">Edit
				&raquo;</a>
		</legend>
		<br />
		<%
			LocalDBLProperty localDBLPropertyService = new LocalDBLProperty();
			Map<String, ArrayList<LocalDBLProperty>> localDBLProperties = localDBLPropertyService
					.findAllByProject(project.getId());
			for (ArrayList<LocalDBLProperty> properties : localDBLProperties
					.values()) {
		%>
		<%
			if (!properties.get(0).getLocalDBLCriterionPossibleValue()
						.getLocalDBLCriterion().getLocalDBLCategory().getId()
						.equals(lastCategory)) {
		%>
		<p style="font-size: 1.2em;">
			<strong> <%=properties.get(0)
							.getLocalDBLCriterionPossibleValue()
							.getLocalDBLCriterion().getLocalDBLCategory()
							.getName()%></strong>
		</p>
		<%
			}
		%>
		<%
			if (!properties.get(0).getLocalDBLCriterionPossibleValue()
						.getLocalDBLCriterion().getId().equals(lastCriterion)) {
		%>
		<label><%=properties.get(0)
							.getLocalDBLCriterionPossibleValue()
							.getLocalDBLCriterion().getName()%></label>
		<%
			}
		%><div class="controls" style="margin-left: 1em; margin-bottom: 1em;">
			<%
				Collection<LocalDBLCriterionPossibleValue> possibleValues = properties
							.get(0)
							.getLocalDBLCriterionPossibleValue()
							.getPossibleValues(
									properties.get(0)
											.getLocalDBLCriterionPossibleValue()
											.getLocalDBLCriterion().getId());
					for (LocalDBLCriterionPossibleValue possibleValue : possibleValues) {
			%>
			<label class="checkbox inline"> <input type="checkbox"
				id="optionsCheckbox" value="<%=possibleValue.getId()%>"
				<%boolean checked = false;
					String inputValue = "";
					for (LocalDBLProperty property : properties) {
						if (possibleValue.getId().equals(
								property.getLocalDBLCriterionPossibleValue()
										.getId())) {
							checked = true;
						}
					}%>
				<%if (checked) {%> checked="checked" <%}%> disabled="disabled">
				<%=possibleValue.getName()%>
			</label>
			<%
				}
			%>
		</div>
		<%
			if (!properties.get(0).getLocalDBLCriterionPossibleValue()
						.getLocalDBLCriterion().getId().equals(lastCriterion)) {
		%>
		<%
			}
		%>
		<%
			lastCriterion = properties.get(0)
						.getLocalDBLCriterionPossibleValue()
						.getLocalDBLCriterion().getId();
				lastCategory = properties.get(0)
						.getLocalDBLCriterionPossibleValue()
						.getLocalDBLCriterion().getLocalDBLCategory().getId();
			}
		%>
	</fieldset>
</form>
<form class="">
	<fieldset>
		<legend>
			Migration Conflicts and Possible Solutions <a class="btn btn-small"
				style="float: right"
				href="migration-conflicts-edit.jsp?<%=project.getId()%>">Edit
				&raquo;</a>
		</legend>
		<%
			Solution solutionService = new Solution();
			for (Solution solution : solutionService
					.getPossilbeSolutions(project.getId())) {
		%>
		<div class="control-group">
			<label class="control-label">Possible Solution: <%=solution.getName()%></label>
			<div class="controls" style="margin-left: 1em;">
				<label class="checkbox" style="padding-left: 0">Description: <%=solution.getDescription()%></label>
				<label class="checkbox" style="padding-left: 0"><em>
						<%
							if (solution.getCdmCriterionPossibleValue1() != null
										&& solution.getCdmCriterionPossibleValue2() == null
										&& solution.getCdhsCriterionPossibleValue() == null
										&& solution.getLocalDBLCriterionPossibleValue() == null) {
						%>Conflict: '<%=solution.getCdmCriterionPossibleValue1()
							.getName()%>' of criterion '<%=solution.getCdmCriterionPossibleValue1()
							.getCdmCriterion().getName()%>' is selected.<%
							} else if (solution.getCdmCriterionPossibleValue1() != null
										&& solution.getCdmCriterionPossibleValue2() != null
										&& solution.getCdhsCriterionPossibleValue() == null
										&& solution.getLocalDBLCriterionPossibleValue() == null) {
						%>Conflict: '<%=solution.getCdmCriterionPossibleValue1()
							.getName()%>' of criterion '<%=solution.getCdmCriterionPossibleValue1()
							.getCdmCriterion().getName()%>' and '<%=solution.getCdmCriterionPossibleValue2()
							.getName()%>' of criterion '<%=solution.getCdmCriterionPossibleValue2()
							.getCdmCriterion().getName()%>' is selected.<%
							}
						%>
				</em></label>
			</div>
		</div>
		<%
			}
		%>
	</fieldset>
</form>
<%@ include file="common/footer.jsp"%>
