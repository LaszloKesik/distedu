package hu.kesik.tutorials.distedu.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.kesik.tutorials.distedu.model.Course;

public class AddCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static Logger LOGGER = LogManager.getRootLogger();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// input parsing with validation
		List<String> errorMsgs = new ArrayList<>();
		String name = request.getParameter("name").trim();
		if (name == null || name.isEmpty()) {
			errorMsgs.add("Please enter the name of the course!");
		}
		String description = request.getParameter("description").trim();
		String priceStr = request.getParameter("price").trim();
		double price = 0;
		try {
			price = Double.parseDouble(priceStr);
			if (price < 0) {
				errorMsgs.add("Price must be a positive number!");
			}
		} catch (NumberFormatException e) {
			errorMsgs.add("Price must be a number!");
		}
		
		if(!errorMsgs.isEmpty()){
			request.setAttribute("errorMsgs", errorMsgs);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("error.jsp");
			requestDispatcher.forward(request, response);
		}else{
			Course course = new Course(name,description,price);
			List<Course> courseList = (List<Course>) this.getServletContext().getAttribute("courseList");
			courseList.add(course);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("list_courses.view");
			requestDispatcher.forward(request, response);
		}

	}

}