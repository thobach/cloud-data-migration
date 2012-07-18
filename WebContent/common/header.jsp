<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Cloud Data Migration</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Cloud Data Store Providers and Assistence for Migration">
<meta name="author" content="Thomas Bachmann">
<link href="css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
</style>
<!-- HTML5 for IE6-8 -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<link rel="shortcut icon" href="ico/favicon.ico">
<link rel="apple-touch-icon" href="ico/apple-touch-icon-iphone.png" />
<link rel="apple-touch-icon" sizes="72x72"
	href="ico/apple-touch-icon-ipad.png" />
<link rel="apple-touch-icon" sizes="114x114"
	href="ico/apple-touch-icon-iphone4.png" />
<link rel="apple-touch-icon" sizes="144x144"
	href="ico/apple-touch-icon-ipad3.png" />
</head>
<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="index.jsp">Cloud Data Migration
					Assistant</a>

				<ul class="nav">
					<li class="active"><a href="index.jsp">Home</a></li>
					<li><a href="cloud-data-stores.jsp">Cloud Data Stores</a></li>
					<li><a href="about.jsp">About</a></li>
					<li><a href="mailto:info@thobach.de">Contact</a></li>
					<li class="divider-vertical"></li>
				</ul>
				<div class="nav-collapse">
					<form class="navbar-form pull-left" action="signin" method="post">
						<input type="text" name="username" class="input-small"
							placeholder="Username"> <input type="password"
							name="password" class="input-small" placeholder="Password">
						<button type="submit" class="btn" style="margin-bottom: 0">Sign
							in</button>
					</form>
					<ul class="nav">
						<li class="divider-vertical"></li>
					</ul>
					<form class="navbar-search pull-right" action="search"
						method="post">
						<input type="text" class="search-query" placeholder="Search">
					</form>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<div class="container">