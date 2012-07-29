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
%>
<h1 style="margin-bottom: 0.3em;">Describe Your Migration Project</h1>
<p class="lead">It's always good to manifest what your project is
	all about, why it is necessary, who are the stakeholders, etc.</p>
<form class="form-horizontal well" method="post"
	action="/classification/project">

	<%
		if (project != null && project.getId() != null) {
	%>
	<input type="hidden" name="id" value="<%=project.getId()%>" />
	<%
		}
	%>
	<fieldset>
		<legend>Project Overview</legend>
		<div class="control-group">
			<label class="control-label">Project Name</label>
			<div class="controls">
				<input name="name"
					value="<%=project.getName() != null ? project.getName() : ""%>"
					class="span4" type="text" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Department(s) Using the
				Component to be Migrated</label>
			<div class="controls">
				<input name="department"
					value="<%=project.getDepartment() != null ? project.getDepartment()
					: ""%>"
					class="span4" type="text" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Website of the Component to be
				Migrated</label>
			<div class="controls">
				<input name="url"
					value="<%=project.getUrl() != null ? project.getUrl() : ""%>"
					class="span6" type="text" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Project Description</label>
			<div class="controls">
				<textarea name="description" style="height: 15em; width: 98.5%;"><%=project.getDescription() != null ? project
					.getDescription() : ""%></textarea>
			</div>
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
