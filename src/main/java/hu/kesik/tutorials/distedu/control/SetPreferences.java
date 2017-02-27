package hu.kesik.tutorials.distedu.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetPreferences extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static Logger LOGGER = LogManager.getRootLogger();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean showDescription = false;
		boolean showPrice = false;
		
		HttpSession session = request.getSession();
		
		if(request.getParameter("showDescription") != null) showDescription = true;
		if(request.getParameter("showPrice") != null) showPrice = true;
		
		session.setAttribute("showDescription", showDescription);
		session.setAttribute("showPrice", showPrice);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
		requestDispatcher.forward(request, response);
	}
}
