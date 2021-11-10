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

import en.polimi.db2.entities.OptionalData;
import en.polimi.db2.entities.PackageData;
import en.polimi.db2.entities.Validityperiod;
import en.polimi.db2.services.OptionalSrv;
import en.polimi.db2.services.PackageSrv;
import en.polimi.db2.services.PeriodSrv;
import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.ErrorManager;
import en.polimi.db2.utils.Utility;

/**
 * Servlet implementation class BuyService
 */
@WebServlet("/BuyService")
public class BuyService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private UserSrv userService;

	@EJB
	private PackageSrv packageService;

	@EJB
	private PeriodSrv periodService;

	@EJB
	private OptionalSrv optionalService;

	private TemplateEngine templateEngine;

	public void init() throws ServletException {
		ServletContext context = getServletContext();
		this.templateEngine = Utility.getInstance().connectTemplate(context);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Integer idUser = -1;
		boolean isLogged = false;
		String idPackString = request.getParameter("idPack");// controllare che questo esista
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
			isLogged = true;
			if (userService.isEmployee(idUser)) {
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
		}

		List<PackageData> packages = null;

		try {
			packages = packageService.findAllPackage();
		} catch (Exception e) {
			ErrorManager.instance.setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error in querying the database", response);
			return;
		}

		int idPack = -1;

		if (Utility.getInstance().checkString(idPackString)) {
			try {
				idPack = Integer.parseInt(idPackString);
			} catch (Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST,
						"Some parameters was incorrect, please try again!", response);
				return;
			}

			PackageData pack = null;
			try {
				pack = packageService.findPackageWithId(idPack);
			} catch (Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error in querying the database", response);
				return;
			}

			if (pack == null) {
				ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST,
						"Some parameters was incorrect, please try again!", response);
				return;
			}
		} else {
			if (packages.size() > 0) {
				idPack = packages.get(0).getId();
			} else {
				ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST, "Database empty", response);
				return;
			}
		}

		List<OptionalData> optionals = null;
		List<Validityperiod> validityPeriod = null;

		try {
			optionals = packageService.findPackageWithId(idPack).getOptionalData();
			validityPeriod = packageService.findPackageWithId(idPack).getValidityPeriods();
		} catch (Exception e) {
			ErrorManager.instance.setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error in querying the database", response);
			return;
		}

		String path = "Templates/BuyService.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("isLogged", isLogged);
		ctx.setVariable("name", username);
		ctx.setVariable("packages", packages);
		ctx.setVariable("validityPeriod", validityPeriod);
		ctx.setVariable("optionals", optionals);
		ctx.setVariable("idSelected", idPack);
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
