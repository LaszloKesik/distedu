<%@page contentType="text/html; charset=ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page
	import="java.util.*, hu.kesik.tutorials.distedu.dao.CourseDAO, hu.kesik.tutorials.distedu.model.Course"%>

<%
	CourseDAO courseDao = new CourseDAO();
	List<Course> courseList = courseDao.getAllCourses();
	pageContext.setAttribute("courseList", courseList);
%>
<html>
<body>
	<h2>Which courses would like to delete?</h2>
	<form action="delete_courses.do" method="post">
		<c:forEach items="${courseList}" var="course">
			<input type="checkbox" name="courses" value="${course.id}" />
			${course.name}
			<br>
			<br>
		</c:forEach>
		<input type="submit" name="submit" value="Delete" />
	</form>
</body>
</html>