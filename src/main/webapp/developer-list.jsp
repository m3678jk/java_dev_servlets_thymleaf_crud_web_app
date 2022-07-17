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
				<a href="https://www.javaguides.net" class="navbar-brand"> Management App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="/"
					class="nav-link">Main Menu</a></li>
			</ul>
		</nav>
	</header>
	<br>

	<div class="row">
		<!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->

		<div class="container">
			<h3 class="text-center" >List of Developers</h3>
			<h3 class="text-center" ><c:out value="${text}" /></h3>
			<hr>

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
						<th>Skills</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach var="l" items="${list}">

                    	<tr>

							<td><c:out value="${l.id}" /></td>
							<td><c:out value="${l.firstName}" /></td>
							<td><c:out value="${l.secondName}" /></td>
							<td><c:out value="${l.age}" /></td>
							<td><c:out value="${l.sex}" /></td>
							<td><c:out value="${l.salary}" /></td>
							<td><c:out value="${l.skills}" /></td>

						</tr>
					</c:forEach>

				</tbody>

			</table>
		</div>
	</div>
</body>
</html>