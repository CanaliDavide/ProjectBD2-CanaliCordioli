package en.polimi.db2.servlets.Client;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import en.polimi.db2.entities.OrderData;
import en.polimi.db2.services.OrderSrv;
import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.Utility;

/**
 * Servlet implementation class LoadConfirm
 */
@WebServlet("/LoadConfirm")
public class LoadConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB
	private UserSrv userService;
	@EJB
	private OrderSrv orderService;
       
    public void init() throws ServletException{	
    	ServletContext context = getServletContext();
        this.templateEngine = Utility.getInstance().connectTemplate(context);
    }
    
    public LoadConfirm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		Integer idUser=-1;
		boolean isLogged=false;
		String username="";
		String orderId_str;
		int orderId = -1;
		
		if(session==null) {
			//errore e dice che devi riloggare
			return;
		}
		else {
			try {
				if(session.getAttribute("idUser")!=null) {
					idUser=(Integer)session.getAttribute("idUser");
				}
			}
			catch(Exception e) {
				//errore e dice che devi riloggare
				return;
			}
		}
		if(idUser!=-1) {
			isLogged=true;
			username=userService.findUser(idUser).getUsername();
		}
		
		orderId_str = request.getParameter("idOrder");
		if(Utility.getInstance().checkString(orderId_str))
			orderId = Integer.parseInt(orderId_str);
		
		OrderData order = orderService.findRejectedOrderOfUser(orderId, idUser);
		
		if(order != null) {
			String path = "Templates/Confirmation.html";
			ServletContext servletContext = getServletContext();
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			ctx.setVariable("isLogged", isLogged);
			ctx.setVariable("name", username);
			ctx.setVariable("namePack", order.getPackageData().getName());
			ctx.setVariable("dateOfActivation", order.getDataActivation());
			ctx.setVariable("validityString", order.getValidityperiod().getMonth());
			ctx.setVariable("options", order.getOptionalData());
			ctx.setVariable("cost", order.getTotalCost());
			
			session.setAttribute("idOrder", orderId);
			templateEngine.process(path, ctx, response.getWriter());
		}else {
			//errore
		}
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
