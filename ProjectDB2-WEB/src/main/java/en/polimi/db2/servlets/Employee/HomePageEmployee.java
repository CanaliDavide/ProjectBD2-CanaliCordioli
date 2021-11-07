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

import en.polimi.db2.entities.OptionalData;
import en.polimi.db2.entities.Service;
import en.polimi.db2.entities.Validityperiod;
import en.polimi.db2.services.OptionalSrv;
import en.polimi.db2.services.PeriodSrv;
import en.polimi.db2.services.ServiceSrv;
import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.ErrorManager;
import en.polimi.db2.utils.Utility;

/**
 * Servlet implementation class HomePageEmployee
 */
@WebServlet("/HomePageEmployee")
public class HomePageEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private UserSrv userService;
	@EJB
	private OptionalSrv optionalService;
	@EJB
	private ServiceSrv serviceService;
	@EJB
	private PeriodSrv periodService;

	private TemplateEngine templateEngine;

	public void init() throws ServletException {
		ServletContext context = getServletContext();
		this.templateEngine = Utility.getInstance().connectTemplate(context);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Integer idUser = -1;
		String username = "";
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

		username = userService.findUser(idUser).getUsername();

		List<Service> services = serviceService.findAll();
		List<OptionalData> optionals = optionalService.findAll();
		List<Validityperiod> periods = periodService.findAllPeriods();

		String path = "Templates/HomeEmployee.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("name", username);
		ctx.setVariable("optionals", optionals);
		ctx.setVariable("services", services);
		ctx.setVariable("validityPeriods", periods);
		ctx.setVariable("isLogged", true);
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
