<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<hr>
<footer>
	<p>
		&copy; Thomas Bachmann 2012 - <a href="/about.jsp"><i
			class="icon-envelope"></i> About</a>
	</p>
</footer>
</div>
<div class="modal hide" id="loadingModal">
	<div class="modal-header">
		<h3>Please wait</h3>
	</div>
	<div class="modal-body">
		<p>Your changes are saved.</p>
		<div class="progress progress-striped
     active">
			<div id="loadingBar" class="bar" style="width: 10%;"></div>
		</div>
	</div>
</div>
<!-- /container -->
<script src="/js/jquery-1.7.2.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script type="text/javascript">
	var pkBaseURL = (("https:" == document.location.protocol) ? "https://sslsites.de/stats.thobach.de/"
			: "http://stats.thobach.de/");
	document.write(unescape("%3Cscript src='" + pkBaseURL
			+ "piwik.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
	try {
		var piwikTracker = Piwik.getTracker(pkBaseURL + "piwik.php", 6);
		piwikTracker.trackPageView();
		piwikTracker.enableLinkTracking();
	} catch (err) {
	}
</script>
<noscript>
	<p>
		<img src="http://stats.thobach.de/piwik.php?idsite=6"
			style="border: 0" alt="" />
	</p>
</noscript>
</body>
</html>
