<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:og="http://ogp.me/ns#" xmlns:fb="http://www.facebook.com/2008/fbml">
<head>
  <script src="//cdn.tinymce.com/4/tinymce.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta property="og:url" content="http://dev.pjw6193:7001/pages/create-blog.jsp" />
<meta property="og:type"content="article" />
<meta property="og:title"content="Different" />
<meta property="og:description"content="this is only a testkhjkgkjhgkj" />
<meta property="og:image"content="http://weknowyourdreams.com/images/cheese/cheese-03.jpg" />
<meta property="og:image:width" content="450" />
<meta property="og:image:height" content="298" />
<meta property="fb:app_id" content="1070815552954243"/>
<!-- LinkedIn Authenticator Token -->
<script src="//platform.linkedin.com/in.js">
    api_key:   77nvk5bz7r4mwj
</script>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="bootstrap-social.css">
<script src="https://use.fontawesome.com/ebec39e24e.js"></script>

<!-- Javascript SDK for FB Comments -->
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.7&appId=139998182166";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>

<title>Blog Post</title>
</head>
<body>
<h2>Create new blog post</h2>
<form:form action="add-blog.do" method="post" commandName="blog">
	Title <br>
	<form:input path="blogTitle"/>
	Subtitle <br>
	<form:input path="blogSubtitle"/>
	<button type="button">Attach Image</button>
	<button type="button">Attach File</button>
	<br>
	<form:textarea path="blogContent" rows="30" cols="100" id="tinyMCE"></form:textarea>
	<br>
	<!-- <button type="button">Add Reference</button>
	<br>
	<textarea rows="5" cols="100"></textarea> -->
	<div>Share:</div>
	<a class="btn btn-social-icon btn-linkedin" target="_blank" href="https://www.linkedin.com/shareArticle?mini=true&url=http://dev.pjw6193.tech/pages/create-blog.jsp">
		<span class="fa fa-linkedin"></span>	
	</a>
	<a class="btn btn-social-icon btn-facebook" target="_blank" id="shareBtn">
		<span class="fa fa-facebook"></span>
	</a>
	<br>
	<input type="submit" value="Preview" />
</form:form>

<!-- Facebook Comments Code, the data-href must point to the website the page will be on -->
	<div class="fb-comments" data-href="http://dev.pjw6193:7001/pages/create-blog.jsp" data-numposts="5"></div>
	
</body>
<script src="${pageContext.servletContext.contextPath }/resources/js/facebookConnection.js"></script>
<script src="${pageContext.servletContext.contextPath }/resources/js/tinyMCE.js"></script>
</html>