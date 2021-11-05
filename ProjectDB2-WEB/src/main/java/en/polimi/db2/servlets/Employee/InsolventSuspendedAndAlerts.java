package en.polimi.db2.servlets.Employee;

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

import en.polimi.db2.entities.Alert;
import en.polimi.db2.entities.OrderData;
import en.polimi.db2.entities.UserData;
import en.polimi.db2.services.AlertSrv;
import en.polimi.db2.services.OrderSrv;
import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.ErrorManager;
import en.polimi.db2.utils.Utility;

/**
 * Servlet implementation class InsolventSuspendedAndAlerts
 */
@WebServlet("/InsolventSuspendedAndAlerts")
public class InsolventSuspendedAndAlerts extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	OrderSrv orderService;
	@EJB
	UserSrv userService;
	@EJB
	AlertSrv alertService;
	private TemplateEngine templateEngine;

	public void init() throws ServletException {
		ServletContext context = getServletContext();
		this.templateEngine = Utility.getInstance().connectTemplate(context);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		Integer idUser = -1;
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
		if (userService.findUser(idUser) == null) {
			ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST,
					"Some parameters was incorrect, please re-login!", response);
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

		List<OrderData> order = null;
		List<Alert> alert = null;
		List<UserData> user = null;
		try {
			order = orderService.findAllSuspended();
			alert = alertService.findAll();
			user = userService.findAllInsolvent();
		} catch (Exception e) {
			ErrorManager.instance.setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error in querying the database", response);
			return;
		}
		
		String path = "Templates/SalesReport.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("query1", false);
		ctx.setVariable("query2", false);
		ctx.setVariable("query3", false);
		ctx.setVariable("query4", false);
		ctx.setVariable("query5", true);
		ctx.setVariable("query6", false);
		ctx.setVariable("order", order);
		ctx.setVariable("user", user);
		ctx.setVariable("alert", alert);
		templateEngine.process(path, ctx, response.getWriter());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
