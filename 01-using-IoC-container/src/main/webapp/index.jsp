<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<title>Insert title here</title>
</head>
<body>
	<div class="container mt-4">
		<h1>IoC Container</h1>
		
		<h3>Courses</h3>
		
		<div>
			<a  class="btn btn-primary" href="course-edit">Add New Course</a>
		</div>
		<div class="mt-4">
		
		<c:choose>
			<c:when test="${empty course}">
				<div class="alert alert-warning">
					There is no course.Please create new course.
				</div>
			</c:when>
			
			<c:otherwise>
				<table class="table table-striped">
					<thead>
						<tr>
							<td>ID</td>
							<td>Name</td>
							<td>Duration</td>
							<td>Fees</td>
							<td>Description</td>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="c" items="${course}">
							<tr>
								<td>${c.id}</td>
								<td>${c.name}</td>
								<td>${c.duration} months</td>
								<td>${c.fees}</td>
								<td>${c.description}</td>
								<td>
									<c:url var="classes" value="/classes">
										<c:param name="courseId" value="${c.id}"></c:param>
									</c:url>
									<a href="${classes}">Open Classes</a>
								
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
		</div>
	</div>
</body>
</html>