<%@page contentType="text/html; charset=ISO-8859-1"%>
<%@page import="java.util.*,
 hu.kesik.tutorials.distedu.model.Course,
 hu.kesik.tutorials.distedu.dao.CourseDAO"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
CourseDAO courseDao = new CourseDAO();
List<Course> courseList = courseDao.getAllCourses();
pageContext.setAttribute("courseList", courseList);
%>
<html>
<body>
	<table>
		<tr id="list-header">
			<th>Name</th>
			<c:if test="${showDescription}">
				<th>Description</th>
			</c:if>
			<c:if test="${showPrice}">
				<th>Price</th>
			</c:if>
		</tr>
		<c:forEach items="${courseList}" var="course">
			<tr>
				<td><b>${course.name}</b></td>
				<c:if test="${showDescription}">
					<td>${course.description}</td>
				</c:if>
				<c:if test="${showPrice}">
					<td>${course.price}</td>
				</c:if>
			</tr>

		</c:forEach>

	</table>

</body>
</html>
