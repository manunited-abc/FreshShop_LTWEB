package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import model.Category;
import model.Product;
import model.ProductCategory;
import model.Review;


public class ProductDAO {
		DBConnect db;
		Connection conn;
	public ProductDAO() throws SQLException {
		db = new DBConnect();
		conn = db.getConnection();
	}
	public boolean insertProduct(String id, String name, String des, double price, String linkI, String idCato, String linkL) {
		boolean result = true;
		try {
			
			PreparedStatement ps = conn.prepareStatement("insert into SANPHAM values(?,?,?,?,?,?,?)");
			ps.setString(1,id);
			ps.setString(2,name);
			ps.setString(3,des);
			ps.setDouble(4,price);
			ps.setString(5,linkI);
			ps.setString(6,idCato);
			ps.setString(7,linkL);
			ps.executeUpdate();
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		} 
		return result;
	}
	public List<Category> getListCategories(){
		List<Category> resultList = new ArrayList<Category>();
		try {
			
			PreparedStatement ps = conn.prepareStatement("select * from LOAI");
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String id = rs.getString("MALOAI");
				String name = rs.getString("TENLOAI");
				Category category = new Category(id, name,0);
				resultList.add(category);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return resultList;
	}
	public List<Product> getListProduct(){
		List<Product> resultList = new ArrayList<Product>();
		try {
			conn=db.getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from SANPHAM");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String id = rs.getString("MASP");
				String name = rs.getString("TENSP");
				String description = rs.getString("MOTA");
				double price = Double.parseDouble(rs.getString("GIA"));
				String linkImage = rs.getString("LINK_IMAGE");
				String linkList = rs.getString("LINK_LIST");
				Product product = new Product(id, name, description, price, linkImage, linkList,0);
				resultList.add(product);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return resultList;
	}
	public List<Category> numProductofCategory() {
		List<Category> resultList = new ArrayList<Category>();
		try {
			
			PreparedStatement ps = conn.prepareStatement("select MALOAI, COUNT(*) AS NUM "
					+ "From SANPHAM "
					+ "Group by MALOAI");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String id =rs.getString(1);
				int count = Integer.parseInt(rs.getString(2));
				Category category = new Category(id, "",count);
				resultList.add(category);
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return resultList;
	}
	public Product findById(String id) {

		Product product = null;
		try {
			
			PreparedStatement ps =conn.prepareStatement("SELECT * FROM SANPHAM WHERE MASP = ?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String ids = rs.getString("MASP").trim();
				String name = rs.getString("TENSP").trim();
				String description = rs.getString("MOTA").trim();
				double price = Double.parseDouble(rs.getString("GIA"));
				String linkImage = rs.getString("LINK_IMAGE").trim();
				String linkList = rs.getString("LINK_LIST").trim();
				Product product1 = new Product(ids, name, description, price, linkImage, linkList,getListReviews(id).size());
				product = product1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return product;
	}
	public void remove() {
		db.remove();
	}
	public List<Product> getListSearch(String search, double startValue, double endValue){

		List<Product> resultList = new ArrayList<Product>();
		try {
			
			PreparedStatement ps = conn.prepareStatement("select * from SANPHAM \r\n"
					+ "inner join LOAI on SANPHAM.MALOAI = LOAI.MALOAI\r\n"
					+ "where dbo.fChuyenCoDauThanhKhongDau(SANPHAM.TENSP) like ? or SANPHAM.TENSP like ? or LOAI.TENLOAI like ? or LOAI.MALOAI =? or SANPHAM.GIA BETWEEN ? AND ?");
			String search1="";
			if(search!="") {
				search1="%"+search+" %";
			}
			
			ps.setString(1, search1);
			ps.setString(2, search1);
			ps.setString(3, search);
			ps.setString(4, search);
			ps.setDouble(5, startValue);
			ps.setDouble(6, endValue);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String id = rs.getString("MASP");
				String name = rs.getString("TENSP");
				String description = rs.getString("MOTA");
				double price = Double.parseDouble(rs.getString("GIA"));
				String linkImage = rs.getString("LINK_IMAGE");
				String linkList = rs.getString("LINK_LIST");
				Product product = new Product(id, name, description, price, linkImage, linkList,getListReviews(id).size());
				resultList.add(product);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return resultList;
	}
	public List<Product> pagdingProduct(int index){

		List<Product> resultList = new ArrayList<Product>();
		try {
			
			PreparedStatement ps = conn.prepareStatement("select * from SANPHAM order by MASP\r\n"
					+ "offset  ? rows fetch next 9 rows only");
			ps.setInt(1, (index-1)*9);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String id = rs.getString("MASP");
				String name = rs.getString("TENSP");
				String description = rs.getString("MOTA");
				double price = Double.parseDouble(rs.getString("GIA"));
				String linkImage = rs.getString("LINK_IMAGE");
				String linkList = rs.getString("LINK_LIST");
				Product product = new Product(id, name, description, price, linkImage, linkList,numOfReview(id));
				resultList.add(product);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return resultList;
	}
	public int getTotalProduct() {
		try {
			
			PreparedStatement ps = conn.prepareStatement("select COUNT(*) from SANPHAM");
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				return rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void main(String[] args) throws SQLException {
		ProductDAO p = new ProductDAO();
	
		for(Review r : p.getListReviews("SP001")) {
			System.out.println(r.getContent());
		    
		}
	}	
	public List<Review> getListReviews(String idProduct) {
		List<Review> resultList = new ArrayList<Review>();
		try {
			
			PreparedStatement ps = conn.prepareStatement("select MANX,NOIDUNG,DANHGIA,THOIGIAN,KHACHHANG.HO, KHACHHANG.TEN "
					+ "from NHANXET inner join KHACHHANG on "
					+ "NHANXET.MAKH = KHACHHANG.MAKH where NHANXET.MASP=?");
			ps.setString(1, idProduct);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String id = rs.getString("MANX");
				String content = rs.getString("NOIDUNG");
				String rating = rs.getString("DANHGIA");
				Calendar dateReview= new GregorianCalendar();
				dateReview.setTime(rs.getDate("THOIGIAN"));
				String name = rs.getString("HO")+" "+rs.getString("TEN");;
				Review review = new Review(id, content, rating, dateReview,name);
				resultList.add(review);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return resultList;
	}
	public int numOfReview(String id) {
		try {
			
			PreparedStatement ps = conn.prepareStatement("select  COUNT(MAKH) \r\n"
					+ "from NHANXET\r\n"
					+ "where MASP=?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt(1);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return 0;
	}
	public void insertReview(Review review,String idProduct, String idCustomer) {
		try {
		
			PreparedStatement ps = conn.prepareStatement("insert into NHANXET values(?,?,?,?,?,?)");
			ps.setString(1, review.getId());
			ps.setString(2, idCustomer);
			ps.setString(3, review.getContent());
			ps.setString(4, review.getRating());
			Date date = new Date(review.getDateReview().getTime().getTime());
			ps.setString(5, idProduct);
			ps.setDate(6,date);
			ps.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	public void insertCategory(String id, String name) {
		try {
			
			PreparedStatement ps = conn.prepareStatement("insert into LOAI values(?,?)");
			ps.setString(1, id);
			ps.setString(2,name);
			ps.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	public void removeCategory(String[] ids) {
		try {
			for(String id : ids) {
				PreparedStatement ps = conn.prepareStatement("delete from LOAI where MALOAI = ?");
				ps.setString(1,id);
				ps.executeUpdate();
			}	
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<ProductCategory> getNameCateogorys() {
		List<ProductCategory> resultList = new ArrayList<ProductCategory>();
		try {		
			PreparedStatement ps = conn.prepareStatement("select MASP, TENLOAI from SANPHAM inner join LOAI on SANPHAM.MALOAI = LOAI.MALOAI\r\n");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String id = rs.getString("MASP");
				String name = rs.getString("TENLOAI");
				ProductCategory product = new ProductCategory(id,name);
				resultList.add(product);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return resultList;
	}
	public void updateCategory(String id, String name) {
		try {
			PreparedStatement ps = conn.prepareStatement("update LOAI SET TENLOAI = ? where MALOAI = ? ");
			ps.setString(1, name);
			ps.setString(2, id);
			ps.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	public Category findCategoryById(String id) {
		Category category = null;
		try {
			
			PreparedStatement ps = conn.prepareStatement("select * from LOAI where MALOAI = ?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String ids = rs.getString("MALOAI");
				String name = rs.getString("TENLOAI");
				category = new Category(ids, name, 0);
				return category;
			}	
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	public void udpateProduct(String id,String name,String des,String price,String linkI,String category,String linkL) {

		try {	
			PreparedStatement ps;
			double prices = Double.parseDouble(price);
			ps = conn.prepareStatement("update SANPHAM set TENSP = ?, MOTA = ?, GIA = ?, MALOAI = ?, LINK_IMAGE= ?, LINK_LIST = ?  where MASP = ? ") ;
			ps.setString(1, name);
			ps.setString(2, des);
			ps.setDouble(3, prices);
			ps.setString(4, category);
			ps.setString(5, linkI);
			ps.setString(6, linkL);
			ps.setString(7, id);
			ps.executeUpdate();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
