package model;

import java.sql.Date;

public class Antecedant {
	private String type, description;
    private Date date;
    private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
    
	public Antecedant(String type, String description, Date date, int id) {
		super();
		this.type = type;
		this.description = description;
		this.date = date;
		this.id = id;
	}
	public Antecedant() {
		super();
	}
	public Antecedant(String type, String description, Date date) {
		super();
		this.type = type;
		this.description = description;
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
    
    
}
