package hu.kesik.tutorials.distedu.listener;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.kesik.tutorials.distedu.model.Course;
import hu.kesik.tutorials.distedu.model.Courses;

public class ApplicationListener implements ServletContextListener {

	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.debug("contextInitialized");
		String resource = "/WEB-INF/data/catalog.xml";
		try {
			JAXBContext context = JAXBContext.newInstance(Courses.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Courses courses = (Courses) unmarshaller.unmarshal(new File(sce.getServletContext().getResource(resource).getPath()));

			sce.getServletContext().setAttribute("courseCount", courses.getCourses().size());
			sce.getServletContext().setAttribute("courseList", courses.getCourses());
			LOGGER.debug("Init: " + sce.getServletContext().getAttribute("courseCount") + " courses loaded!");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LOGGER.debug("contextDestroyed");
		Courses courses = new Courses();
		courses.setCourses((List<Course>) sce.getServletContext().getAttribute("courseList"));

		String resource = "/WEB-INF/data/catalog.xml";
		try {
			JAXBContext context = JAXBContext.newInstance(Courses.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(courses, new File(sce.getServletContext().getResource(resource).getPath()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

	}

}
