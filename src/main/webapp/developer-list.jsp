<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Developer Management</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: tomato">
			<div>
				<a href="https://www.javaguides.net" class="navbar-brand"> Developer
					Management App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/developers/list"
					class="nav-link">Developers</a></li>
			</ul>
		</nav>
	</header>
	<br>

	<div class="row">
		<!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->

		<div class="container">
			<h3 class="text-center">List of Developers</h3>
			<hr>
			<div class="container text-left">
				<a href="developers?action=new" class="btn btn-success">Add
					New Developer</a>
			</div>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>ID</th>
						<th>First Name</th>
						<th>Second Name</th>
						<th>Age</th>
						<th>Sex</th>
						<th>Salary</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach var="entry" items="${listDevelopers}">

                    	<tr>

							<td><c:out value="${entry.key}" /></td>
							<td><c:out value="${entry.value.firstName}" /></td>
							<td><c:out value="${entry.value.secondName}" /></td>
							<td><c:out value="${entry.value.age}" /></td>
							<td><c:out value="${entry.value.sex}" /></td>
							<td><c:out value="${entry.value.salary}" /></td>


							<td><a href="developers?action=edit&id=<c:out value='${entry.key}' />">Edit</a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="developers?action=delete&id=<c:out value='${entry.key}' />">Delete</a></td>
						</tr>
					</c:forEach>

				</tbody>

			</table>
		</div>
	</div>
</body>
</html>