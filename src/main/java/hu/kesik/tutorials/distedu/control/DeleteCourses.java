package hu.kesik.tutorials.distedu.control;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.kesik.tutorials.distedu.dao.CourseDAO;

public class DeleteCourses extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] courseIds = request.getParameterValues("courses");
		if(courseIds != null){
			CourseDAO courseDao = new CourseDAO();
			courseDao.deleteCourses(courseIds);
			LOGGER.debug("Courses deleted: " + Arrays.toString(courseIds));
		}
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);

	}
}
