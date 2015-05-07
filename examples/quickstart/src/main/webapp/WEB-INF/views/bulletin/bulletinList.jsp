<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>公告同步</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				<label>名称：</label> <input type="text" name="search_LIKE_title" class="input-medium" value="${param.search_LIKE_title}"> 
				<button type="submit" class="btn" id="search_btn">Search</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>公告ID</th><th>公告标题</th><th>公告时间</th></tr></thead>
		<tbody>
		<c:forEach items="${bulletins.content}" var="bulletin">
			<tr>
				<td><a href="#">${bulletin.projectId}</a></td>
				<td><a href="#">${bulletin.projectNameForTitle}</a></td>
				<td><a href="#">${bulletin.announcementDate}</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${bulletins}" paginationSize="20"/>

	<div><a class="btn" href="#">同步</a></div>
</body>
</html>
