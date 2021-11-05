package en.polimi.db2.servlets.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
 * Servlet implementation class AverageOptionalsPerPackage
 */
@WebServlet("/AverageOptionalsPerPackage")
public class AverageOptionalsPerPackage extends HttpServlet {
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

		List<Object[]> result = null;
		List<Object[]> finalResult = new ArrayList<>();

		try {
			result = orderService.avgOptionalsPerPackage();
		} catch (Exception e) {
			ErrorManager.instance.setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error in querying the database", response);
			return;
		}

		for (Object[] o : result) {
			try {
				String[] array = new String[4];

				array[0] = ((Integer) o[0]).toString();
				array[1] = (String) o[1];
				Long d1 = (Long) o[2];
				Long d2 = (Long) o[3];
				Double d3 = d1.doubleValue() / d2.doubleValue();
				String avg = d3.toString();
				String[] avgSplit = new String[1];

				if (avg.contains(".")) {
					avgSplit = avg.split(Pattern.quote("."), 2);
				} else {
					avgSplit[0] = avg;
				}

				if (avgSplit.length == 1) {
					array[2] = avgSplit[0];
				} else {
					avgSplit[1] = avgSplit[1].concat("000");
					array[2] = avgSplit[0] + "." + avgSplit[1].substring(0, 2);
				}
				finalResult.add(array);
			} catch (Exception e) {
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
		ctx.setVariable("query3", false);
		ctx.setVariable("query4", true);
		ctx.setVariable("query5", false);
		ctx.setVariable("query6", false);
		ctx.setVariable("result", finalResult);

		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
