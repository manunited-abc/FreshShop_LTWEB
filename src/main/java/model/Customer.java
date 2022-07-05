package model;
public class Customer {
	String makh;
	String firstName;
	String lastName;
	String email;
	String password;
	boolean isLogin;
	public Customer(String makh, String firstName, String lastName, String email, String password) {
		super();
		this.makh = makh;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.isLogin = false;
	}
	public boolean getIsLogin() {
		return isLogin;
	}
	public void setIsLogin(boolean isLogin) {
		this.isLogin= isLogin;
	}
	public String getMakh() {
		return makh;
	}

	public void setMakh(String makh) {
		this.makh = makh;
	}	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return lastName+" "+firstName;
	}
	@Override
	public String toString() {
		return "Customer [makh=" + makh + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ "]";
	}

}
