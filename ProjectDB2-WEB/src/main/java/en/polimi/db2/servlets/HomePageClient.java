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

import en.polimi.db2.entities.PackageData;
import en.polimi.db2.services.PackageSrv;
import en.polimi.db2.services.UserSrv;
import en.polimi.db2.utils.Utility;

/**
 * Servlet implementation class HomePageClient
 */
@WebServlet("/HomePageClient")
public class HomePageClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private UserSrv userService;
    
	@EJB
	private PackageSrv packageService;
	
	private TemplateEngine templateEngine;
    
    
    public void init() throws ServletException{	
    	ServletContext context = getServletContext();
        this.templateEngine = Utility.getInstance().connectTemplate(context);
    }
	
    public HomePageClient() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		Integer idUser=-1;
		boolean isLogged=false;
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
		if(idUser!=-1) {
			isLogged=true;
			username=userService.findUser(idUser).getUsername();
		}
		
		List<PackageData> packages=packageService.findAllPackage();
		
		String path = "Templates/HomeClient.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("isLogged", isLogged);
		ctx.setVariable("name", username);
		ctx.setVariable("packages", packages);
		templateEngine.process(path, ctx, response.getWriter());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
