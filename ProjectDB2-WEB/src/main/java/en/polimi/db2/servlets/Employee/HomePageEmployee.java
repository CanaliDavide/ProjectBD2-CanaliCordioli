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
    
    
    public void init() throws ServletException{	
    	ServletContext context = getServletContext();
        this.templateEngine = Utility.getInstance().connectTemplate(context);
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		Integer idUser=-1;
		String username="";
		if(session==null) {
			//errore e dice che devi riloggare
			return;
		}
		else {
			try {
				idUser=(Integer)session.getAttribute("idUser"); 
			}
			catch(Exception e) {
				//errore e dice che devi riloggare
				return;
			}
		}
		if(userService.findUser(idUser)==null) {
			//errore
			return;
		}
		if(!userService.findUser(idUser).getIsEmployee()) {
			//errore autorizzazione
			return;
		}
		
		username=userService.findUser(idUser).getUsername();
		
		List<Service> services =  serviceService.findAll();
		List<OptionalData> optionals = optionalService.findAll();
		List<Validityperiod> periods = periodService.findAllPeriode();
		
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

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
