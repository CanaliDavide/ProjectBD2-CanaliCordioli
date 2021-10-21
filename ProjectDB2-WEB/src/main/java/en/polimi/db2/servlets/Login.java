package en.polimi.db2.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import en.polimi.db2.services.UserService;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private UserService userService;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String DB_URL = "jdbc:mysql://localhost:3306/telcodb";
		final String USER = "root";
		final String PASS = "polimidb2";
		String result = "Connection worked";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (Exception e) {
			result = "Connection failed";
			e.printStackTrace();
		}
		
		userService.createUser("prova", "Canali", "prova", false, false);
		
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.close();
	}
}
