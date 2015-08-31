<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>项目明细</title>
</head>
<body>
	<form id="inputForm" class="form-horizontal">
		<fieldset>
			<legend><small>项目简要</small></legend>
			<div class="control-group">
				<label class="control-label">项目编码:</label>
				<div class="controls">${projectData.projectCode}</div>
			</div>
			<div class="control-group">
				<label class="control-label">项目名称:</label>
				<div class="controls">${projectData.projectName}</div>
			</div>
			<div class="control-group">
				<label class="control-label">项目创建时间:</label>
				<div class="controls">${projectData.delegateDate}</div>
			</div>
			<div class="control-group">
				<label class="control-label">项目当前节点:</label>
				<div class="controls">${activeProjectStepTypeView.projectStepTypeName}</div>
			</div>
			<div class="control-group">
				<label class="control-label">项目上个节点:</label>
				<div class="controls">${lastedProjectStepTypeView.projectStepTypeName}</div>
			</div>
			
			
			
		</fieldset>
		
		<c:if test="${ not empty projectStepTypeViews }">
		<fieldset>
			<legend><small>项目节点</small></legend>
			<div>
						<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead><tr>
						<th>节点名称</th>
						</tr></thead>
						<tbody>
						<c:forEach items="${projectStepTypeViews}" var="projectStepTypeView">
							<tr>
								<td>${projectStepTypeView.projectStepTypeName}</td>
							</tr>
						</c:forEach>
						</tbody>
						</table>
			</div>
		</fieldset>
		</c:if>
		
		<c:if test="${not empty projectData.projectPkgDatas  }">
		<fieldset>
			<legend><small>标段信息</small></legend>
			<div class="control-group">
				<label class="control-label">项目标段:</label>
				<div class="controls">
					<c:forEach items="${projectData.projectPkgDatas}" var="projectPkg" varStatus="status">
						<input type="radio" onclick="loadTender('${projectData.projectId}','${projectPkg.projectId}')"  name="${projectData.projectId }" ${status.index==0?'checked="checked"':''} value="${projectPkg.id}">${projectPkg.projectName}&nbsp;
					</c:forEach>
				</div>
			</div>
		</fieldset>
		</c:if>
		
		<fieldset>
			<legend><small>投标人信息</small></legend>
			<div id="tenderDiv">
				<jsp:include page="/oa/div/getProjectTender?sectionId=${not empty projectData.projectPkgDatas?projectData.projectPkgDatas[0].projectId:''}"/>
			</div>
		</fieldset>
		
		<fieldset>
			<legend><small>文件信息</small></legend>
			<div>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead><tr>
					<th>附件名称</th>
					<th>上传时间</th>
					</tr></thead>
					<tbody>
					<c:forEach items="${projectDocViews}" var="projectDocView">
						<tr>
							<td><a href="${ctx}/oa/getProjectFile?filePath=${projectDocView.attachmentPath}">${projectDocView.attachmentName}</a></td>
							<td>${projectDocView.uploadDate}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</fieldset>
		
		
		
	</form>
	<script type="text/javascript">
		function loadTender(projectId  , sectionId){
			$("#tenderDiv").empty().load('${ctx}/oa/div/getProjectTender?projectId='+projectId +'&sectionId='+sectionId);
		}
	</script>
</body>
</html>
