<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>采购人同步</title>
	<script type="text/javascript">
	var remoteUrl = "http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";
	
	function synBuyer(){
		var ids = [];
		$.each($('input[name=checkID]:checked'),function( idx, obj ){
			ids.push( $(obj).val() );
		});	
		if(ids.length == 0 ){
			alert("至少选择一条数据！");
			return false;
		}
		if(!confirm("确认操作这些数据？")){
			return false;
		}
		$.ajax({
			type:"POST",
			url:remoteUrl + "/buyer/synBuyer", 
			data:{"ids":ids},
			dataType:"json"})
			.done(function(json) {
				if(json.success){
					alert("操作成功!");
				}else{
					alert("操作失败！");
				}
			});
	}
	
	function checkAllorNot(e){
		$("input[type=checkbox][name=checkID]").prop("checked", $("#checkAll").prop("checked") ) ;
	}
	</script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				<label>名称：</label> <input type="text" name="search_LIKE_customerName" class="input-medium" value="${param.search_LIKE_customerName}"> 
				<button type="submit" class="btn" id="search_btn">Search</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><input type="checkbox" id="checkAll" onchange="checkAllorNot(this);"></th><th>采购人ID</th><th>采购人名称</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${buyers.content}" var="buyer">
			<tr>
				<td><input type="checkbox" name="checkID" value="${buyer.id}"></td>
				<td><a href="#">${buyer.customerId}</a></td>
				<td><a href="#">${buyer.customerName}</a></td>
				<td><a href="#">同步</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${buyers}" paginationSize="20"/>

	<div><a class="btn" href="javascript:synBuyer();">同步</a></div>
</body>
</html>
