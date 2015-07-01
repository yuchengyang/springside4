<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>招标项目同步</title>
	<script type="text/javascript">
	var remoteUrl = "http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";
	
	function synProject(){
		var ids = [];
		var checkradio = true;
		$.each($('input[name=checkID]:checked'),function( idx, obj ){
			var radiochecked = $(obj).parent().parent().find("input[type=radio]:checked");
			if($(radiochecked).val() == null ){
				//checkradio = false;
				//return false;
				ids.push( $(obj).val() );
			}else{
				ids.push( $(obj).val() + ':'+ $(radiochecked).val() );
			}
		});	
		
		/*
		if(!checkradio){
			alert("请确定公告文件都已经确定选中唯一！");
			return false;
		}
		*/
		
		if(ids.length == 0 ){
			alert("至少选择一条数据！");
			return false;
		}
		if(!confirm("确认操作这些数据？")){
			return false;
		}
		$.ajax({
			type:"POST",
			url:remoteUrl + "/project/synProject", 
			data:{"ids":ids},
			dataType:"json"})
			.done(function(json) {
				if(json.success){
					alert("操作成功!");
				}else{
					alert("操作失败！");
				}
				window.location.reload();
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
			<label>名称：</label>
			<input type="text" name="search_LIKE_projectName"class="input-medium" value="${param.search_LIKE_projectName}">
			<label>项目经理：</label>
			<input type="text" name="search_LIKE_creatorName" class="input-medium" value="${param.search_LIKE_creatorName}">
			<label>项目部门：</label>
			<input type="text" name="search_LIKE_organizationName" class="input-medium"  value="${param.search_LIKE_organizationName}">
			<br>
			<label>时间：</label>
			<input type="text" name="search_GTE_delegateDate" class="input-medium"  value="${param.search_GTE_delegateDate}">
			至
			<input type="text" name="search_LTE_delegateDate" class="input-medium"  value="${param.search_LTE_delegateDate}">
			例如（2001-05）
			
			<button type="submit" class="btn" id="search_btn">Search</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<th><input type="checkbox" id="checkAll" onchange="checkAllorNot(this);"></th>
		<th>项目名称</th>
		<th>项目经理</th>
		<th>项目部门</th>
		<th>立项日期</th>
		<th>同步阶段</th>
		<th>公告</th>
		</tr></thead>
		<tbody>
		
		
		<c:forEach items="${projects.content}" var="project">
			<tr>
				<td style="width:20px;"><input type="checkbox" name="checkID"  <c:if test="${project.synStatus eq '4' }">disabled="disabled"  readonly="readonly" </c:if>value="${project.id}"></td>
				<td style="width:380px;"><a href="#">${project.projectName}</a></td>
				<td style="width:50px;"><a href="#">${project.creatorName}</a></td>
				<td style="width:100px;"><a href="#">${project.organizationName}</a></td>
				<td style="width:80px;"><fmt:formatDate value="${project.delegateDate}" pattern="yyyy-MM-dd"/></td>
				<td>${project.synStatusCN }</td>
				<td>
					<c:forEach var="bulletinData" items="${project.bulletinDatas}">
						<input type="radio" value="${bulletinData.id}"  name="fileName[${project.projectId}]" <c:if test="${fn:length( project.bulletinDatas ) > 0 }">checked="checked"</c:if>><a href="#">${bulletinData.attachmentPathShort}</a>
					</c:forEach>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${projects}" paginationSize="20"/>

	<div><a class="btn" href="javascript:synProject();">同步</a></div>
</body>
</html>
