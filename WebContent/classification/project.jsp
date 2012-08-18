<%@page import="com.clouddatamigration.classification.model.Type"%>
<%@page import="com.clouddatamigration.classification.model.Impact"%>
<%@page
	import="com.clouddatamigration.classification.model.SelectionType"%>
<%@page
	import="com.clouddatamigration.CloudDataMigrationContextListener"%>
<%@page import="com.clouddatamigration.migration.model.MigrationService"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Set"%>
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
<%@page import="com.clouddatamigration.classification.model.Project"%>
<%@page import="com.clouddatamigration.classification.model.CDMScenario"%>
<%@page import="com.clouddatamigration.store.model.CloudDataStore"%>
<%@page
	import="com.clouddatamigration.classification.model.CDHSCriterion"%>
<%@page
	import="com.clouddatamigration.classification.model.CDMCriterion"%>
<%@page
	import="com.clouddatamigration.classification.model.CDMCriterionPossibleValue"%>
<%@page
	import="com.clouddatamigration.classification.model.CDHSCriterionPossibleValue"%>
<%@page
	import="com.clouddatamigration.store.model.CloudDataStoreProperty"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%
	User user = new User();
	String sessionToken = user.findSessionToken(request.getCookies());
	user = user.findBySessionToken(sessionToken);
	if (sessionToken == null || user == null) {
		response.setStatus(HttpServletResponse.SC_SEE_OTHER);
		response.setHeader("Location",
				"/index.jsp?error=sessionTimeout");
		return;
	}
%>
<%
	request.setAttribute("pageName", "projects.jsp");
%>
<%@ include file="../common/header.jsp"%>
<%
	if (request.getParameter("info") != null
			&& request.getParameter("info").equals("added")) {
%><div class="alert alert-success">
	Way to go! <i class="icon-heart"></i> Your project was saved.
</div>
<%
	}
