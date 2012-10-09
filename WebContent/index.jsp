<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ page import="com.clouddatamigration.store.model.CloudDataStore"%>
<%
	request.setAttribute("pageName", "index.jsp");
%>
<%@ include file="common/header.jsp"%>
<%
	if (request.getParameter("info") != null
			&& request.getParameter("info").equals("signedUp")) {
%><div class="alert alert-success">
	Thanks for your interest! <i class="icon-heart"></i> You can now log in using your username and password.
</div>
<%
	}
%>
<!-- Main hero unit for a primary marketing message or call to action -->
<div class="hero-unit">
	<h1 style="margin-bottom: .3em;">Migrate your Data to the Cloud!</h1>
	<p>The Cloud Data Migration Assistant helps you to identify the
		steps that are required to migrate data from your local or Cloud data
		store to a new Cloud data store.</p>
	<img src="/img/overview.png" alt="step-by-step explenation"
		class="thumbnail"
		style="padding: 1em; background-color: white; margin-bottom: 1.5em;" />
	<div class="row">
		<div class="span5">
			<p>This is how it works:</p>
			<ul>
				<li>Step 1. Identify Cloud migration scenario</li>
				<li>Step 2. Describe desired Cloud data hosting solution</li>
				<li>Step 3. Select Cloud data store</li>
				<li>Step 4. Identify patterns to solve potential conflicts</li>
				<li>Step 5. Adapt data access layer and upper application
					layers if needed</li>
				<li>Step 6. Migrate data to the selected Cloud data store</li>
			</ul>
		</div>
		<div class="span5">
			<p>
				... or simply browse and compare <a
					href="/store/cloud-data-stores.jsp">cloud data stores</a>:
			</p>
			<ul>
				<li><a href="/store/cloud-data-stores.jsp?filter=rdbms">Relation
						Database Management Systems (RDBMS)</a></li>
				<li><a href="/store/cloud-data-stores.jsp?filter=nosql">NoSQL</a></li>
				<li><a href="/store/cloud-data-stores.jsp?filter=blob">Blob
						Stores</a></li>
				<li><a href="/store/cloud-data-stores.jsp?filter=cdn">Content
						Delivery Network (CDN)</a></li>
			</ul>
		</div>
	</div>
</div>

<!-- Example row of columns -->
<div class="row">
	<div class="span6">
		<form class="form-horizontal well" action="/classification/signup"
			method="post" id="signup-form">
			<fieldset>
				<legend>
					Sign Up <span class="label label-success">Free</span>
				</legend>
				<div class="control-group">
					<label class="control-label" for="username">Username</label>
					<div class="controls">
						<input type="text" class="input-xlarge" id="username"
							name="username">
						<p class="help-block">Your selected username to login.</p>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="password">Password</label>
					<div class="controls">
						<input type="password" class="input-xlarge" id="password"
							name="password">
						<p class="help-block">Your secure password to login.</p>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="email">E-mail Address</label>
					<div class="controls">
						<input type="email" class="input-xlarge" id="email" name="email">
						<p class="help-block">Your valid E-mail address in case we need to notify you about this service.</p>
					</div>
				</div>
				<div class="form-actions">
					<button class="btn">Cancel</button>
					<button type="submit" class="btn btn-primary">Sign Up
						&raquo;</button>
				</div>
				<!-- <legend>
					Sign Up For Private Beta <span class="label label-success">Free</span>
				</legend>
				<div class="control-group">
					<label class="control-label" for="email">E-mail Address</label>
					<div class="controls">
						<input type="email" class="input-xlarge" id="email" name="email">
						<p class="help-block">You will receive an invitation to this
							E-mail address soon.</p>
					</div>
				</div>
				<div class="form-actions">
					<button class="btn">Cancel</button>
					<button type="submit" class="btn btn-primary">Sign Up
						&raquo;</button>
				</div>
				 -->
			</fieldset>
		</form>
	</div>
	<div class="span6">
		<div class="well">
			<h2>Supported Cloud Data Stores</h2>
			<ul>
				<%
					CloudDataStore cdsService = new CloudDataStore();
					for (CloudDataStore cloudDataStore : cdsService.findAll()) {
				%>
				<li><a
					href="/store/cloud-data-store.jsp?id=<%=cloudDataStore.getId()%>"><%=cloudDataStore.getName()%></a></li>
				<%
					}
				%>
			</ul>
			<p>
				<a class="btn" href="/store/cloud-data-stores.jsp">View all
					&raquo;</a>
			</p>
			<p>
				<a class="btn btn-primary" href="/store/add-cloud-data-store.jsp">Add
					New Cloud Data Store &raquo;</a>
			</p>
		</div>
	</div>
</div>
<%@ include file="common/footer.jsp"%>
