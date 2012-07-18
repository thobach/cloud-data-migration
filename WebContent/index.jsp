<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.clouddatamigration.classification.model.CloudDataStore"%>
<%@ include file="common/header.jsp"%>
<!-- Main hero unit for a primary marketing message or call to action -->
<div class="hero-unit">
	<h1 style="margin-bottom: .3em;">Migrate your Data to the Cloud!</h1>
	<p>The Cloud Data Migration Assistant helps you to identify the
		steps that are required to migrate data from you local or Cloud data
		store to a new Cloud data store.</p>
	<img src="img/overview.png" alt="step-by-step explenation"
		class="thumbnail"
		style="padding: 1em; background-color: white; margin-bottom: 1.5em;" />
	<div class="row">
		<div class="span5">
			<p>This is how it works:</p>
			<ul>
				<li>Step 1. Identify Cloud migration scenario</li>
				<li>Step 2. Select Cloud data store</li>
				<li>Step 3. Identify customizations required before the Cloud
					migration</li>
				<li>Step 4. Migrate data to the selected Cloud data store</li>
			</ul>
		</div>
		<div class="span5">
			<p>
				... or simply browse and compare <a href="cloud-data-store.jsp">cloud
					data stores</a>:
			</p>
			<ul>
				<li><a href="cloud-data-store.jsp?filter=rdbms">Relation
						Database Management Systems (RDBMS)</a></li>
				<li><a href="cloud-data-store.jsp?filter=nosql">NoSQL</a></li>
				<li><a href="cloud-data-store.jsp?filter=blob">Blob Stores</a></li>
				<li><a href="cloud-data-store.jsp?filter=cdn">Content
						Delivery Network (CDN)</a></li>
			</ul>
		</div>
	</div>
</div>

<!-- Example row of columns -->
<div class="row">
	<div class="span6">
		<h2>
			Sign Up <span class="label label-success">Free</span>
		</h2>
		<form class="form-horizontal" action="signup" method="post">
			<fieldset>
				<legend></legend>
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
						<p class="help-block">Your E-mail address to verify your
							account.</p>
					</div>
				</div>
				<div class="form-actions">
					<button class="btn">Cancel</button>
					<button type="submit" class="btn btn-primary">Sign Up
						&raquo;</button>
				</div>
			</fieldset>
		</form>
	</div>
	<div class="span6">
		<h2>Supported Cloud Data Stores</h2>
		<ul>
			<%
				CloudDataStore cdsService = new CloudDataStore();
				for (CloudDataStore cloudDataStore : cdsService.findAll()) {
			%>
			<li><a
				href="cloud-data-store.jsp?id=<%=cloudDataStore.getId()%>"><%=cloudDataStore.getName()%></a></li>
			<%
				}
			%>
		</ul>
		<p>
			<a class="btn" href="cloud-data-stores.jsp">View all &raquo;</a>
		</p>
		<p>
			<a class="btn btn-primary" href="add-cloud-data-store.jsp">Add
				New Cloud Data Store &raquo;</a>
		</p>
	</div>
</div>
<%@ include file="common/footer.jsp"%>
