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
				<a href="https://www.javaguides.net" class="navbar-brand"> Developer Management</a>
			</div>

			<ul class="navbar-nav">
				<li><a href="</developers"
					class="nav-link">Developers</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${developer != null}">
					<form action="developers?action=update" method="post">
				</c:if>
				<c:if test="${developer == null}">
					<form action="developers?action=insert" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${developer != null}">
            			Edit Developer
            		</c:if>
						<c:if test="${developer == null}">
            			Add New Developer
            		</c:if>
					</h2>
				</caption>

				<c:if test="${developer != null}">
					<input type="hidden" name="id" value="<c:out value='${id}' />" />
				</c:if>

				<fieldset class="form-group">
					<label>Developer First Name</label> <input type="text"
						value="<c:out value='${developer.firstName}' />" class="form-control"

						name="firstName" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Developer Second Name</label> <input type="text"
						value="<c:out value='${developer.secondName}' />" class="form-control"
						name="secondName" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Age</label> <input type="text"
						value="<c:out value='${developer.age}' />" class="form-control"
						name="age" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Sex</label> <input type="text"
						value="<c:out value='${developer.sex}' />" class="form-control"

						name="sex" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Salary</label> <input type="text"
						value="<c:out value='${developer.salary}' />" class="form-control"
						name="salary" required="required">
				</fieldset>


				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>