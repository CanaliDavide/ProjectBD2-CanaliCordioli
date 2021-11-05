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

import en.polimi.db2.services.OrderSrv;

/**
 * Servlet implementation class AverageOptionalsPerPackage
 */
@WebServlet("/AverageOptionalsPerPackage")
public class AverageOptionalsPerPackage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	OrderSrv orderService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AverageOptionalsPerPackage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Object[]> result = orderService.avgOptionalsPerPackage();
		
		List<Integer> packsIds = new ArrayList<Integer>();
		List<Double> average = new ArrayList<Double>();
		
		for(Object[] res : result) {
			packsIds.add((Integer) res[0]);
			Long d1 = (Long) res[1];
			Long d2 = (Long) res[2];
			Double avg = d1.doubleValue()/d2.doubleValue();
			average.add(avg);
		}
		average.stream().forEach(x ->System.out.println(x));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
