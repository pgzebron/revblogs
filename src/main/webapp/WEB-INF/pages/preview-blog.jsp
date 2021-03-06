 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Revature Blog</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="resources/css/main.css" rel="stylesheet">
<script src="resources/js/angular.min.js"></script>
<script src="resources/js/app.js"></script>
<script src="resources/js/controllers/TagSearchController.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- HEADERS NEEDED TO PREVENT BACK BUTTON ON LOGOUT. DO NOT REMOVE ME! -->
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<meta property="og:url" content="http://dev.pjw6193:7001/pages/preview-blog.jsp" />
<meta property="og:type"content="article" />
<meta property="og:title"content="Different" />
<meta property="og:description"content="this is only a testkhjkgkjhgkj" />
<meta property="og:image"content="http://weknowyourdreams.com/images/cheese/cheese-03.jpg" />
<meta property="og:image:width" content="450" />
<meta property="og:image:height" content="298" />
<meta property="fb:app_id" content="1070815552954243"/>
<!-- LinkedIn Authenticator Token -->
<script src="//platform.linkedin.com/in.js">
    api_key:   77nvk5bz7r4mwj;
</script>
<script src="https://use.fontawesome.com/ebec39e24e.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-social.css">
<link rel="shortcut icon" type="image/png" href="/content/resources/img/favicon.png"/>
</head>

<body ng-app="app" ng-controller="TagSearchController">

<div id="curseModal" class="modal fade">
 	<div class="modal-dialog">
   		<div class="modal-content">
     		<div class="modal-header">
       			<button type="button" class="close" data-dismiss="modal">&times;</button>
       			<h4 class="modal-title">Error</h4>
    		</div>
    		<form action="newReimbursement.do" method="post" class="form">
    		<div class="modal-body">
    			Please remove any highlighted swear words.
     		</div>
     		</form>
   		</div>
 	</div>
</div>

<jsp:include page="navbar.jsp"></jsp:include>
<div class="overall-container">
  <div class="container page-content content-padding">
    <div class="row">
      <div class="panel panel-default col-sm-8">
        <div class="post-content" id="postContent" style="padding-top: 60px;">
          <div class="post-heading">
            <div class="post-meta">
              <span class="post-date">
              	<c:out value="${blog.publishDate}"></c:out>
              </span>
              <span class="post-author">
              	<c:out value="${blog.author.firstName}"></c:out> 
              	<c:out value="${blog.author.lastName}"></c:out>
              </span>
            </div>
            <h1 class="post-title">
            	<c:out value="${blog.blogTitle}"></c:out>
            </h1>
            <h3 class="post-subtitle">
            	<c:out value="${blog.blogSubtitle}"></c:out>
            </h3>
          </div>
          <div class="post-body" id="previewBlogBody">
          	<c:out value="${blog.blogContent}" escapeXml="false"></c:out>
          </div>
          <div class="post-references-heading">
          </div>
          <div class="post-references-body">
            <c:forEach varStatus="vs" var="ref" items="${blog.references}">
              <c:if test="${not empty ref}">
              	<div class="post-reference-item">
              	  <c:out value="[${vs.index+1}] - ${ref}"></c:out>
              	</div>
              </c:if>
            </c:forEach>
          </div>
          <div class="post-tags">
          	<c:forEach var="t" items="${blog.tags}">
          		<a target="_blank" href="" ng-click="getPageWithTagsFromBlogPost(${t.tagId})"><c:out value="${t.description}"></c:out></a>
           	</c:forEach>
          </div>
          <form:form action="edit.do">
          	<button type="submit">Edit</button>
          </form:form>
          <form action="publish.do" method="post" onsubmit="return filter(this)">
         	<button type="submit">Publish</button>
          </form>
          
          	
         
        </div>
      </div>
      <div class="col-sm-4">
        <div class="panel panel-primary">
          <div class="panel-heading">
            <h3 class="panel-title">About the Author</h3>
          </div>
          <div class="panel-body">
            <div class="row author">
              <div class="col-xs-12 author-image">
              	<img src="${blog.author.profilePicture}" />
              </div>
              <div class="col-xs-12 author-name text-center">
              	<c:out value="${blog.author.firstName}"></c:out> 
              	<c:out value="${blog.author.lastName}"></c:out>
              </div>
            </div>
            <div class="row">
              <div class="col-xs-12 author-desc">
              	<c:out value="${blog.author.description}"></c:out>
              </div>
            </div>
          </div>
        </div>

        <div class="panel panel-primary">
          <div class="panel-heading">
            <h3 class="panel-title">Share this post</h3>
          </div>
          <div class="panel-body">
                   <div>Share:</div>
			<a class="btn btn-social-icon btn-linkedin" target="_blank"">
				<span class="fa fa-linkedin"></span>	
			</a>
			<a class="btn btn-social-icon btn-facebook" target="_blank">
				<span class="fa fa-facebook"></span>
			</a>
			<a class="btn btn-social-icon btn-twitter" target="_blank">
				<span class="fa fa-twitter"></span>				
			</a>
          </div>
        </div>

        <div class="panel panel-primary">
          <div class="panel-heading">
            <h3 class="panel-title">Learn more about Revature</h3>
          </div>
          <div class="panel-body">
            <ul class="list-unstyled">
              <li><a href="#">Revature</a></li>
              <li><a href="#">Life at Revature</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="footer footer2">
    <div class="container">
      <div class="row">
        <div class="col-sm-6">
          <img class="img-responsive" src="resources/img/rev-footer.png" />
        </div>
      </div>
    </div>
  </div>
 </div>
</body>
<script type="text/javascript" src="resources/js/ui.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/antiCursing.js"></script>
</html>