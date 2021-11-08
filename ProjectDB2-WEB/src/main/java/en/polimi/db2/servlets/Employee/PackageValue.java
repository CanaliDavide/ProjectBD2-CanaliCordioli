package en.polimi.db2.servlets.Employee;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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

import en.polimi.db2.services.OrderSrv;
import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.ErrorManager;
import en.polimi.db2.utils.Utility;

/**
 * Servlet implementation class PackageValue
 */
@WebServlet("/PackageValue")
public class PackageValue extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private TemplateEngine templateEngine;
	@EJB
	OrderSrv orderService;
	@EJB
	private UserSrv userService;
    
	public void init() throws ServletException {
		ServletContext context = getServletContext();
		this.templateEngine = Utility.getInstance().connectTemplate(context);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Integer idUser = -1;
		String username =null;
		if (session == null) {
			ErrorManager.instance.setError(HttpServletResponse.SC_REQUEST_TIMEOUT, "Session timed out!", response);
			return;
		} else {
			try {
				if (session.getAttribute("idUser") != null) {
					idUser = (Integer) session.getAttribute("idUser");
				}
			} catch (Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST, "Some parameters was incorrect, please re-login!", response);
				return;
			}
		}
		if(userService.findUser(idUser)==null) {
			ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST, "Some parameters was incorrect, please re-login!", response);
			return;
		}
		if (idUser == -1) {
			ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST,
					"Some parameters was incorrect, please re-login!", response);
			return;
		}
		if(!userService.isEmployee(idUser)) {
			ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST, "You don't have permissions!", response);
			return;
		}
		
		username=userService.findUser(idUser).getUsername();
		List<Object[]> result = null;
		try {
			result = orderService.packageValue();
		}catch(Exception e) {
			ErrorManager.instance.setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error in querying the database", response);
			return;
		}
		
		List<String[]> finalResult=new ArrayList<>();
		
		for(Object[] o : result) {
			try {
				String[] array = new String[4];
				
				array[0]=((Integer) o[0]).toString();
				array[1]=(String) o[1];
				Double d1=(Double) o[2];
				Double d2= (Double) o[3];
				
				d1 =  new BigDecimal(String.valueOf(d1)).setScale(2, RoundingMode.FLOOR).doubleValue();
				array[2] = d1.toString();
				d2 =  new BigDecimal(String.valueOf(d2)).setScale(2, RoundingMode.FLOOR).doubleValue();
				array[3] = d2.toString();
				
				finalResult.add(array);
			}catch(Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error in querying the database", response);
				return;
			}
		}
		
		String path = "Templates/SalesReport.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("query1", false);
		ctx.setVariable("query2", false);
		ctx.setVariable("query3", true);
		ctx.setVariable("query4", false);
		ctx.setVariable("query5", false);
		ctx.setVariable("query6", false);
		ctx.setVariable("result", finalResult);
		ctx.setVariable("username", username);
		templateEngine.process(path, ctx, response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
