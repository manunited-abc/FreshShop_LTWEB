package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import dao.CustomerDAO;
import model.Customer;
import model.IDRandom;



/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		CustomerDAO khd = (CustomerDAO)getServletContext().getAttribute("khachHangDAO");
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		String passwordEncode = BCrypt.hashpw(pass, BCrypt.gensalt(12));
		if (firstName != null || lastName != null || email != null || pass != null) {
			IDRandom idrandom= new IDRandom();
			String makh = "KH-"+idrandom.getIDRandom();
			Customer kh = new Customer(makh, firstName, lastName, email, passwordEncode);
			boolean isExitsEmail = false;
			boolean isSuccess = false;	
				if(!khd.checkEmail(email)) {
					isExitsEmail= true;
					request.setAttribute("isExitsEmail", isExitsEmail);
					request.getRequestDispatcher("register.jsp").forward(request, response);
				}else {
					khd.insertCustomer(kh);
					khd.insertRole("CUSTOMER", kh.getMakh());
					isSuccess=true;
					request.setAttribute("isSuccess", isSuccess);
					request.getRequestDispatcher("login.jsp").forward(request, response);		
				}
				
			}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
