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

import en.polimi.db2.entities.Alert;
import en.polimi.db2.entities.OrderData;
import en.polimi.db2.entities.UserData;
import en.polimi.db2.services.AlertSrv;
import en.polimi.db2.services.OrderSrv;
import en.polimi.db2.services.UserSrv;

/**
 * Servlet implementation class InsolventSuspendedAndAlerts
 */
@WebServlet("/InsolventSuspendedAndAlerts")
public class InsolventSuspendedAndAlerts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	OrderSrv orderService;
	@EJB
	UserSrv userService;
	@EJB
	AlertSrv alertService;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsolventSuspendedAndAlerts() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<OrderData> orderResult = orderService.findAllSuspended();
		List<UserData> userResult = userService.findAllInsolvent();
		List<Alert> alertResult = alertService.findAll();
	
		for(OrderData ord:orderResult)
			System.out.println(ord.getId());
		for(UserData usr:userResult)
			System.out.println(usr.getId());
		for(Alert alt: alertResult)
			System.out.println(alt.getId());
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
