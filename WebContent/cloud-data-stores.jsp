<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ page
	import="com.clouddatamigration.classification.model.CloudDataStore"%>
<%@ page
	import="com.clouddatamigration.classification.model.CloudDataStoreProperty"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.ArrayList"%>
<%
	request.setAttribute("pageName", "cloud-data-stores.jsp");
%>
<%@ include file="common/header.jsp"%>
<!-- Example row of columns -->
<div class="row">
	<div class="span12">
		<h2>Cloud Data Stores</h2>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Name</th>
					<th>Provider</th>
					<th>Type</th>
					<th>Deployment Model</th>
					<th>Service Model</th>
					<th>Scalability</th>
					<th>Consistency</th>
					<th>Security</th>
					<th>Description</th>
				</tr>
			</thead>
			<tbody>
				<%
					CloudDataStore cloudDataStoreService = new CloudDataStore();
					for (CloudDataStore cloudDataStore : cloudDataStoreService
							.findAll()) {
						CloudDataStoreProperty cloudDataStorePropertyService = new CloudDataStoreProperty();
						Map<String, ArrayList<CloudDataStoreProperty>> cdhs = cloudDataStorePropertyService
								.findAllByCDS(cloudDataStore.getId());
				%>
				<tr>
					<td><a
						href="cloud-data-store.jsp?id=<%=cloudDataStore.getId()%>"><%=cloudDataStore.getName()%></a></td>
					<td><a href="<%=cloudDataStore.getWebsite()%>"><%=cloudDataStore.getProvider()%></a></td>
					<td>
						<%
							String type = cdhs.get("STORAGE_TYPE") != null ? cdhs
										.get("STORAGE_TYPE").get(0)
										.getCdhsCriterionPossibleValue().getName() : "-";
						%> <%=type%></td>
					<td>
						<%
							String deploymentModel = cdhs
										.get("CLOUD_COMPUTING_DEPLOYMENT_MODEL") != null ? cdhs
										.get("CLOUD_COMPUTING_DEPLOYMENT_MODEL").get(0)
										.getCdhsCriterionPossibleValue().getName() : "-";
						%> <%=deploymentModel%></td>
					<td>
						<%
							String serviceModel = cdhs.get("CLOUD_COMPUTING_SERVICE_MODEL") != null ? cdhs
										.get("CLOUD_COMPUTING_SERVICE_MODEL").get(0)
										.getCdhsCriterionPossibleValue().getName()
										: "-";
						%> <%=serviceModel%></td>
					<td>
						<%
							String scalability = cdhs.get("AVAILABILITY_REPLICATION_TYPE") != null ? cdhs
										.get("AVAILABILITY_REPLICATION_TYPE").get(0)
										.getCdhsCriterionPossibleValue().getName()
										: "-";
						%> <%=scalability%></td>
					<td>
						<%
							String consistency = cdhs.get("CAP_CONSISTENCY_MODEL") != null ? cdhs
										.get("CAP_CONSISTENCY_MODEL").get(0)
										.getCdhsCriterionPossibleValue().getName()
										: "";
						%> <%=consistency%></td>
					<td>
						<%
							String security = cdhs.get("SECURITY_CONFIDENTIALITY") != null ? cdhs
										.get("SECURITY_CONFIDENTIALITY").get(0)
										.getCdhsCriterionPossibleValue().getName()
										: "-";
						%> <%=security%></td>
					<td><%=cloudDataStore.getDescription() != null ? cloudDataStore
						.getDescription().length() > 210 ? cloudDataStore
						.getDescription().substring(0, 200) + "..." : "" : ""%></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<p>
			<a class="btn btn-primary" href="add-cloud-data-store.jsp">Add
				New Cloud Data Store &raquo;</a>
		</p>
	</div>
</div>
<%@ include file="common/footer.jsp"%>
