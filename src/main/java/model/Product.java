package model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import cart.Item;

public class Product implements Item{
	String idProduct;
	String nameProduct;
	String description;
	double price;
	String linkImage;
	String linkList;
	int popular;

	public Product(String idProduct, String nameProduct, String description, double price, String linkImage,
			String linkList,int popular) {
		super();
		this.idProduct = idProduct;
		this.nameProduct = nameProduct;
		this.description = description;
		this.price = price;
		this.linkImage = linkImage;
		this.linkList = linkList;
		this.popular = popular	;
				}
	public int getPopular() {
		return popular;
	}
	public void setPopular(int popular) {
		this.popular = popular;
	}
	public String getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(String idProduct) {
		this.idProduct = idProduct;
	}
	public String getNameProduct() {
		return nameProduct;
	}
	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getLinkImage() {
		return linkImage;
	}
	public void setLinkImage(String linkImage) {
		this.linkImage = linkImage;
	}
	public String getLinkList() {
		return linkList;
	}
	public void setLinkList(String linkList) {
		this.linkList = linkList;
	}
	@Override
	public String toString() {
		return "Product [idProduct=" + idProduct + ", nameProduct=" + nameProduct + ", description=" + description
				+ ", price=" + price + ", linkImage=" + linkImage + ", linkList=" + linkList + "]";
	}
	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		return this.price;
	}
public static void main(String[] args) {
	Date date = new Date("30/11/2020");
	System.out.println(date);
	
}
	
}
