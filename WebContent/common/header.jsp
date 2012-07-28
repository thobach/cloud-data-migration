<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ page import="com.clouddatamigration.classification.model.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Cloud Data Migration</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Cloud Data Store Providers and Assistence for Migration">
<meta name="author" content="Thomas Bachmann">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}

.radio.inline+.radio.inline,.checkbox.inline+.checkbox.inline {
	margin-left: 25px;
}
</style>
<!-- HTML5 for IE6-8 -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<link rel="shortcut icon" href="/favicon.ico">
<link rel="apple-touch-icon" href="/ico/apple-touch-icon-iphone.png" />
<link rel="apple-touch-icon" sizes="72x72"
	href="/ico/apple-touch-icon-ipad.png" />
<link rel="apple-touch-icon" sizes="114x114"
	href="/ico/apple-touch-icon-iphone4.png" />
<link rel="apple-touch-icon" sizes="144x144"
	href="/ico/apple-touch-icon-ipad3.png" />
</head>
<body>
	<%
		String pageName = "index.jsp";
		if (request.getAttribute("pageName") != null) {
			pageName = (String) request.getAttribute("pageName");
		}
		User userHeader = new User();
		String sessionTokenHeader = userHeader.findSessionToken(request
				.getCookies());
		userHeader = userHeader.findBySessionToken(sessionTokenHeader);
	%>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="/index.jsp"
					style="font-weight: bold; color: white;"><img
					src="/img/logo.png"
					alt="Logo Cloud Data Migration Assistant"
					style="height: 20px; margin-top: -6px; margin-left: 2px;" /> Cloud
					Data Migration Assistant</a>
				<ul class="nav">
					<li class="divider-vertical"></li>
					<li <%if (pageName.equals("index.jsp")) {%> class="active" <%}%>><a
						href="/index.jsp"><i class="icon-home icon-white"></i> Home</a></li>
					<li <%if (pageName.equals("cloud-data-stores.jsp")) {%>
						class="active" <%}%>><a href="/store/cloud-data-stores.jsp"><i
							class="icon-list-alt icon-white"></i> Cloud Data Stores</a></li>
					<%
						if (sessionTokenHeader != null && userHeader != null) {
					%>
					<li <%if (pageName.equals("projects.jsp")) {%> class="active" <%}%>><a
						href="/classification/projects.jsp"><i
							class="icon-folder-open icon-white"></i> Projects</a></li>
					<%
						}
					%>
					<li <%if (pageName.equals("about.jsp")) {%> class="active" <%}%>><a
						href="/about.jsp"><i class="icon-envelope icon-white"></i>
							About</a></li>
					<li class="divider-vertical"></li>
				</ul>
				<div class="nav-collapse">
					<%
						if (sessionTokenHeader == null || userHeader == null) {
					%>
					<form class="navbar-form pull-left" action="/classification/signin"
						method="post">
						<input type="text" name="username" class="input-small"
							placeholder="Username"> <input type="password"
							name="password" class="input-small" placeholder="Password">
						<button type="submit" class="btn" style="margin-bottom: 0">Sign
							in</button>
					</form>
					<%
						} else {
					%><p class="navbar-text pull-left"
						style="margin-right: 1em; width: 110px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">
						<a href="/classification/projects.jsp"><i
							class="icon-user icon-white"></i> <%=userHeader.getEmail()%></a>
					</p>
					<form class="navbar-form pull-left"
						action="/classification/signout" method="post">
						<button type="submit" class="btn btn-small"
							style="margin-bottom: 0">Sign out</button>
					</form>
					<%
						}
					%>
					<ul class="nav">
						<li class="divider-vertical"></li>
					</ul>
					<form class="navbar-search pull-right"
						action="https://google.com/search" method="get">
						<div class="input-append">
							<input type="search" name="q" class="search-query"
								placeholder="Search"
								style="padding-top: 5px; padding-bottom: 5px; margin-top: -1px; width: 110px">
							<button class="btn" type="submit" style="margin: 0;">
								<i class="icon-search"></i>
							</button>
						</div>
						<input type="hidden" name="as_sitesearch"
							value="cloud-data-migration.com">
					</form>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<div class="container">