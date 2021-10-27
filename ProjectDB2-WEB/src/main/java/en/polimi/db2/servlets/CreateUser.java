package en.polimi.db2.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.Utility;

/**
 * Servlet implementation class CreateUser
 */
@WebServlet("/CreateUser")
public class CreateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private UserSrv userService;
    
    public CreateUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility ins=Utility.getInstance();
		String name = request.getParameter("username");
		String email = request.getParameter("useremail");
		String password1 = request.getParameter("userpswd1");
		String password2 = request.getParameter("userpswd2");
		
		if(ins.checkString(name) && ins.checkString(password1) 
				&& ins.checkString(password2)&&ins.isMail(email)
				&& password1.equals(password2)) {
			try {
				userService.createUser(name, password2, email, true, false);
			}
			catch(Exception e) {
				//TODO: inventarsi qualcosa
				System.out.println("Error creation user!");
			}
			HttpSession session=request.getSession(true);  
	        session.setAttribute("newUserCreated", true);
	        response.sendRedirect("Login");
		}
		
	}

}
