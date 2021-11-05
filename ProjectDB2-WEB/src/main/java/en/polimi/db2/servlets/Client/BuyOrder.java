package en.polimi.db2.servlets.Client;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import en.polimi.db2.entities.OrderData;
import en.polimi.db2.services.AlertSrv;
import en.polimi.db2.services.OptionalSrv;
import en.polimi.db2.services.OrderSrv;
import en.polimi.db2.services.PackageSrv;
import en.polimi.db2.services.PeriodSrv;
import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.ErrorManager;
import en.polimi.db2.utils.Utility;

/**
 * Servlet implementation class BuyOrder
 */
@WebServlet("/BuyOrder")
public class BuyOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	OrderSrv orderService;
	@EJB
	OptionalSrv optionalService;
	@EJB
	PackageSrv packageService;
	@EJB
	UserSrv userService;
	@EJB
	PeriodSrv periodService;
	@EJB
	AlertSrv alertService;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

		if (idUser == -1) {
			ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST,
					"Some parameters was incorrect, please re-login!", response);
			return;
		}
		
		if (userService.isEmployee(idUser)) {
			ErrorManager.instance.setError(HttpServletResponse.SC_FORBIDDEN,
					"You are not allowed to see this page!", response);
			return;
		}
			

		Integer idOrder = null;

		try {
			idOrder = (Integer) session.getAttribute("idOrder");
		} catch (Exception e) {
			ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST,
					"Some parameters was incorrect, please re-login!", response);
			return;
		}

		long currentDate = System.currentTimeMillis();
		Timestamp datetime = new Timestamp(currentDate);
		if (idOrder == null) {
			Double cost = null;
			Date actDate = null;
			List<Integer> optionals = null;
			int idPack = -1;
			int idValidity = -1;
			try {
				cost = (Double) session.getAttribute("cost");
				actDate = (Date) session.getAttribute("dateOfActivation");
				optionals = (List<Integer>) session.getAttribute("options");
				idPack = (int) session.getAttribute("idPack");
				idValidity = (int) session.getAttribute("idVal");
			} catch (Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST,
						"Some parameters was incorrect, please re-login!", response);
				return;
			}

			float totalCost = cost.floatValue();

			boolean isValid = Utility.getInstance().externalService();

			int numberOfInvalid = isValid ? 0 : 1;

			try {
				orderService.createOrder(actDate, datetime, isValid, numberOfInvalid, totalCost,
						optionalService.findByIds(optionals), packageService.findPackageWithId(idPack),
						userService.findUser(idUser), periodService.findValidityWithId(idValidity));
			} catch (Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error in querying the database", response);
				return;
			}

		} else {
			boolean isValid = Utility.getInstance().externalService();
			OrderData order = null;
			Integer numOfFailed = -1;
			
			try {
				order = orderService.buyInsolvent(idOrder, idUser, isValid);
				numOfFailed = orderService.numberOfFailedPay(idUser);
			} catch (Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Error in querying the database", response);
				return;
			}

			if (!isValid && numOfFailed % 3 == 0) {
				try {
					alertService.createAlert(order.getUserData().getMail(), order.getUserData().getUsername(),
							order.getUserData(), datetime, order.getTotalCost());
				} catch (Exception e) {
					ErrorManager.instance.setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
							"Error in querying the database", response);
					return;
				}
			}
		}
		session.removeAttribute("idOrder");
		response.sendRedirect("HomePageClient");
	}
}