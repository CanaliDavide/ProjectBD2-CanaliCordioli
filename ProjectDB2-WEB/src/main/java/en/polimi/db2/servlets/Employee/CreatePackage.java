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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		String[] options = request.getParameterValues("opt");
		String[] services = request.getParameterValues("serv");
		String[] periods = request.getParameterValues("prd");
		String namePackage = request.getParameter("packageName");
		
		List<Integer> optionsInteger=new ArrayList<Integer>();
		List<Integer> servicesInteger=new ArrayList<Integer>();
		List<Integer> periodsInteger=new ArrayList<Integer>();
		
		if(options!=null) {
			try {
				for(int i=0; i<options.length; i++) {
					optionsInteger.add(Integer.parseInt(options[i]));
				}
			}catch(Exception e) {
				//errore
			}
		}
		
		if(services!=null) {
			try {
				for(int i=0; i<services.length; i++) {
					servicesInteger.add(Integer.parseInt(services[i]));
				}
			}catch(Exception e) {
				//errore
			}
		}
		if(periods != null) {
			try {
				for(int i=0; i<services.length; i++) {
					periodsInteger.add(Integer.parseInt(periods[i]));
				}
			}catch(Exception e) {
				//errore
			}
		}
		
		packageService.createPackage(namePackage, 
				optionalService.findByIds(optionsInteger), 
				serviceService.findByIds(servicesInteger),
				periodService.findByIds(periodsInteger));		
		
		response.sendRedirect("HomePageEmployee");
	}


}
