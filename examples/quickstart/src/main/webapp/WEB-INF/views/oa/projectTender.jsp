<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<th>投标人名称</th>
		<th>单位类型</th>
		<th>投标类型</th>
		<th>是否参与开标</th>
		<th>投标金额(万元)</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${appCompanyViews}" var="appCompanyView">
			<tr>
				<td>${appCompanyView.companyName}</td>
				<td>
					<c:if test="${appCompanyView.tenderType==1}">独立投标人</c:if>
					<c:if test="${appCompanyView.tenderType==2}">联合体</c:if>
				</td>
				<td>
					<c:if test="${appCompanyView.category==1}">直接投标</c:if>
					<c:if test="${appCompanyView.category==2}">资格预审</c:if>
				</td>
				<td>${appCompanyView.attendBidOpen?'是':'否'}</td>
				<td>${appCompanyView.tenderQuote}</td>
			</tr>
		</c:forEach>
		</tbody>
</table>