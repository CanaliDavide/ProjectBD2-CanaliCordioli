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
 * Servlet implementation class CreateOptional
 */
@WebServlet("/CreateOptional")
public class CreateOptional extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	private UserSrv userService;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		Integer idUser=-1;
		String username="";
		if(session==null) {
			//errore e dice che devi riloggare
			return;
		}
		else {
			try {
				idUser=(Integer)session.getAttribute("idUser"); 
			}
			catch(Exception e) {
				//errore e dice che devi riloggare
				return;
			}
		}
		if(userService.findUser(idUser)==null) {
			//errore
			return;
		}
		if(!userService.findUser(idUser).getIsEmployee()) {
			//errore autorizzazione
			return;
		}
		
		String nameOptional = request.getParameter("optionName");
		String feeString = request.getParameter("feeMonthly");
		Float fee=null;
		if(Utility.getInstance().checkString(feeString) && Utility.getInstance().checkString(nameOptional)) {
			try {
				fee= Float.valueOf(feeString);
			}catch(Exception e) {
				//errore
			}
		}
		else {
			//errore
		}
		
		//query per inserire il nuovo optional nel db
		
		response.sendRedirect("HomePageEmployee");
		
	}

}