%>
<form class="form-horizontal">
	<%
		Project projectService = new Project();
		Project project = projectService.findByID(request
				.getParameter("id"));
	%>
	<h1 style="float: left;">
		<a href="<%=project.getUrl()%>"><%=project.getName()%></a> (<%=project.getDepartment()%>)
	</h1>
	<p style="float: right;">
		<a class="btn"
			href="/classification/set-project-description.jsp?id=<%=project.getId()%>">Edit
			&raquo;</a>
	</p>
	<p style="clear: both;" class="lead"><%=project.getDescription()%></p>
	<p>
		Follow Your Migration Steps: <a href="#step1">1. Identify Cloud
			migration scenario</a> - <a href="#step2">2. Describe desired Cloud
			data hosting solution</a> - <a href="#step3">3. Select Cloud data
			store</a> - <a href="#step4">4. Identify patterns to solve potential
			conflicts</a> - <a href="#step5">5. Adapt data access layer and upper
			application layers if needed</a> - <a href="#step6">6. Migrate data
			to the selected Cloud data store</a>
	</p>
	<fieldset>
		<legend id="step1" style="margin-bottom: 0;">
			Step 1a: Select Migration Scenario <a class="btn btn-small"
				style="float: right"
				href="/classification/set-migration-scenario.jsp?id=<%=project.getId()%>">Edit
				&raquo;</a>
		</legend>
		<%
			for (CDMScenario cdmScenario : project.getCdmScenarios()) {
		%>
		<div class="control-group">
			<label class="control-label">Scenario</label>
			<div class="controls">
				<label class="radio inline"><input type="radio"
					id="optionsCheckbox" checked="checked" disabled="disabled"><%=cdmScenario.getName()%></label>
				<p class="help-block" style="margin-top: 0.2em; margin-bottom: 1em;"><%=cdmScenario.getDescription()%></p>
			</div>
		</div>
		<%
			}
			if (project.getCdmScenarios().size() == 0) {
		%><div class="control-group">
			<p class="alert alert-info">First select your Cloud data
				migration scenario.</p>
		</div>
		<%
			}
		%>

	</fieldset>
	<fieldset>
		<legend style="margin-bottom: 0;">
			Step 1b: Refine Cloud Data Migration Strategy <a
				class="btn btn-small" style="float: right"
				href="/classification/set-migration-strategy.jsp?id=<%=project.getId()%>">Edit
				&raquo;</a>
		</legend>
		<%
			String lastCriterion = "";

			Set<CDMCriterionPossibleValue> cdmStrategies = project
					.getCdmCriterionPossibleValues();
			for (CDMCriterionPossibleValue cdmCriterionPossibleValue : cdmStrategies) {
				if (!cdmCriterionPossibleValue.getCdmCriterion().getId()
						.equals(lastCriterion)) {
		%>

		<%
			if (!cdmCriterionPossibleValue.getCdmCriterion().getId()
							.equals(lastCriterion)) {
		%><div class="control-group">
			<label class="control-label"><%=cdmCriterionPossibleValue.getCdmCriterion()
								.getName()%></label>
			<%
				}
			%><div class="controls">
				<%
					Collection<CDMCriterionPossibleValue> possibleValues = cdmCriterionPossibleValue
									.getPossibleValues(cdmCriterionPossibleValue
											.getCdmCriterion().getId());
							for (CDMCriterionPossibleValue possibleValue : possibleValues) {
				%>
				<label
					class="<%=(possibleValue.getCdmCriterion()
								.getSelectionType() == SelectionType.SINGLE ? "radio"
								: "checkbox")%> inline">
					<input
					type="<%=(possibleValue.getCdmCriterion()
								.getSelectionType() == SelectionType.SINGLE ? "radio"
								: "checkbox")%>"
					id="optionsCheckbox" value="<%=possibleValue.getId()%>"
					<%boolean checked = false;
						String inputValue = "";
						for (CDMCriterionPossibleValue cdmStrategy : cdmStrategies) {
							if (possibleValue.getId().equals(
									cdmStrategy.getId())) {
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
				if (!cdmCriterionPossibleValue.getCdmCriterion().getId()
								.equals(lastCriterion)) {
			%>
		</div>
		<%
			}
		%>
		<%
			}
				lastCriterion = cdmCriterionPossibleValue.getCdmCriterion()
						.getId();
			}

			if (lastCriterion.isEmpty()) {
		%><div class="control-group">
			<p class="alert alert-info">After determining your high level
				Cloud data migration scenario you can specify some details of your
				migration strategy in order to find out if there are any potential
				conflicts.</p>
		</div>
		<%
			}
		%>
	</fieldset>
	<fieldset>
		<legend style="margin-bottom: 0;"> Step 1c: Identify
			Potential Migration Strategy Conflicts </legend>
		<%
			int numberOfConflicts = 0;
			for (CDMScenario cdmScenario : project.getCdmScenarios()) {
				for (CDMCriterionPossibleValue actualCdmCriterionPossibleValue : project
						.getCdmCriterionPossibleValues()) {
					boolean criterionValueSupported = false;
					ArrayList<String> supportedValues = new ArrayList<String>();
					for (CDMCriterionPossibleValue cdmCriterionPossibleValue : cdmScenario
							.getCdmCriterionPossibleValues()) {
						if (cdmCriterionPossibleValue
								.getCdmCriterion()
								.getId()
								.equals(actualCdmCriterionPossibleValue
										.getCdmCriterion().getId())) {
							supportedValues
									.add("'"
											+ cdmCriterionPossibleValue
													.getName() + "'");
						}
						if (cdmCriterionPossibleValue.getId().equals(
								actualCdmCriterionPossibleValue.getId())) {
							criterionValueSupported = true;
						}
					}
					if (!criterionValueSupported) {
						ArrayList<String> supportedScenarios = new ArrayList<String>();
						for (CDMScenario supportedCdmScenario : actualCdmCriterionPossibleValue
								.getCdmScenarios()) {
							boolean scenarioAlreadySelected = false;
							for (CDMScenario actualCdmScenario : project
									.getCdmScenarios()) {
								if (actualCdmScenario.getId().equals(
										supportedCdmScenario.getId())) {
									scenarioAlreadySelected = true;
								}
							}
							if (scenarioAlreadySelected) {
								supportedScenarios.add("'<strong>"
										+ supportedCdmScenario.getName()
										+ "</strong>'");
							} else {
								supportedScenarios.add("'"
										+ supportedCdmScenario.getName() + "'");
							}
						}
						numberOfConflicts++;
		%><div class="control-group" style="margin-bottom: 0">
			<label class="control-label">Conflict #<%=numberOfConflicts%></label>
			<div class="controls">
				<label class="checkbox" style="padding-left: 0">The scenario
					'<%=cdmScenario.getName()%>' does not support '<%=actualCdmCriterionPossibleValue.getName()%>'
					for '<%=actualCdmCriterionPossibleValue
								.getCdmCriterion().getName()%>'.
				</label>
			</div>
		</div>
		<div class="control-group" style="margin-bottom: 0">
			<label class="control-label">Possible Solution #<%=numberOfConflicts%>.1
			</label>
			<div class="controls">
				<label class="checkbox" style="padding-left: 0"><em>Change
						migration scenario</em>. The scenario '<%=cdmScenario.getName()%>'
					supports <%
					if (supportedValues.isEmpty()) {
				%>no values<%
					} else {
				%><%=Arrays
									.toString(supportedValues.toArray())
									.replace('[', ' ').replace(']', ' ')%> <%
 	}
 %> for the criterion '<%=actualCdmCriterionPossibleValue
								.getCdmCriterion().getName()%>'.</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Possible Solution #<%=numberOfConflicts%>.2
			</label>
			<div class="controls">
				<label class="checkbox" style="padding-left: 0"> <em>Change
						migration strategy</em>: The value '<%=actualCdmCriterionPossibleValue.getName()%>'
					for criterion '<%=actualCdmCriterionPossibleValue
								.getCdmCriterion().getName()%>' is supported by the scenarios <%=Arrays.toString(supportedScenarios.toArray())
								.replace('[', ' ').replace(']', '.')%></label>
			</div>
		</div>
		<%
			}
				}
			}
			if (numberOfConflicts == 0) {
		%><div class="control-group">
			<p class="alert alert-info">Great, there seem to be now conflicts
				in your Clout data migration strategy.</p>
		</div>
		<%
			}
		%>
	</fieldset>
	<fieldset>
		<legend id="step2" style="margin-bottom: 0;">
			Step 2: Describe Desired Cloud Data Hosting Solution <a
				class="btn btn-small" style="float: right"
				href="/classification/set-cloud-data-hosting-solution.jsp?id=<%=project.getId()%>">Edit
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
				<label
					class="<%=possibleValue.getCdhsCriterion()
							.getSelectionType() == SelectionType.SINGLE ? "radio"
							: "checkbox"%> inline">
					<input
					type="<%=possibleValue.getCdhsCriterion()
							.getSelectionType() == SelectionType.SINGLE ? "radio"
							: "checkbox"%>"
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
					<%if (checked) {%> checked="checked" <%}%> disabled="disabled"
					<%=possibleValue.getType() == Type.INPUT ? "style=\"margin-top: 8px;\""
							: ""%>>
					<%
						out.print(possibleValue.getName());
								if (possibleValue.getType() == Type.INPUT) {
					%>: <input name="<%=possibleValue.getId()%>-value"
					value="<%=inputValue != null ? inputValue : ""%>"
					disabled="disabled" type="text"
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

			if (lastCategory.isEmpty()) {
		%><div class="control-group">
			<p class="alert alert-info">When you describe the requirements
				for a Cloud data hosting solution you can laster select from a
				number of Cloud data stores that match your requirements.</p>
		</div>
		<%
			}
		%>
	</fieldset>
	<fieldset>
		<legend id="step3" style="margin-bottom: 0;">
			Step 3: Select Cloud Data Store <a class="btn btn-small"
				style="float: right"
				href="/classification/set-cloud-data-store.jsp?id=<%=project.getId()%>">Edit
				&raquo;</a>
		</legend>
		<%
			if (project.getCloudDataStore() != null) {
		%>
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
		<%
			} else {
		%>
		<div class="control-group">
			<p class="alert alert-info">After you defined your desired Cloud
				data hosting solution you can select from a number of Cloud data
				stores that match your requirements.</p>
		</div>
		<%
			}
		%>
	</fieldset>
	<fieldset>
		<legend id="step4" style="margin-bottom: 0;">
			Step 4a: Describe Local Data Layer <a class="btn btn-small"
				style="float: right"
				href="/classification/set-local-database-layer.jsp?id=<%=project.getId()%>">Edit
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
		<label style="margin-left: 20px;"><%=properties.get(0)
							.getLocalDBLCriterionPossibleValue()
							.getLocalDBLCriterion().getName()%></label>
		<%
			}
		%>
		<div class="controls" style="margin-left: 40px; margin-bottom: 1em;">
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
			lastCriterion = properties.get(0)
						.getLocalDBLCriterionPossibleValue()
						.getLocalDBLCriterion().getId();
				lastCategory = properties.get(0)
						.getLocalDBLCriterionPossibleValue()
						.getLocalDBLCriterion().getLocalDBLCategory().getId();
			}

			if (localDBLProperties.size() == 0) {
		%><div class="control-group">
			<p class="alert alert-info">When you describe your current data
				layer we can identify potential patterns and solutions that help you
				during your Cloud data migration.</p>
		</div>
		<%
			}
		%>
	</fieldset>
	<fieldset>
		<legend style="margin-bottom: 0;"> Step 4b: Identify Patterns
			to Solve Potential Migration Conflicts </legend>
		<%
			int solutionNumber = 0;
			Solution solutionService = new Solution();
			for (Solution solution : solutionService
					.getPossilbeSolutions(project.getId())) {
				solutionNumber++;
		%>
		<div class="control-group" style="margin-bottom: 0">
			<label class="control-label">Conflict #<%=solutionNumber%></label>
			<div class="controls">
				<label class="checkbox" style="padding-left: 0"> <%
 	if (solution.getCdmCriterionPossibleValue1() != null
 				&& solution.getCdmCriterionPossibleValue2() == null
 				&& solution.getCdhsCriterionPossibleValue() == null
 				&& solution.getLocalDBLCriterionPossibleValue() == null) {
 %>You selected '<%=solution.getCdmCriterionPossibleValue1()
							.getCdmCriterion().getName()%>' for the cloud data migration
					criterion '<%=solution.getCdmCriterionPossibleValue1()
							.getName()%>'.<%
 	} else if (solution.getCdmCriterionPossibleValue1() != null
 				&& solution.getCdmCriterionPossibleValue2() != null
 				&& solution.getCdhsCriterionPossibleValue() == null
 				&& solution.getLocalDBLCriterionPossibleValue() == null) {
 %>You selected '<%=solution.getCdmCriterionPossibleValue1()
							.getName()%>' for the cloud data migration criterion '<%=solution.getCdmCriterionPossibleValue1()
							.getCdmCriterion().getName()%>' and '<%=solution.getCdmCriterionPossibleValue2()
							.getName()%>' for the cloud data migration criterion '<%=solution.getCdmCriterionPossibleValue2()
							.getCdmCriterion().getName()%>'.<%
 	} else if (solution.getCdmCriterionPossibleValue1() != null
 				&& solution.getCdmCriterionPossibleValue2() == null
 				&& solution.getCdhsCriterionPossibleValue() != null
 				&& solution.getLocalDBLCriterionPossibleValue() == null) {
 %>You selected '<%=solution.getCdmCriterionPossibleValue1()
							.getName()%>' for the cloud data migration criterion '<%=solution.getCdmCriterionPossibleValue1()
							.getCdmCriterion().getName()%>' and your cloud data store has '<%=solution.getCdhsCriterionPossibleValue()
							.getName()%>' selected for the criterion '<%=solution.getCdhsCriterionPossibleValue()
							.getCdhsCriterion().getName()%>'.<%
 	} else if (solution.getCdmCriterionPossibleValue1() == null
 				&& solution.getCdmCriterionPossibleValue2() == null
 				&& solution.getCdhsCriterionPossibleValue() != null
 				&& solution.getLocalDBLCriterionPossibleValue() == null) {
 %>Your cloud data store has '<%=solution.getCdhsCriterionPossibleValue()
							.getName()%>' selected for the criterion '<%=solution.getCdhsCriterionPossibleValue()
							.getCdhsCriterion().getName()%>'.<%
 	} else if (solution.getCdmCriterionPossibleValue1() == null
 				&& solution.getCdmCriterionPossibleValue2() == null
 				&& solution.getCdhsCriterionPossibleValue() == null
 				&& solution.getLocalDBLCriterionPossibleValue() != null) {
 %>You selected '<%=solution.getLocalDBLCriterionPossibleValue()
							.getName()%>' for the local data base criterion '<%=solution.getLocalDBLCriterionPossibleValue()
							.getLocalDBLCriterion().getName()%>'.<%
 	} else if (solution.getCdmCriterionPossibleValue1() == null
 				&& solution.getCdmCriterionPossibleValue2() == null
 				&& solution.getCdhsCriterionPossibleValue() != null
 				&& solution.getLocalDBLCriterionPossibleValue() != null) {
 %>You selected '<%=solution.getLocalDBLCriterionPossibleValue()
							.getName()%>' for the local data base criterion '<%=solution.getLocalDBLCriterionPossibleValue()
							.getLocalDBLCriterion().getName()%>' and your cloud data store
					has '<%=solution.getCdhsCriterionPossibleValue()
							.getName()%>' selected for the criterion '<%=solution.getCdhsCriterionPossibleValue()
							.getCdhsCriterion().getName()%>'.<%
 	}
 %>
				</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Possible Solution #<%=solutionNumber%></label>
			<div class="controls">
				<label class="checkbox" style="padding-left: 0"><em><%=solution.getName()%></em>.
					<%=solution.getDescription()%></label>
			</div>
		</div>
		<%
			}
			if (solutionNumber == 0) {
		%><div class="control-group">
			<p class="alert alert-info">Good! Currently there are no patterns
				or solutions for potential conflicts.</p>
		</div>
		<%
			}
		%>
	</fieldset>
	<fieldset>
		<legend id="step5" style="margin-bottom: 0;"> Step 5: Adapt
			Data Access Layer And Upper Application Layers </legend>

		<%
			if (project.getCdmScenarios().size() == 0) {
		%>
		<div class="control-group">
			<p class="alert alert-info">If you select a migration scenario
				you will see a list of impacts of the migration on the network, data
				access and application logic layer.</p>
		</div>
		<%
			} else {
				String impactCategory = "";
				for (CDMScenario cdmScenario : project.getCdmScenarios()) {
					for (Impact impact : cdmScenario.getCdmImpacts()) {
						if (!impactCategory.equals(impact.getImpactCategory()
								.getName())) {
		%>
		<div class="control-group">
		<p style="font-size: 1.2em;">
			<strong><%=cdmScenario.getName() + " - "
									+ impact.getImpactCategory().getName()%></strong>
		</p>
		</div>
		<%
			}
		%>
		<div class="control-group">
			<label class="control-label"><%=impact.getName()%></label>
			<div class="controls">
				<label class="checkbox" style="padding-left: 0"><%=impact.getDescription()%></label>
			</div>
		</div>
		<%
			impactCategory = impact.getImpactCategory().getName();
					}

				}
			}
		%>

	</fieldset>
