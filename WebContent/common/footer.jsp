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
<script src="/js/jquery.validate.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#signup-form').validate({
		rules: {
			username: {
				minlength: 4,
				required: true
			},
			email: {
				required: true,
				email: true
			},
			password: {
				minlength: 4,
				required: true
			}
		},
		highlight: function(label) {
			$(label).closest('.control-group').addClass('error');
		},
		success: function(label) {
			label.text('OK!').addClass('valid')
				.closest('.control-group').addClass('success');
		}
	});
});
</script>
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
<script type="text/javascript" charset="utf-8">
  var is_ssl = ("https:" == document.location.protocol);
  var asset_host = is_ssl ? "https://d3rdqalhjaisuu.cloudfront.net/" : "http://d3rdqalhjaisuu.cloudfront.net/";
  document.write(unescape("%3Cscript src='" + asset_host + "javascripts/feedback-v2.js' type='text/javascript'%3E%3C/script%3E"));
</script>

<script type="text/javascript" charset="utf-8">
  var feedback_widget_options = {};
  feedback_widget_options.display = "overlay";  
  feedback_widget_options.company = "cloud-data-migration";
  feedback_widget_options.placement = "left";
  feedback_widget_options.color = "#222";
  feedback_widget_options.style = "idea";
  var feedback_widget = new GSFN.feedback_widget(feedback_widget_options);
</script>

</body>
</html>
