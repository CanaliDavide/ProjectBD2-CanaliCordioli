package en.polimi.db2.servlets.Client;
import java.io.IOException;
import java.util.List;

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
import en.polimi.db2.entities.PackageData;
import en.polimi.db2.services.OrderSrv;
import en.polimi.db2.services.PackageSrv;
import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.ErrorManager;
import en.polimi.db2.utils.Utility;

/**
 * Servlet implementation class HomePageClient
 */
@WebServlet("/HomePageClient")
public class HomePageClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private UserSrv userService;
	@EJB
	private PackageSrv packageService;
	@EJB
	private OrderSrv orderService;
	
	private TemplateEngine templateEngine;
    
    
    public void init() throws ServletException{	
    	ServletContext context = getServletContext();
        this.templateEngine = Utility.getInstance().connectTemplate(context);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		Integer idUser=-1;
		boolean isLogged=false;
		String username="";
		boolean isInsolvent = false;
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
		if(idUser!=-1) {
			isLogged=true;
			username=userService.findUser(idUser).getUsername();
			isInsolvent = userService.findUser(idUser).getIsInsolvent();
		}
		List<PackageData> packages=packageService.findAllPackage();
		List<OrderData> orders = orderService.findAllRejectedWithUserId(idUser);
		
		String path = "Templates/HomeClient.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("isLogged", isLogged);
		ctx.setVariable("name", username);
		ctx.setVariable("packages", packages);
		ctx.setVariable("isInsolvent", isInsolvent);
		ctx.setVariable("rejectedOrders", orders);
		templateEngine.process(path, ctx, response.getWriter());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
