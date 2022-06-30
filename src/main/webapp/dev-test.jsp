<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.ArrayList" %>

<html>
<body>

	<br>


					<!--   for (Todo todo: todos) {  -->
					<c:forEach var="developer" items="${listDevelopers}">

                    	<tr>

							<td><c:out value="${developer.id}" /></td>
							<td><c:out value="${developer.firstName}" /></td>
							<td><c:out value="${developer.secondName}" /></td>
							<td><c:out value="${developer.age}" /></td>
							<td><c:out value="${developer.sex}" /></td>
							<td><c:out value="${developer.salary}" /></td>

							<td><a href="edit?id=<c:out value='${developer.id}' />">Edit</a>
								&nbsp;&nbsp;&nbsp;&nbsp; <a
								href="delete?id=<c:out value='${developer.id}' />">Delete</a></td>
						</tr>
					</c:forEach>

</body>
</html>