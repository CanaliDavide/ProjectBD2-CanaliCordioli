package en.polimi.db2.servlets.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import en.polimi.db2.services.OptionalSrv;
import en.polimi.db2.services.PackageSrv;
import en.polimi.db2.services.PeriodSrv;
import en.polimi.db2.services.ServiceSrv;
import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.ErrorManager;

/**
 * Servlet implementation class CreatePackage
 */
@WebServlet("/CreatePackage")
public class CreatePackage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private UserSrv userService;
	@EJB
	private PackageSrv packageService;
	@EJB
	private ServiceSrv serviceService;
	@EJB
	private OptionalSrv optionalService;
	@EJB
	private PeriodSrv periodService;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

		String[] options = request.getParameterValues("opt");
		String[] services = request.getParameterValues("serv");
		String[] periods = request.getParameterValues("prd");
		String namePackage = request.getParameter("packageName");

		List<Integer> optionsInteger = new ArrayList<Integer>();
		List<Integer> servicesInteger = new ArrayList<Integer>();
		List<Integer> periodsInteger = new ArrayList<Integer>();

		if (options != null) {
			try {
				for (int i = 0; i < options.length; i++) {
					optionsInteger.add(Integer.parseInt(options[i]));
				}
			} catch (Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST, "Some parameters was incorrect!",
						response);
				return;
			}
		}

		if (services != null) {
			try {
				for (int i = 0; i < services.length; i++) {
					servicesInteger.add(Integer.parseInt(services[i]));
				}
			} catch (Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST, "Some parameters was incorrect!",
						response);
				return;
			}
		}
		if (periods != null) {
			try {
				for (int i = 0; i < periods.length; i++) {
					periodsInteger.add(Integer.parseInt(periods[i]));
				}
			} catch (Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST, "Some parameters was incorrect!",
						response);
				return;
			}
		}

		try {
			packageService.createPackage(namePackage, optionalService.findByIds(optionsInteger),
					serviceService.findByIds(servicesInteger), periodService.findByIds(periodsInteger));
		} catch (Exception e) {
			ErrorManager.instance.setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error in querying the database", response);
			return;
		}

		response.sendRedirect("HomePageEmployee");
	}

}
