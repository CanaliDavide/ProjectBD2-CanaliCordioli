package en.polimi.db2.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import en.polimi.db2.services.PackageSrv;
import en.polimi.db2.services.PeriodSrv;
import en.polimi.db2.services.UserSrv;
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
	
    public void init() throws ServletException{	
    	ServletContext context = getServletContext();
        this.templateEngine = Utility.getInstance().connectTemplate(context);
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utility ins=Utility.getInstance();
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
				System.out.print("error relog necessary");
				//return;
			}
		}
		if(idUser!=-1) {
			isLogged=true;
			username=userService.findUser(idUser).getUsername();
		}
		
		String packSelection = request.getParameter("packSelection"); //id pack scelto
		String validity = request.getParameter("ValidityPeriod");//id validity perido
		String[] options = request.getParameterValues("opt"); // non so bene cosa cazzo ci sia dentro -- ho scoperto che contiene gli id degli option selezioanti
	
		for(int i=0;i<options.length;i++){
		    System.out.println("Option: "+options[i]);
		}
		System.out.println("pack selection:" + packSelection+ "-----validity id:"+validity);
		Integer idPack=-1;
		Integer idValidity =-1;
		List<Integer> idOptional = new ArrayList<Integer>();
		if(ins.checkString(packSelection)&& ins.checkString(validity)) {
			try {
				idPack=Integer.parseInt(packSelection);
				idValidity=Integer.parseInt(validity);
				for(int i=0; i<options.length;i++) {
					idOptional.add(Integer.parseInt(options[i]));
				}
			}catch (Exception e) {
				//qualche merda
			}
		}
		
		//se sono qua vuol dire tutti gli id sono validi!
		//ora li posso veramente salvare nella session
		
		String namePack=null;
		int validityString=-1;
		
		namePack=packageService.findPackageWithId(idPack).getName();
		
		validityString=periodService.findValidityWithId(idValidity).getMonth();
		
		Double cost = packageService.totalCostForPackage(idPack, idValidity,idOptional);
		
		String path = "Templates/Confirmation.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("isLogged", isLogged);
		ctx.setVariable("name", username);
		ctx.setVariable("namePack", namePack);
		ctx.setVariable("validityString", validityString);
		ctx.setVariable("cost", cost);
		templateEngine.process(path, ctx, response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//possiamo mettere nel post l'azione casuale di accettare o no la transazione
		
	}

}
