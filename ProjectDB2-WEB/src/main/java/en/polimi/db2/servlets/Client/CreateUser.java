package en.polimi.db2.servlets.Client;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.ErrorManager;
import en.polimi.db2.utils.Utility;

/**
 * Servlet implementation class CreateUser
 */
@WebServlet("/CreateUser")
public class CreateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private UserSrv userService;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Utility ins = Utility.getInstance();
		
		String name = request.getParameter("username");
		String mail = request.getParameter("useremail");
		String password1 = request.getParameter("userpswd1");
		String password2 = request.getParameter("userpswd2");

		if (ins.checkString(name) && ins.checkString(password1) && ins.checkString(password2) && ins.isMail(mail)
				&& password1.equals(password2)) {
			try {
				userService.createUser(name, password2, mail, false, false);
			} catch (Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error creating user in database!", response);
				return;
			}

			HttpSession session = request.getSession(true);
			session.setAttribute("newUserCreated", true);
			response.sendRedirect("Login");
		}

	}

}
