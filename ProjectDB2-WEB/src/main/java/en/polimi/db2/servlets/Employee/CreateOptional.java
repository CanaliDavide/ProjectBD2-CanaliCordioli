package en.polimi.db2.servlets.Employee;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import en.polimi.db2.services.OptionalSrv;
import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.ErrorManager;
import en.polimi.db2.utils.Utility;

/**
 * Servlet implementation class CreateOptional
 */
@WebServlet("/CreateOptional")
public class CreateOptional extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	private UserSrv userService;
	@EJB
	private OptionalSrv optionalService;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		Integer idUser=-1;
		String username="";
		if (session == null) {
			ErrorManager.instance.setError(HttpServletResponse.SC_REQUEST_TIMEOUT, "Session timed out!", response);
			return;
		} else {
			try {
				if (session.getAttribute("idUser") != null) {
					idUser = (Integer) session.getAttribute("idUser");
				}
			} catch (Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST,
						"Some parameters was incorrect, please re-login!", response);
				return;
			}
		}

		if (idUser != -1) {
			if (!userService.isEmployee(idUser)) {
				ErrorManager.instance.setError(HttpServletResponse.SC_FORBIDDEN,
						"You are not allowed to see this page!", response);
				return;
			}

			try {
				username = userService.findUser(idUser).getUsername();
			} catch (Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error in querying the database", response);
				return;
			}
		} else {
			ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST,
					"Some parameters was incorrect, please re-login!", response);
			return;
		}
		
		String nameOptional = request.getParameter("optionName");
		String feeString = request.getParameter("feeMonthly");
		Float fee=null;
		if(Utility.getInstance().checkString(feeString) && Utility.getInstance().checkString(nameOptional)) {
			try {
				fee= Float.valueOf(feeString);
			}catch(Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST,
						"Some parameters was incorrect!", response);
				return;
			}
		}
		else {
			ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST,
					"Some parameters was incorrect!", response);
			return;
		}
		
		optionalService.createOptional(fee, nameOptional);
		
		response.sendRedirect("HomePageEmployee");
	}

}
