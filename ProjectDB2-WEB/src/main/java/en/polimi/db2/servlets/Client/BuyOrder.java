package en.polimi.db2.servlets.Client;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;

import en.polimi.db2.entities.OptionalData;
import en.polimi.db2.entities.OrderData;
import en.polimi.db2.services.AlertSrv;
import en.polimi.db2.services.OptionalSrv;
import en.polimi.db2.services.OrderSrv;
import en.polimi.db2.services.PackageSrv;
import en.polimi.db2.services.PeriodSrv;
import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.Utility;

/**
 * Servlet implementation class BuyOrder
 */
@WebServlet("/BuyOrder")
public class BuyOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;

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

	public void init() throws ServletException {
		ServletContext context = getServletContext();
		this.templateEngine = Utility.getInstance().connectTemplate(context);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		Integer idUser = -1;
		boolean isLogged = false;
		String username = "";
		if (session == null) {
			// errore e dice che devi riloggare
			return;
		} else {
			try {
				if (session.getAttribute("idUser") != null) {
					idUser = (Integer) session.getAttribute("idUser");
				}
			} catch (Exception e) {
				// errore e dice che devi riloggare
				System.out.print("error relog necessary");
				// return;
			}
		}
		if (idUser != -1) {
			isLogged = true;
		}

		Integer idOrder = (Integer) session.getAttribute("idOrder");
		long currentDate = System.currentTimeMillis();
		Timestamp datetime = new Timestamp(currentDate);
		if (idOrder == null) {

			Double cost = (Double) session.getAttribute("cost");
			float totalCost = cost.floatValue();

			Date actDate = (Date) session.getAttribute("dateOfActivation");



			boolean isValid = Utility.getInstance().externalService();

			int numberOfInvalid = isValid ? 0 : 1;

			List<Integer> optionals = (List<Integer>) session.getAttribute("options");
			int idPack = (int) session.getAttribute("idPack");
			int idValidity = (int) session.getAttribute("idVal");

			orderService.createOrder(actDate, datetime, isValid, numberOfInvalid, totalCost,
					optionalService.findByIds(optionals), packageService.findPackageWithId(idPack),
					userService.findUser(idUser), periodService.findValidityWithId(idValidity));

		}else {
			boolean isValid = Utility.getInstance().externalService();
			OrderData order = orderService.buyInsolvent(idOrder, idUser, isValid);
			if(!isValid && order.getNumberOfInvalid() == 3)
				alertService.createAlert(order.getUserData().getMail(),order.getUserData().getUsername(), order.getUserData(), datetime, order.getTotalCost());
		}

		response.sendRedirect("HomePageClient");
	}

}
