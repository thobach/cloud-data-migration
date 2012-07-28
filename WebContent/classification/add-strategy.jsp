<%@page import="com.clouddatamigration.classification.model.CDMScenario"%>
<%@page import="com.clouddatamigration.classification.model.Project"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%
	request.setAttribute("pageName", "cloud-data-stores.jsp");
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
<%
	if (!(project != null && project.getId() != null)) {
%>
<h1 style="margin-bottom: 0.3em;">Add a New Migration Strategy</h1>
<%
	}
%>
<form class="form-horizontal well" method="post"
	action="/classification/projectStrategies">
	<input type="hidden" name="id" value="<%=project.getId()%>" />
	<fieldset>
		<legend>Possible Cloud Data Migration Scenarios</legend>
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
				<label class="checkbox inline"><input type="checkbox"
					id="cdmScenario" name="cdmScenario" <%if (checked) {%>
					checked="checked" <%}%> value="<%=cdmScenario.getId()%>"><%=cdmScenario.getName()%></label>
				<p class="help-block" style="margin-top: 0.2em; margin-bottom: 1em;">
					<i class="icon-info-sign" style="opacity: 0.5"></i>
					<%=cdmScenario.getDescription()%></p>
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
