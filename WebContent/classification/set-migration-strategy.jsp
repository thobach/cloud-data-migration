<%@page import="com.clouddatamigration.classification.model.SelectionType"%>
<%@page
	import="com.clouddatamigration.classification.model.CDMCriterion"%>
<%@page import="java.util.Set"%>
<%@page
	import="com.clouddatamigration.classification.model.CDMCriterionPossibleValue"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
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
<h1>Step 1b: Refine Cloud Data Migration Strategy</h1>
<p class="lead">In the first step of the Cloud data migration
	process you also refine the main idea of your migration. How should the
	migration be done, what are the high level details?</p>
<form class="form-horizontal well" method="post"
	action="/classification/projectStrategy">
	<input type="hidden" name="id" value="<%=project.getId()%>" />
	<fieldset>
		<legend>Refine Cloud Data Migration Strategy</legend>
		<%
			CDMCriterion cdmCriterionService = new CDMCriterion();

			CDMCriterionPossibleValue cdmCriterionPossibleValueService = new CDMCriterionPossibleValue();

			Set<CDMCriterionPossibleValue> cdmStrategies = project
					.getCdmCriterionPossibleValues();

			for (CDMCriterion cdmCriterion : cdmCriterionService
					.findAll("orderNumber ASC")) {
		%>
		<div class="control-group">
			<label class="control-label"><%=cdmCriterion.getName()%></label>
			<div class="controls">
				<%
					Collection<CDMCriterionPossibleValue> possibleValues = cdmCriterionPossibleValueService
								.getPossibleValues(cdmCriterion.getId());
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
					name="<%=possibleValue.getCdmCriterion().getId()%>" value="<%=possibleValue.getId()%>"
					<%boolean checked = false;
					String inputValue = "";
					for (CDMCriterionPossibleValue property : cdmStrategies) {
						if (possibleValue.getId().equals(property.getId())) {
							checked = true;
						}
					}%>
					<%if (checked) {%> checked="checked" <%}%>> <%=possibleValue.getName()%>
					<%%>
				</label>
				<%
					}
				%>
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
