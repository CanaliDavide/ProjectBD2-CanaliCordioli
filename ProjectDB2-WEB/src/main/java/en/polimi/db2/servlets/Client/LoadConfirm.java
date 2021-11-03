package en.polimi.db2.servlets.Client;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import en.polimi.db2.entities.OrderData;
import en.polimi.db2.services.OrderSrv;
import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.ErrorManager;
import en.polimi.db2.utils.Utility;

/**
 * Servlet implementation class LoadConfirm
 */
@WebServlet("/LoadConfirm")
public class LoadConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private UserSrv userService;
	@EJB
	private OrderSrv orderService;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		Integer idUser=-1;
		String orderId_str;
		int orderId = -1;
		
		if(session==null) {
			ErrorManager.instance.setError(HttpServletResponse.SC_REQUEST_TIMEOUT, "Session timed out!", response);
			return;
		}
		else {
			try {
				if(session.getAttribute("idUser")!=null) {
					idUser=(Integer)session.getAttribute("idUser");
				}
			}
			catch(Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST, "Some parameters was incorrect, please re-login!", response);
				return;
			}
		}
		
		orderId_str = request.getParameter("idOrder");
		
		if(Utility.getInstance().checkString(orderId_str)) {
			try {
				orderId = Integer.parseInt(orderId_str);
			}catch(Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST, "Some parameters was incorrect, please re-login!", response);
				return;
			}
		}
			
		
		OrderData order = orderService.findRejectedOrderOfUser(orderId, idUser);
		
		if(order != null) {

			session.setAttribute("idOrder", orderId);
			response.sendRedirect("Confirmation");
		}else {
			ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST, "Some parameters was incorrect, please re-login!", response);
		}
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
