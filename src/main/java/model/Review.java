package model;


import java.util.Calendar;
import java.util.Date;

public class Review {
	String id;
	String content;
	String rating;
	Calendar dateReview;
	String name;
	public Review(String id, String content, String rating, Calendar dateReview, String name) {
		super();
		this.id = id;
		this.content = content;
		this.rating = rating;
		this.dateReview = dateReview;
		this.name= name;
	
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public Calendar getDateReview() {
		return dateReview;
	}
	public void setDateReview(Calendar dateReview) {
		this.dateReview = dateReview;
	}
	
}
