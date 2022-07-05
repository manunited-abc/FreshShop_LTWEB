package servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProductDAO;
import dao.UrlDAO;
import model.Customer;
import model.IDRandom;
import model.Review;



/**
 * Servlet implementation class AddReview
 */
@WebServlet("/addreview")
public class AddReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddReview() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProductDAO productDAO = (ProductDAO) getServletContext().getAttribute("productDAO");
		String id = request.getParameter("idProduct");
		String rating = request.getParameter("rating");
	    String content = request.getParameter("content");
	    System.out.println(content);
	    System.out.println(id+" id sp");
	    IDRandom r = new IDRandom();
	    String idReview = "NX-"+r.getIDRandom();
	    Calendar dateReview = new GregorianCalendar();
	    dateReview.setTime(new GregorianCalendar().getTime());
	    Review review = new Review(idReview, content, rating, dateReview, null);
	    HttpSession session = request.getSession();
	    Customer customer ;
	    customer = (Customer)session.getAttribute("user");
	    productDAO.insertReview(review, id, customer.getMakh());
//	    request.getRequestDispatcher("shopdetail").forward(request, response);
		UrlDAO urlDAO = (UrlDAO)getServletContext().getAttribute("urlDAO");
		String urlLast = urlDAO.getUrlLast();
		if(urlLast==null) {
			urlLast="index.jsp";
		}else {
			urlLast=urlLast.substring(1);
		}
		request.getRequestDispatcher(urlLast).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
