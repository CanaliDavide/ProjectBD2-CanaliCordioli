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
 * Servlet implementation class PackageValue
 */
@WebServlet("/PackageValue")
public class PackageValue extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	OrderSrv orderService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PackageValue() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Object[]> result = orderService.packageValue();
		
		List<Integer> packsIds = new ArrayList<Integer>();
		List<Double> totalValue = new ArrayList<Double>();
		List<Double> valueNoOpt = new ArrayList<Double>();

		
		for(Object[] res : result) {
			System.out.println(res[0] + " " + res[1] +" "+ res[2]);
			packsIds.add((Integer) res[0]);
			totalValue.add((Double) res[1]);
			valueNoOpt.add((Double) res[2]);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}