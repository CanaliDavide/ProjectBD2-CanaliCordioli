package en.polimi.db2.servlets.Common;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import en.polimi.db2.entities.UserData;
import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.ErrorManager;
import en.polimi.db2.utils.Utility;
import javax.ejb.EJB;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private UserSrv userService;

	private TemplateEngine templateEngine;

	public void init() throws ServletException {
		ServletContext context = getServletContext();
		this.templateEngine = Utility.getInstance().connectTemplate(context);
	}

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		
		boolean newUserCreated = false;
		boolean logInError = false;

		if (session.getAttribute("newUserCreated") != null) {
			newUserCreated = (boolean) session.getAttribute("newUserCreated");
		}
		if (session.getAttribute("logInError") != null) {
			logInError = (boolean) session.getAttribute("logInError");
		}
		String path = "index.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("logInError", logInError);
		ctx.setVariable("newUserCreated", newUserCreated);
		templateEngine.process(path, ctx, response.getWriter());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Utility ins = Utility.getInstance();
		String mail = request.getParameter("emailLogIn");
		String password = request.getParameter("passwordLogIn");
		UserData user = null;
		if (ins.checkString(password) && ins.isMail(mail)) {
			try {
				user = userService.checkCredentials(mail, password);
			} catch (Exception e) {
				ErrorManager.instance.setError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Conection with db failed!", response);
				return;
				//System.out.println("problema con connessione o cose strane");
			}
			if (user != null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("idUser", user.getId());
				boolean isFromCofirm = false;
				if (user.getIsEmployee()) {
					response.sendRedirect("HomePageEmployee");
				} else {
					if (session.getAttribute("isFromConfirm") == null) {
						response.sendRedirect("HomePageClient");
					} else {
						isFromCofirm = (boolean) session.getAttribute("isFromConfirm");
						response.sendRedirect("Confirmation");
					}
				}
			} else {
				HttpSession session = request.getSession(false);
				session.setAttribute("logInError", true);
				doGet(request, response);
			}

		}
		else {
			ErrorManager.instance.setError(HttpServletResponse.SC_BAD_REQUEST, "Missing core parameter!", response);
			return;
		}

	}

}
