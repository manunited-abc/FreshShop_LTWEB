package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import model.Customer;


public class CustomerDAO {
	DBConnect db;
	Connection conn;
	public CustomerDAO() throws SQLException {
		db = new DBConnect();
		conn = db.getConnection();
	}
	
	public boolean insertCustomer(Customer customer) {
		boolean result = true;
		try {
			PreparedStatement ps = conn.prepareStatement("insert into KHACHHANG values(?,?,?,?,?,?,?,?)");
			ps.setString(1,customer.getMakh());
			ps.setString(2,customer.getFirstName());
			ps.setString(3,customer.getLastName());
			ps.setString(4,customer.getEmail());
			ps.setString(5,customer.getPassword());
			ps.setString(6,null);
			ps.setString(7,null);
			ps.setString(8,null);

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			ps.executeUpdate();
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		} 
		return result;
	}
	public void insertRole(String role, String idCustomer) {
		try {
			PreparedStatement ps = conn.prepareStatement("insert into ROLE_USER values(?,?)");
			ps.setString(1,role);
			ps.setString(2,idCustomer);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	public String getRole(String idUser) {
		String role = "";
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ROLE_USER WHERE MAKH = ?;");
			ps.setString(1, idUser);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				role = rs.getString("ROLE_USER").trim();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return role;
	}
	public List<Customer> getListCustomer() {

		List<Customer> resultList = new ArrayList<Customer>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM KHACHHANG;");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String makh = rs.getString(1).trim();
				String lastName = rs.getString(2).trim();
				String firstName = rs.getString(3).trim();
				String emails = rs.getString(4).trim();
				String pass = rs.getString(5).trim();
				Customer kh = new Customer(makh, firstName, lastName, emails, pass);
				resultList.add(kh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	public Customer findById(String id) {

		Customer customer = null;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM KHACHHANG WHERE MAKH = ?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String makh = rs.getString(1).trim();
				String lastName = rs.getString(2).trim();
				String firstName = rs.getString(3).trim();
				String emails = rs.getString(4).trim();
				String pass = rs.getString(5).trim();
				Customer kh = new Customer(makh, firstName, lastName, emails, pass);
				customer = kh;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
		return customer;
	}
	public Customer findByEmail(String email) {
	
		Customer customer = null;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM KHACHHANG WHERE EMAIL = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String makh = rs.getString(1).trim();
				String lastName = rs.getString(2).trim();
				String firstName = rs.getString(3).trim();
				String emails = rs.getString(4).trim();
				String pass = rs.getString(5).trim();
				Customer kh = new Customer(makh, firstName, lastName, emails, pass);
				customer = kh;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
		return customer;
	}

	public boolean checkLogin(String email, String pass) {

		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM KHACHHANG WHERE EMAIL = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				boolean isPassword= BCrypt.checkpw(pass, rs.getString(5).trim());
				if (rs.getString(4).trim().equals(email) && isPassword) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
	
		}
		return false;
	}

	public boolean checkEmail(String email) {

		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM KHACHHANG WHERE EMAIL = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		
		}
		return true;
	}

//	public void remove() {
//		db.remove();
//	}
	public static void main(String[] args) {

	}

}
