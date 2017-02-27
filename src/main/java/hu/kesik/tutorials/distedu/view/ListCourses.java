package hu.kesik.tutorials.distedu.view;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.kesik.tutorials.distedu.model.Course;
import hu.kesik.tutorials.distedu.model.Courses;

public class ListCourses extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static Logger LOGGER = LogManager.getRootLogger();

	public ListCourses() {
	}

	public void init() {
		String resource = "/WEB-INF/catalog.xml";
		try {
			JAXBContext context = JAXBContext.newInstance(Courses.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Courses courses = (Courses) unmarshaller.unmarshal(new File(this.getServletContext().getResource(resource).getPath()));
			
			this.getServletContext().setAttribute("courseCount", courses.getCourses().size());
			this.getServletContext().setAttribute("courseList", courses.getCourses());		
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF8");
		PrintWriter out = response.getWriter();
		try {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet ListCourses</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1> Course list </h1>");
			out.println("<ul>");
			List<Course> courselist = (List<Course>) this.getServletContext().getAttribute("courseList");	
			Iterator<Course> it = courselist.iterator();
			while (it.hasNext()) {
				out.println("<li>" + it.next() + "</li>");
			}
			out.println("</ul>");
			out.println("</body>");
			out.println("</html>");
		} finally {
			out.close();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
		Courses courses = new Courses();
		courses.setCourses((List<Course>)this.getServletContext().getAttribute("courseList"));
		
		String resource = "/WEB-INF/catalog.xml";
		try {
			JAXBContext context = JAXBContext.newInstance(Courses.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(courses, new File(this.getServletContext().getResource(resource).getPath()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		
	}

}
