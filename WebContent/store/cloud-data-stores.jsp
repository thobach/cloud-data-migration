<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ page import="com.clouddatamigration.store.model.CloudDataStore"%>
<%@ page
	import="com.clouddatamigration.store.model.CloudDataStoreProperty"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.ArrayList"%>
<%
	request.setAttribute("pageName", "cloud-data-stores.jsp");
	String filter = request.getParameter("filter");
	String filterTitle;
	if (filter == null) {
		filterTitle = "List of Cloud Data Stores (RDBMS, NoSQL, BLOBL, CDN), DBaaS";
	} else if (filter.equals("rdbms")) {
		filterTitle = "Relational Cloud Database List (RDBMS), DBaaS";
	} else if (filter.equals("nosql")) {
		filterTitle = "NoSQL Cloud Database List (Key-Value Stores), DBaaS";
	} else if (filter.equals("blob")) {
		filterTitle = "Blob Cloud Data Store Lost (File/Binary Data Stores), DBaaS";
	} else if (filter.equals("cdn")) {
		filterTitle = "Cloud Content Delivery Network List (CDN), DBaaS";
	} else {
		filterTitle = "List of Cloud Data Stores";
	}
	request.setAttribute("title", filterTitle);
%>
<%@ include file="../common/header.jsp"%>
<!-- Example row of columns -->
<div class="row">
	<div class="span12">
		<h2 style="float: left">Cloud Data Stores</h2>
		<div style="float: right; margin-top: 12px;">
			Filter: <a href="?filter=rdbms">Relational DB (RDBMS)</a>, <a
				href="?filter=nosql">NoSQL</a>, <a href="?filter=blob">Files
				(BLOB)</a>, <a href="?filter=cdn">Content Delivery Network (CDN)</a>
		</div>
		<table class="table table-striped table-bordered table-condensed"
			style="clear: both;">
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
					int count = 0;
					for (CloudDataStore cloudDataStore : cloudDataStoreService
							.findAll()) {
						CloudDataStoreProperty cloudDataStorePropertyService = new CloudDataStoreProperty();
						Map<String, ArrayList<CloudDataStoreProperty>> cdhs = cloudDataStorePropertyService
								.findAllByCDS(cloudDataStore.getId());

						if (request.getParameter("filter") != null
								&& !request.getParameter("filter").isEmpty()) {
							if (filter.equals("rdbms")
									&& cdhs.get("STORAGE_TYPE") != null) {
								boolean isRdbms = false;
								for (CloudDataStoreProperty cloudDataStoreProperty : cdhs
										.get("STORAGE_TYPE")) {
									if (cloudDataStoreProperty
											.getCdhsCriterionPossibleValue().getKey()
											.equals("RDBMS")) {
										isRdbms = true;
									}
								}
								if (!isRdbms) {
									continue;
								}
							} else if (filter.equals("nosql")
									&& cdhs.get("STORAGE_TYPE") != null) {
								boolean isNoSQL = false;
								for (CloudDataStoreProperty cloudDataStoreProperty : cdhs
										.get("STORAGE_TYPE")) {
									if (cloudDataStoreProperty
											.getCdhsCriterionPossibleValue().getKey()
											.equals("NOSQL")) {
										isNoSQL = true;
									}
								}
								if (!isNoSQL) {
									continue;
								}
							} else if (filter.equals("blob")
									&& cdhs.get("STORAGE_TYPE") != null) {
								boolean isBlob = false;
								for (CloudDataStoreProperty cloudDataStoreProperty : cdhs
										.get("STORAGE_TYPE")) {
									if (cloudDataStoreProperty
											.getCdhsCriterionPossibleValue().getKey()
											.equals("BLOB_STORE")) {
										isBlob = true;
									}
								}
								if (!isBlob) {
									continue;
								}
							} else if (filter.equals("cdn")
									&& cdhs.get("STORAGE_TYPE") != null) {
								boolean isCdn = false;
								for (CloudDataStoreProperty cloudDataStoreProperty : cdhs
										.get("STORAGE_TYPE")) {
									if (cloudDataStoreProperty
											.getCdhsCriterionPossibleValue().getKey()
											.equals("CDN")) {
										isCdn = true;
									}
								}
								if (!isCdn) {
									continue;
								}
							}
						}
						count++;
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
					if (count == 0) {
				%>
				<tr>
					<td colspan="9"><div class="alert alert-info"
							style="margin: 1em;">
							Oh man! We don't have any cloud data store of that type. Be the
							first to <a href="/store/add-cloud-data-store.jsp">create one</a>!
							<i class="icon-flag"></i>
						</div></td>
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
<%@ include file="../common/footer.jsp"%>
