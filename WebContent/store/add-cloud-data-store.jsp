<%@page import="com.clouddatamigration.classification.model.Type"%>
<%@page import="com.clouddatamigration.classification.model.SelectionType"%>
<%@page
	import="com.clouddatamigration.classification.model.CloudDataHostingSolution"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ page import="com.clouddatamigration.store.model.CloudDataStore"%>
<%@ page
	import="com.clouddatamigration.classification.model.CDHSCriterion"%>
<%@ page
	import="com.clouddatamigration.classification.model.CDHSCriterionPossibleValue"%>
<%@ page
	import="com.clouddatamigration.store.model.CloudDataStoreProperty"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%
	request.setAttribute("pageName", "cloud-data-stores.jsp");
%>
<%@ include file="../common/header.jsp"%>
<%
	CloudDataStore cds = new CloudDataStore();
	if (request.getParameter("id") != null) {
		cds = cds.findByID(request.getParameter("id"));
	}

	CloudDataStoreProperty cdsProperty = new CloudDataStoreProperty();
	Map<String, ArrayList<CloudDataStoreProperty>> cdsProperties = cdsProperty
			.findAllByCDS(cds.getId());

	CDHSCriterionPossibleValue cdhsCriterionPossibleValue = new CDHSCriterionPossibleValue();
	Map<String, ArrayList<CDHSCriterionPossibleValue>> cdhs = cdhsCriterionPossibleValue
			.findAllGrouped();
%>
<%
	if (!(cds != null && cds.getId() != null)) {
%>
<h1 style="margin-bottom: 0.3em;">Add a New Cloud Data Store</h1>
<p class="alert alert-info" style="margin-bottom: 1.2em;">Thanks man!
	Your effort is very much appreciated.</p>
<%
	}
%>
<form class="form-horizontal well" method="post" action="cloudDataStore">

	<%
		if (cds != null && cds.getId() != null) {
	%>
	<input type="hidden" name="id" value="<%=cds.getId()%>" />
	<%
		}
	%>
	<fieldset>
		<legend>Description</legend>
		<div class="control-group">
			<label class="control-label">Name</label>
			<div class="controls">
				<input name="name"
					value="<%=cds.getName() != null ? cds.getName() : ""%>"
					class="span4" type="text" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Provider</label>
			<div class="controls">
				<input name="provider"
					value="<%=cds.getProvider() != null ? cds.getProvider() : ""%>"
					class="span4" type="text" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Website</label>
			<div class="controls">
				<input name="website"
					value="<%=cds.getWebsite() != null ? cds.getWebsite() : ""%>"
					class="span6" type="text" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Description</label>
			<div class="controls">
				<textarea name="description" style="height: 15em; width: 98.5%;"><%=cds.getDescription() != null ? cds.getDescription() : ""%></textarea>
			</div>
		</div>
	</fieldset>
	<%
		String lastCategory = "";
		String lastCriterion = "";
		for (ArrayList<CDHSCriterionPossibleValue> properties : cdhs
				.values()) {
	%>

	<%
		if (!properties.get(0).getCdhsCriterion().getCdhsCategory()
					.getId().equals(lastCategory)) {
	%><fieldset>
		<legend><%=properties.get(0).getCdhsCriterion()
							.getCdhsCategory().getName()%></legend>
		<%
			}
		%>
		<%
			if (!properties.get(0).getCdhsCriterion().getId()
						.equals(lastCriterion)) {
		%><div class="control-group">
			<label class="control-label"><%=properties.get(0).getCdhsCriterion().getName()%></label>
			<%
				}
			%><div class="controls">
				<%
					for (CDHSCriterionPossibleValue possibleValue : properties) {
				%>
				<label
					class="<%=possibleValue.getCdhsCriterion()
							.getSelectionType() == SelectionType.SINGLE ? "radio"
							: "checkbox"%> inline">
					<input
					type="<%=possibleValue.getCdhsCriterion()
							.getSelectionType() == SelectionType.SINGLE ? "radio"
							: "checkbox"%>"
					id="<%=possibleValue.getCdhsCriterion().getId()%>"
					name="<%=possibleValue.getCdhsCriterion().getId()%>"
					value="<%=possibleValue.getId()%>"
					<%boolean checked = false;
					String inputValue = "";
					if (cdsProperties.get(possibleValue.getCdhsCriterion()
							.getKey()) != null) {
						for (CloudDataStoreProperty cloudDataStoreProperty : cdsProperties
								.get(possibleValue.getCdhsCriterion().getKey())) {
							if (possibleValue.getId().equals(
									cloudDataStoreProperty
											.getCdhsCriterionPossibleValue()
											.getId())) {
								checked = true;
								inputValue = cloudDataStoreProperty
										.getInputValue();
							}
						}
					}%>
					<%if (checked) {%> checked="checked" <%}%>
					<%=possibleValue.getType() == Type.INPUT ? "style=\"margin-top: 8px;\""
							: ""%>>
					<%
						out.print(possibleValue.getName());
								if (possibleValue.getType() == Type.INPUT) {
					%>: <input name="<%=possibleValue.getId()%>"
					value="<%=inputValue != null ? inputValue : ""%>"
					style="width: <%=inputValue != null && !inputValue.isEmpty() ? inputValue
								.length() * 7 + 10 : 100%>px;"
					type="text" /> <%
 	}
 %>
				</label>
				<%
					}
				%>
				<p class="help-block">
					<i class="icon-info-sign" style="opacity: 0.5"></i>
					<%=properties.get(0).getCdhsCriterion().getDescription()%></p>
			</div>
			<%
				if (!properties.get(0).getCdhsCriterion().getId()
							.equals(lastCriterion)) {
			%>
		</div>
		<%
			}
		%>
		<%
			if (!properties.get(0).getCdhsCriterion().getCdhsCategory()
						.getId().equals(lastCategory)) {
		%>
	</fieldset>
	<%
		}
	%>
	<%
		lastCategory = properties.get(0).getCdhsCriterion()
					.getCdhsCategory().getId();
			lastCriterion = properties.get(0).getCdhsCriterion().getId();
		}
	%>
	<div class="form-actions">
		<a href="cloud-data-stores.jsp" class="btn">Cancel</a>
		<button type="submit" class="btn btn-primary"
			onclick="$('#loadingModal').modal({backdrop:'static', keyboard: false}); $('#loadingBar').delay(100).animate({'width':'25%'},1000).animate({'width':'50%'},1000).animate({'width':'75%'},1000).animate({'width':'100%'},1000); return true;">Save
			&raquo;</button>
	</div>
</form>
<%@ include file="../common/footer.jsp"%>
