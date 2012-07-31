<%@page
	import="com.clouddatamigration.classification.model.CDHSCriterion.SelectionType"%>
<%@page
	import="com.clouddatamigration.classification.model.CDHSCriterion"%>
<%@page
	import="com.clouddatamigration.classification.model.CDHSCriterionPossibleValue.Type"%>
<%@page
	import="com.clouddatamigration.classification.model.CDHSCriterionPossibleValue"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page
	import="com.clouddatamigration.classification.model.CloudDataHostingSolution"%>
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
<h1>Step 2: Describe Desired Cloud Data Hosting Solution</h1>
<p class="lead">After describing the requirements for your desired
	Cloud data hosting solution you can view which Cloud data stores
	qualify to your requirements.</p>
<form class="form-horizontal well" method="post"
	action="/classification/projectCDHS">
	<input type="hidden" name="id" value="<%=project.getId()%>" />
	<%
		String lastCriterion = "";
		String lastCategory = "";
		CloudDataHostingSolution cloudDataHostingSolutionService = new CloudDataHostingSolution();
		Map<String, ArrayList<CloudDataHostingSolution>> properties = cloudDataHostingSolutionService
				.findAllByProject(project.getId());
		CDHSCriterionPossibleValue cdhsCriterionPossibleValueService = new CDHSCriterionPossibleValue();
		for (CDHSCriterionPossibleValue cdhsCriterionPossibleValue : cdhsCriterionPossibleValueService
				.findAll("cdhsCriterion.cdhsCategory.orderNumber ASC, cdhsCriterion.orderNumber ASC, orderNumber ASC")) {
	%>
	<%
		if (!cdhsCriterionPossibleValue.getCdhsCriterion()
					.getCdhsCategory().getId().equals(lastCategory)) {
	%>
	<fieldset>
		<legend><%=cdhsCriterionPossibleValue.getCdhsCriterion()
							.getCdhsCategory().getName()%></legend>

		<%
			}
		%>
		<%
			if (!cdhsCriterionPossibleValue.getCdhsCriterion().getId()
						.equals(lastCriterion)) {
		%><div class="control-group">
			<label class="control-label"><%=cdhsCriterionPossibleValue.getCdhsCriterion()
							.getName()%></label>
			<%%><div class="controls">
				<%
					Collection<CDHSCriterionPossibleValue> possibleValues = cdhsCriterionPossibleValue
									.getPossibleValues(cdhsCriterionPossibleValue
											.getCdhsCriterion().getId());
							boolean criterionSelected = false;
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
					name="<%=possibleValue.getCdhsCriterion().getId()%>"
					value="<%=possibleValue.getId()%>"
					<%boolean checked = false;
						String inputValue = "";
						if (properties.get(cdhsCriterionPossibleValue
								.getCdhsCriterion().getKey()) != null) {
							for (CloudDataHostingSolution property : properties
									.get(cdhsCriterionPossibleValue
											.getCdhsCriterion().getKey())) {
								if (possibleValue
										.getId()
										.equals(property
												.getCdhsCriterionPossibleValue()
												.getId())) {
									checked = true;
									criterionSelected = true;
								}
								inputValue = property.getValue();
							}
						}%>
					<%if (checked) {%> checked="checked" <%}%>
					<%=possibleValue.getType() == Type.INPUT ? "style=\"margin-top: 8px;\""
								: ""%>>
					<%
						out.print(possibleValue.getName());
									if (possibleValue.getType() == CDHSCriterionPossibleValue.Type.INPUT) {
					%>: <input name="<%=possibleValue.getId()%>-value"
					value="<%=inputValue != null ? inputValue : ""%>" type="text"
					style="width: <%=inputValue != null
									&& !inputValue.isEmpty() ? inputValue
									.length() * 7 + 10 : 100%>px;" />
					<%
						}
					%>
				</label>
				<%
					}
							if (possibleValues.iterator().next().getCdhsCriterion()
									.getSelectionType() == SelectionType.SINGLE) {
				%><label class="radio inline"><input type="radio"
					name="<%=possibleValues.iterator().next()
								.getCdhsCriterion().getId()%>"
					value="" <%if (!criterionSelected) {%> checked="checked" <%}%> />
					No Preference</label>
				<%
					}
				%>
				<p class="help-block">
					<i class="icon-info-sign" style="opacity: 0.5"></i>
					<%=cdhsCriterionPossibleValue.getCdhsCriterion()
							.getDescription()%></p>
			</div>
			<%%>
		</div>
		<%
			}
		%>
	</fieldset>
	<%
		lastCriterion = cdhsCriterionPossibleValue.getCdhsCriterion()
					.getId();
			lastCategory = cdhsCriterionPossibleValue.getCdhsCriterion()
					.getCdhsCategory().getId();
		}
	%>
	<div class="form-actions">
		<a href="/classification/projects.jsp" class="btn">Cancel</a>
		<button type="submit" class="btn btn-primary"
			onclick="$('#loadingModal').modal({backdrop:'static', keyboard: false}); $('#loadingBar').delay(100).animate({'width':'25%'},1000).animate({'width':'50%'},1000).animate({'width':'75%'},1000).animate({'width':'100%'},1000); return true;">Save
			&raquo;</button>
	</div>
</form>
<%@ include file="../common/footer.jsp"%>
