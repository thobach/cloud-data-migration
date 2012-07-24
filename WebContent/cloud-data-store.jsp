<%@page
	import="com.clouddatamigration.classification.model.CDHSCriterionPossibleValue.Type"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ page
	import="com.clouddatamigration.classification.model.CloudDataStore"%>
<%@ page
	import="com.clouddatamigration.classification.model.CDHSCriterion"%>
<%@ page
	import="com.clouddatamigration.classification.model.CDHSCriterionPossibleValue"%>
<%@ page
	import="com.clouddatamigration.classification.model.CloudDataStoreProperty"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%
	request.setAttribute("pageName", "cloud-data-stores.jsp");
%>
<%@ include file="common/header.jsp"%>
<%
	if (request.getParameter("info") != null
			&& request.getParameter("info").equals("added")) {
%><div class="alert alert-success">
	Wow, thanks! <i class="icon-heart"></i> Your cloud data store was
	saved.
</div>
<%
	}
%>
<form class="form-horizontal well">
	<%
		CloudDataStore cloudDataStoreService = new CloudDataStore();
		CloudDataStore cds = cloudDataStoreService.findByID(request
				.getParameter("id"));
		CloudDataStoreProperty cloudDataStorePropertyService = new CloudDataStoreProperty();
		Map<String, ArrayList<CloudDataStoreProperty>> cdhs = cloudDataStorePropertyService
				.findAllByCDS(cds.getId());
	%>
	<h2 style="float: left;"><%=cds.getName()%>
		(<a href="<%=cds.getWebsite()%>"><%=cds.getProvider()%></a>)
	</h2>
	<p style="float: right;">
		<a class="btn" href="add-cloud-data-store.jsp?id=<%=cds.getId()%>">Edit
			&raquo;</a>
	</p>
	<p style="clear: both;"><%=cds.getDescription()%></p>
	<%
		String lastCategory = "";
		String lastCriterion = "";
		for (ArrayList<CloudDataStoreProperty> properties : cdhs.values()) {
	%>

	<%
		if (!properties.get(0).getCdhsCriterionPossibleValue()
					.getCdhsCriterion().getCdhsCategory().getId()
					.equals(lastCategory)) {
	%><fieldset>
		<legend><%=properties.get(0).getCdhsCriterionPossibleValue()
							.getCdhsCriterion().getCdhsCategory().getName()%></legend>
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
					for (CloudDataStoreProperty property : properties) {
						if (possibleValue.getId().equals(
								property.getCdhsCriterionPossibleValue()
										.getId())) {
							checked = true;
							inputValue = property.getInputValue();
						}
					}%>
					<%if (checked) {%> checked="checked" <%}%> disabled="disabled"
					<%=possibleValue.getType() == Type.INPUT ? "style=\"margin-top: 8px;\""
							: ""%>>
					<%
						out.print(possibleValue.getName());
								if (possibleValue.getType() == CDHSCriterionPossibleValue.Type.INPUT) {
					%>: <input name="<%=possibleValue.getId()%>-value"
					value="<%=inputValue != null ? inputValue : ""%>"
					disabled="disabled"
					style="width: <%=inputValue != null ? inputValue.length() * 7 + 10
								: 100%>px;"
					type="text" /> <%
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
			if (!properties.get(0).getCdhsCriterionPossibleValue()
						.getCdhsCriterion().getCdhsCategory().getId()
						.equals(lastCategory)) {
		%>
	</fieldset>
	<%
		}
	%>
	<%
		lastCategory = properties.get(0)
					.getCdhsCriterionPossibleValue().getCdhsCriterion()
					.getCdhsCategory().getId();
			lastCriterion = properties.get(0)
					.getCdhsCriterionPossibleValue().getCdhsCriterion()
					.getId();
		}
	%>

</form>
<%@ include file="common/footer.jsp"%>
