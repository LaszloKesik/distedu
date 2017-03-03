<html>
<head></head>
<body>
	<ul>
		<li><a href="list_courses.jsp">List Courses</a></li>
		<li><a href="add_course.jsp">Add Course</a></li>
		<li><a href="delete_courses.jsp">Delete Courses</a></li>
		<br>
		<li><a href="set_preferences.jsp">Set Preferences</a></li>
		
	</ul>

	<h1>
		Menetek száma:
		<%= hu.kesik.tutorials.distedu.listener.SessionCounterListener.getSessionNumber()%>
	</h1>
</body>
</html>