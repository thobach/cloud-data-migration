<%@page
	import="com.clouddatamigration.classification.model.LocalDBLCriterionPossibleValue"%>
<%@page
	import="com.clouddatamigration.classification.model.LocalDBLProperty"%>
<%@page
	import="com.clouddatamigration.classification.model.CDHSCriterion"%>
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
<h1>Step 4a: Describe Local Data Layer</h1>
<p class="lead">After describing your current local database layer
	we can identify patterns and solutions that can help you to migrate
	your data to the Cloud.</p>
<form class="form-horizontal well" method="post"
	action="/classification/projectLocalDBL">
	<input type="hidden" name="id" value="<%=project.getId()%>" />

	<%
		String lastCriterion = "";
		String lastCategory = "";

		LocalDBLCriterionPossibleValue localDBLCriterionPossibleValueService = new LocalDBLCriterionPossibleValue();

		LocalDBLProperty localDBLPropertyService = new LocalDBLProperty();
		Collection<LocalDBLProperty> localDBLProperties = localDBLPropertyService
				.findAllByProjectId(project.getId());

		for (LocalDBLCriterionPossibleValue localDBLCriterionPossibleValue : localDBLCriterionPossibleValueService
				.findAll("localDBLCriterion.localDBLCategory.orderNumber ASC, localDBLCriterion.orderNumber ASC, orderNumber ASC")) {
	%>
	<%
		if (!localDBLCriterionPossibleValue.getLocalDBLCriterion()
					.getLocalDBLCategory().getId().equals(lastCategory)) {
	%>
	<fieldset>
		<legend style="margin-bottom: 1em;"><%=localDBLCriterionPossibleValue
							.getLocalDBLCriterion().getLocalDBLCategory()
							.getName()%></legend>
		<%
			}
		%>
		<%
			if (!localDBLCriterionPossibleValue.getLocalDBLCriterion()
						.getId().equals(lastCriterion)) {
		%>
		<div class="control-group">
			<label class="control-label"
				style="float: none; text-align: left; width: auto; margin-left: 20px;"><%=localDBLCriterionPossibleValue
							.getLocalDBLCriterion().getName()%></label>
			<div class="controls" style="margin-left: 40px;">
				<%
					for (LocalDBLCriterionPossibleValue potentialLocalDBLCriterionPossibleValue : localDBLCriterionPossibleValue
									.getPossibleValues(localDBLCriterionPossibleValue
											.getLocalDBLCriterion().getId())) {
				%>
				<label class="radio inline"> <input type="radio"
					name="<%=potentialLocalDBLCriterionPossibleValue
								.getLocalDBLCriterion().getId()%>"
					value="<%=potentialLocalDBLCriterionPossibleValue
								.getId()%>"
					<%boolean checked = false;
						String inputValue = "";
						for (LocalDBLProperty property : localDBLProperties) {
							if (potentialLocalDBLCriterionPossibleValue
									.getId()
									.equals(property
											.getLocalDBLCriterionPossibleValue()
											.getId())) {
								checked = true;
							}
						}%>
					<%if (checked) {%> checked="checked" <%}%>> <%=potentialLocalDBLCriterionPossibleValue
								.getName()%>
				</label>
				<%
					}
				%>
			</div>
		</div>
		<%
			}
		%>
		<%
			if (!localDBLCriterionPossibleValue.getLocalDBLCriterion()
						.getLocalDBLCategory().getId().equals(lastCategory)) {
		%>
	</fieldset>
	<%
		}
	%>
	<%
		lastCriterion = localDBLCriterionPossibleValue
					.getLocalDBLCriterion().getId();
			lastCategory = localDBLCriterionPossibleValue
					.getLocalDBLCriterion().getLocalDBLCategory().getId();
		}
	%>
	<div class="form-actions" style="padding-left: 40px;">
		<a href="/classification/projects.jsp" class="btn">Cancel</a>
		<button type="submit" class="btn btn-primary"
			onclick="$('#loadingModal').modal({backdrop:'static', keyboard: false}); $('#loadingBar').delay(100).animate({'width':'25%'},1000).animate({'width':'50%'},1000).animate({'width':'75%'},1000).animate({'width':'100%'},1000); return true;">Save
			&raquo;</button>
	</div>
</form>
<%@ include file="../common/footer.jsp"%>
