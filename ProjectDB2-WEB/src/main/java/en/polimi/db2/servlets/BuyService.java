package en.polimi.db2.servlets;

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
    
    
    public void init() throws ServletException{	
    	ServletContext context = getServletContext();
        this.templateEngine = Utility.getInstance().connectTemplate(context);
    }
    
    public BuyService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		Integer idUser=-1;
		boolean isLogged=false;
		String numId=request.getParameter("idPack");//controllare che questo esista
		String username="";
		if(session==null) {
			//errore e dice che devi riloggare
			return;
		}
		else {
			try {
				if(session.getAttribute("idUser")!=null) {
					idUser=(Integer)session.getAttribute("idUser");
				}
			}
			catch(Exception e) {
				//errore e dice che devi riloggare
				return;
			}
		}
		if(idUser!=-1) {
			isLogged=true;
			username=userService.findUser(idUser).getUsername();
		}
		
		List<PackageData> packages=packageService.findAllPackage();
		
		List<Validityperiod> validityPeriod= periodService.findAllPeriode();
		int idPack=-1;
		if(Utility.getInstance().checkString(numId)) {
			try {
				idPack=Integer.parseInt(numId);
			}catch(Exception e ) {
				//errore
			}
			if(packageService.findPackageWithId(idPack)==null) {
				//errore
				return;
			}
		}
		else {
			idPack=packages.get(0).getId();
		}
		
		List<OptionalData> optionals = packageService.findPackageWithId(idPack).getOptionalData();
		
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
