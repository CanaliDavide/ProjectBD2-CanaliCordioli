package en.polimi.db2.servlets.Client;

import java.io.IOException;
import java.text.DateFormat;
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

import en.polimi.db2.services.OptionalSrv;
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
	@EJB
	private OptionalSrv optionalService;
	
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
				if(session.getAttribute("idUser")!=null) {
					idUser=(Integer)session.getAttribute("idUser");
				}
			}
			catch(Exception e) {
				//errore e dice che devi riloggare
				System.out.print("error relog necessary");
				//return;
			}
		}
		if(idUser != null && idUser!=-1) {
			isLogged=true;
			username=userService.findUser(idUser).getUsername();
		}
	
		String packSelection = request.getParameter("packSelection"); //id pack scelto
		String validity = request.getParameter("ValidityPeriod");//id validity perido
		String[] options = request.getParameterValues("opt"); // non so bene cosa cazzo ci sia dentro -- ho scoperto che contiene gli id degli option selezioanti
	    String activationDate = request.getParameter("activationDate");
		
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALIAN);
	    
	    Date actDate = null;
	    try {
			 actDate = formatter.parse(activationDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i=0;i<options.length;i++){
		    System.out.println("Option: "+options[i]);
		}
		
		System.out.println("pack selection:" + packSelection+ "-----validity id:"+validity);
		Integer idPack=-1;
		Integer idValidity =-1;
		List<Integer> idOptional = new ArrayList<Integer>();
		if(ins.checkString(packSelection)&& ins.checkString(validity) ) {
			try {
				idPack=Integer.parseInt(packSelection);
				idValidity=Integer.parseInt(validity);
				if(options!=null) {
					for(int i=0; i<options.length;i++) {
						idOptional.add(Integer.parseInt(options[i]));
					}
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
		ctx.setVariable("idPack", idPack);
		ctx.setVariable("namePack", namePack);
		ctx.setVariable("idVal", idValidity);
		ctx.setVariable("validityString", validityString);
		ctx.setVariable("cost", cost);
		ctx.setVariable("options", optionalService.findByIds(idOptional));
		ctx.setVariable("dateOfActivation", actDate);
		
		session.setAttribute("isFromConfirm", true);
		session.setAttribute("idPack", idPack);
		session.setAttribute("idVal", idValidity);
		session.setAttribute("cost", cost);
		session.setAttribute("options", optionalService.findByIds(idOptional));
		session.setAttribute("dateOfActivation", actDate);
		
		templateEngine.process(path, ctx, response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//possiamo mettere nel post l'azione casuale di accettare o no la transazione
		
	}

}
