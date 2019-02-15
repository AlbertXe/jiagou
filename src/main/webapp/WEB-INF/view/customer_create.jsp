<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="src/main/webapp/asset/lib/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#customer_form").ajaxForm({
			type:'post',
			url:'customer_create',
			success: function(data){
				if(data){
					location.href="customer";
				}
			}
		});
		
	});

</script>
<title>创建客户</title>
</head>
<body>
	<form id="customer_form" enctype="multipart/form-data">
		<table>
			<tr>
				<td>客户名称</td>
				<td><input type="text" name="name" value="${customer.name }"></td>
			</tr>
			<tr>
				<td>联系人</td>
				<td><input type="text" name="contact" value="${customer.contact }"></td>
			</tr>
			<tr>
				<td>电话号码</td>
				<td><input type="text" name="telephone" value="${customer.telephone }"></td>
			</tr>
			<tr>
				<td>邮箱地址</td>
				<td><input type="text" name="email" value="${customer.email }"></td>
			</tr>
			<tr>
				<td>照片</td>
				<td><input type="file" name="photo" value="${customer.photo }"></td>
			</tr>
		</table>
		<button type="submit">save</button>
	</form>
</body>
</html>