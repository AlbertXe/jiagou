<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="BASE" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<th>客户名称</th>
			<th>联系人</th>
			<th>电话</th>
			<th>邮箱</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${customers}" var="customer">
		<tr>
			<td>${customer.name }</td>
			<td>${customer.contact }</td>
			<td>${customer.telephone }</td>
			<td>${customer.email }</td>
			<td>
				<a href="${BASE}/customer_edit?id=${customer.id}">编辑</a>
				<a href="${BASE}/customer_delete?id=${customer.id}">删除</a>
			</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>