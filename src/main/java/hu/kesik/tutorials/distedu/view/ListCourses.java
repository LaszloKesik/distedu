package hu.kesik.tutorials.distedu.view;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.kesik.tutorials.distedu.model.Course;

public class ListCourses extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static Logger LOGGER = LogManager.getRootLogger();

	public ListCourses() {
	}

	public void init() {
		List<Course> courseList = new ArrayList<Course>();

		String resource = "/WEB-INF/catalog.txt";
		InputStream is = this.getServletContext().getResourceAsStream(resource);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		while (true) {
			String line = null;
			try {
				line = br.readLine();
				if (line == null)
					break;
				StringTokenizer stk = new StringTokenizer(line, "#");
				Course course = new Course();
				course.setName(stk.nextToken());
				course.setDescription(stk.nextToken());
				course.setPrice(Double.parseDouble(stk.nextToken()));
				courseList.add(course);
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}

		this.getServletContext().setAttribute("courseCounter", courseList.size());
		this.getServletContext().setAttribute("courseList", courseList);
		LOGGER.debug("Courses loaded! courseCounter=" + this.getServletContext().getAttribute("courseCounter"));
		try {
			br.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
		int coursecounter = (Integer) this.getServletContext().getAttribute("courseCounter");
		List<Course> courselist = (List<Course>) this.getServletContext().getAttribute("courseList");
		if (coursecounter != courselist.size()) {
			String resource = "/WEB-INF/catalog.txt";
			String path = this.getServletContext().getRealPath(resource);
			LOGGER.debug("destroyPATH:" + path);
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(path));
				Iterator<Course> it = courselist.iterator();
				while (it.hasNext())
					pw.println(it.next());
				pw.close();
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
	}

}
