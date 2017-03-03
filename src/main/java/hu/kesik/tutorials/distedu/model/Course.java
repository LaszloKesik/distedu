package hu.kesik.tutorials.distedu.model;

import java.io.Serializable;

public class Course implements Serializable{

	private int id;
	private String name;
	private String description;
	private double price;

	public Course() {
		this(-1, "", "", 0.0);
	}

	public Course(int id, String name, String description, double price) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return name + "#" + description + "#" + price;
	}

}
