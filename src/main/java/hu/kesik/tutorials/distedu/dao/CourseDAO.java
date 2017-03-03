package hu.kesik.tutorials.distedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.kesik.tutorials.distedu.model.Course;

public class CourseDAO {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String JNDI_URI = "java:/comp/env/jdbc/DevEnvDB";

	private String jndiURI;
	private Connection connection = null;
	private DataSource ds;

	public CourseDAO() {
		this(JNDI_URI);
	}

	public CourseDAO(String jndiURI) {
		super();
		this.jndiURI = jndiURI;
		init();
	}

	private void init() {
		try {
			Context ctx = new InitialContext();
			this.ds = (DataSource) ctx.lookup(this.jndiURI);
		} catch (NamingException e) {
			LOGGER.error("JNDI Naming error: " + e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void connect() {
		try {
			this.connection = this.ds.getConnection();
		} catch (SQLException e) {
			LOGGER.error("Connection error: " + e.getMessage());
		}

	}

	public void disconnect() {
		if (this.connection == null)
			return;
		try {
			this.connection.close();
		} catch (SQLException e) {
			LOGGER.error("Disconnection error: " + e.getMessage());
		}
	}

	public int deleteCourses(String[] ids) {
		connect();
		if (this.connection == null) {
			return -1;
		}
		// Creating SQL statement
		Statement stmt = null;
		if (ids.length == 0) {
			return 0;
		}
		String sql = "delete from course where (id in (" + ids[0];
		for (int i = 1; i < ids.length; i++) {
			sql += ", " + ids[i];
		}
		sql += "))";
		// Running SQL statement
		int result = 0;
		try {
			stmt = this.connection.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			LOGGER.error("SQL delete error: " + e.getMessage());
			result = -1;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LOGGER.error(e.getMessage());
				}
			}
			disconnect();
		}
		return result;
	}

	public boolean insertCourse(Course course) {
		connect();
		if (this.connection == null) {
			return false;
		}

		boolean success = false;
		String sql = "Insert into course(name, description, price) values (?,?,?)";
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, course.getName());
			stmt.setString(2, course.getDescription());
			stmt.setDouble(3, course.getPrice());
			success = stmt.execute();
			// connection.commit(); - not needed because autocommit=true
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LOGGER.error(e.getMessage());
				}
			}
			disconnect();
		}
		return success;

	}

	public List<Course> getAllCourses() {
		connect();
		if (this.connection == null) {
			return null;
		}
		String query = "Select * from course";
		Statement stmt = null;
		ResultSet rs = null;
		List<Course> list = new ArrayList<>();

		try {
			stmt = this.connection.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("NAME");
				String description = rs.getString("DESCRIPTION");
				double price = rs.getDouble("PRICE");
				list.add(new Course(id, name, description, price));
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					LOGGER.error("ResultSet close: " + e.getMessage());
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LOGGER.error("Statement close: " + e.getMessage());
				}
			}
			disconnect();
		}
		return list;
	}

}