</form>
<form class="form-horizontal" action="/migration/migration"
	method="POST" target="migrationIframeExport" id="migrationForm">
	<input type="hidden" name="action" value="export" id="migrationAction" />
	<fieldset>
		<legend id="step6" style="margin-bottom: 0;"> Step 6: Migrate
			Data to the Selected Cloud Data Store </legend>
		<div class="control-group">
			<%
				MigrationService migrationService = (MigrationService) getServletContext()
						.getAttribute(
								CloudDataMigrationContextListener.MIGRATION_SERVICE);
			%>
			<label class="control-label">Source System</label>
			<div class="controls">
				<%
					for (String sourceId : migrationService.getSourceIds()) {
				%><label class="radio inline"> <input type="radio"
					name="sourceSystemId" value="<%=sourceId%>"
					onchange="$('.sourceSystem').addClass('hide'); $('#<%=sourceId%>').removeClass('hide');">
					<%=sourceId%>
				</label>
				<p class="help-block" style="margin-bottom: 1.5em;"><%=migrationService.getSourceInstructions(sourceId)%></p>
				<div class="form-inline hide sourceSystem" id="<%=sourceId%>">
					<%
						for (String connectionParam : migrationService
									.getSourceConnectionParams(sourceId)) {
					%>
					<label for="<%=sourceId + "_" + connectionParam%>"
						style="width: 140px; margin-bottom: 1.5em;"><%=connectionParam%>:</label>
					<input id="<%=sourceId + "_" + connectionParam%>" type="text"
						name="<%=sourceId + "_" + connectionParam%>" value="" /><br />
					<%
						}
					%>
				</div>
				<%
					}
				%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Target System</label>
			<div class="controls">
				<%
					for (String targetId : migrationService.getTargetIds()) {
				%><label class="radio inline"> <input type="radio"
					name="targetSystemId" value="<%=targetId%>"
					onchange="$('.targetSystem').addClass('hide'); $('#<%=targetId%>').removeClass('hide');">
					<%=targetId%>
				</label>
				<p class="help-block" style="margin-bottom: 1.5em;"><%=migrationService.getTargetInstructions(targetId)%></p>
				<div class="form-inline hide targetSystem" id="<%=targetId%>">
					<%
						for (String connectionParam : migrationService
									.getTargetConnectionParams(targetId)) {
					%>
					<label for="<%=targetId + "_" + connectionParam%>"
						style="width: 140px; margin-bottom: 1.5em;"><%=connectionParam%>:</label>
					<input id="<%=targetId + "_" + connectionParam%>" type="text"
						name="<%=targetId + "_" + connectionParam%>" value="" /><br />
					<%
						}
					%>
				</div>
				<%
					}
				%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Migration Log Export</label>
			<div class="controls">
				<iframe id="migrationIframeExport" name="migrationIframeExport"
					src="/migration/logmsg.txt" style="width: 100%; height: 200px;"
					onload="if(this.contentWindow.location.href.substr(-10)!='logmsg.txt') {$('#migrationAction')[0].value='import'; $('#migrationForm')[0].target='migrationIframeImport'; $('#migrationForm')[0].submit()}"></iframe>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Migration Log Import</label>
			<div class="controls">
				<iframe id="migrationIframeImport" name="migrationIframeImport"
					src="/migration/logmsg.txt" style="width: 100%; height: 200px;"
					onload="if(this.contentWindow.location.href.substr(-10)!='logmsg.txt') {$('#migrationAction')[0].value='export'; $('#migrationForm')[0].target='migrationIframeExport';}"></iframe>
			</div>
		</div>
	</fieldset>
	<div class="form-actions">
		<a href="/classification/project.jsp?id=<%=project.getId()%>"
			class="btn">Cancel</a>
		<button type="submit" class="btn btn-primary"
			onclick="$('#migrationIframeExport')[0].src='/migration/logmsg.txt'; $('#migrationIframeImport')[0].src='/migration/logmsg.txt'; return true;">Start
			Migration &raquo;</button>
	</div>
</form>
<%@ include file="../common/footer.jsp"%>
