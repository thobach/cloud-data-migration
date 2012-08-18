<%@page import="com.clouddatamigration.classification.model.CDMScenario"%>
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
<h1>Step 1a: Select Migration Scenario</h1>
<p class="lead">In the first step of the Cloud data migration
	process you describe the main idea of your migration. What is the
	reason for the migration project?</p>
<form class="form-horizontal well" method="post"
	action="/classification/projectScenarios">
	<input type="hidden" name="id" value="<%=project.getId()%>" />
	<fieldset>
		<legend style="margin-bottom: 0.5em">Possible Cloud Data
			Migration Scenarios</legend>
		<p>Note: If your migration projects contains multiple scenarios,
			please create one project per scenario.</p>
		<div class="control-group">
			<label class="control-label">Cloud Data Migration Scenario</label>
			<%
				CDMScenario cdmScenarioService = new CDMScenario();
				for (CDMScenario cdmScenario : cdmScenarioService
						.findAll("orderNumber ASC")) {
					boolean checked = false;
					for (CDMScenario usedCdmScenario : project.getCdmScenarios()) {
						if (cdmScenario.getId().equals(usedCdmScenario.getId())) {
							checked = true;
						}
					}
			%>

			<div class="controls">
				<label class="radio inline" style="width: 100%;"><img
					src="<%=cdmScenario.getImageLocation()%>"
					style="background-color: white; padding: 1em; width: 650px; float: right; margin-left: 1em; margin-right: 1em; margin-bottom: 1em;"
					class="thumbnail" /><input type="radio" name="cdmScenario"
					<%if (checked) {%> checked="checked" <%}%>
					value="<%=cdmScenario.getId()%>"><strong><%=cdmScenario.getName()%></strong><br />
					<%=cdmScenario.getDescription()%></label>
			</div>
			<%
				}
			%>
		</div>
	</fieldset>
	<div class="form-actions">
		<a href="/classification/projects.jsp" class="btn">Cancel</a>
		<button type="submit" class="btn btn-primary"
			onclick="$('#loadingModal').modal({backdrop:'static', keyboard: false}); $('#loadingBar').delay(100).animate({'width':'25%'},1000).animate({'width':'50%'},1000).animate({'width':'75%'},1000).animate({'width':'100%'},1000); return true;">Save
			&raquo;</button>
	</div>
</form>
<%@ include file="../common/footer.jsp"%>
