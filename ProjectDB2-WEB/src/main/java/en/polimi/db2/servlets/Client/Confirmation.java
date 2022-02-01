package en.polimi.db2.servlets.Client;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import en.polimi.db2.entities.OptionalData;
import en.polimi.db2.entities.OrderData;
import en.polimi.db2.services.OptionalSrv;
import en.polimi.db2.services.OrderSrv;
import en.polimi.db2.services.PackageSrv;
import en.polimi.db2.services.PeriodSrv;
import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.ErrorManager;
import en.polimi.db2.utils.Utility;

/**
 * Servlet implementation class Confirmation
 */
@WebServlet("/Confirmation")
@MultipartConfig
public class Confirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;

	@EJB
	private UserSrv userService;
	@EJB
	private PackageSrv packageService;
	@EJB
	private PeriodSrv periodService;
	@EJB
	private OptionalSrv optionalService;
	@EJB
	private OrderSrv orderService;

	public void init() throws ServletException {
		ServletContext context = getServletContext();
		this.templateEngine = Utility.getInstance().connectTemplate(context);
	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Utility ins = Utility.getInstance();

		HttpSession session = request.getSession(false);
		Integer idUser = -1;
		boolean isLogged = false;
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

		String packSelection = request.getParameter("packSelection");
		String validity = request.getParameter("ValidityPeriod");
		String[] options = request.getParameterValues("opt");
		String activationDate = request.getParameter("activationDate");
		
		System.out.print("\n\n");
		System.out.print("from request.getparameter\n");
		System.out.print(packSelection);
		System.out.print("\n");
		System.out.print(validity);
		System.out.print("\n");
		System.out.print(options);
		System.out.print("\n");
		System.out.print(activationDate);
		System.out.print("\n\n");

		Date actDate = null;
		Integer idPack = -1;
		Integer idValidity = -1;
		List<Integer> idOptional = new ArrayList<Integer>();
		
		Integer idOrder;

		if (options == null)
			options = new String[0];

		if (!ins.checkString(packSelection) || !ins.checkString(validity) || !ins.checkString(activationDate)) {
			System.out.print("\n dovrei esser qui");
			try {

				
				idOrder = (Integer) session.getAttribute("idOrder");
				
				if (idOrder != null) {
					OrderData order = orderService.findRejectedOrderOfUser(idOrder, idUser);
					idPack = order.getPackageData().getId();
					idValidity = order.getValidityperiod().getId();
					actDate = order.getDataActivation();
					for (OptionalData op : order.getOptionalData()) {
						idOptional.add(op.getId());
					}
				}else {
					idPack = (Integer) session.getAttribute("idPack");
					idValidity = (Integer) session.getAttribute("idVal");
					idOptional = (List<Integer>) session.getAttribute("options");
					actDate = (Date) session.getAttribute("dateOfActivation");
				}
				
				System.out.print("\n");
				System.out.print(idPack);
				System.out.print("\n");
				System.out.print(idValidity);
				System.out.print("\n");
				System.out.print(idOptional);
				System.out.print("\n");
				System.out.print(actDate);
				System.out.print("\n");
			} catch (Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST,
						"Some parameters was incorrect, please try again!", response);
				return;
			}
		} else {
			System.out.print("\n non dovrei essere qui");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALIAN);

			try {
				actDate = formatter.parse(activationDate);
			} catch (ParseException e1) {
				ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST,
						"Some parameters was incorrect, please try again!", response);
				return;
			}
			try {
				idPack = Integer.parseInt(packSelection);
				idValidity = Integer.parseInt(validity);
				for (int i = 0; i < options.length; i++) {
					idOptional.add(Integer.parseInt(options[i]));
				}
			} catch (Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST,
						"Some parameters was incorrect, please try again!", response);
				return;
			}
		}

		String namePack = null;
		int validityString = -1;
		Double cost = null;
		List<OptionalData> optionalsData = null;

		try {
			namePack = packageService.findPackageWithId(idPack).getName();
			validityString = periodService.findValidityWithId(idValidity).getMonth();
			cost = packageService.totalCostForPackage(idPack, idValidity, idOptional);
			optionalsData = optionalService.findByIds(idOptional);
		} catch (Exception e) {
			ErrorManager.instance.setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Error in querying the database -- forse è qui", response);
			return;
		}
		
		cost = new BigDecimal(String.valueOf(cost)).setScale(2, RoundingMode.FLOOR).doubleValue();
	  
		session.setAttribute("isFromConfirm", true);
		session.setAttribute("idPack", idPack);
		session.setAttribute("cost", cost);
		session.setAttribute("idVal", idValidity);
		session.setAttribute("options", idOptional);
		session.setAttribute("dateOfActivation", actDate);

		String path = "Templates/Confirmation.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		
		ctx.setVariable("isLogged", isLogged);
		ctx.setVariable("name", username);
		ctx.setVariable("idPack", idPack);
		ctx.setVariable("namePack", namePack);
		ctx.setVariable("idVal", idValidity);
		ctx.setVariable("validityString", validityString);
		ctx.setVariable("cost", cost);
		ctx.setVariable("options", optionalsData);
		ctx.setVariable("dateOfActivation", actDate);
		templateEngine.process(path, ctx, response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
