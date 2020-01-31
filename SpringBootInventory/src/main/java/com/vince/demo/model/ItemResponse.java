package com.vince.demo.model;

public class ItemResponse {
	
	String id = "";
	String name = "";
	String type = "";
	String description = "";
	double cost = 0;
	String dateCreated = "";
	int count = 0;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String datecreated) {
		this.dateCreated = datecreated;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "ItemResponse [id=" + id + ", name=" + name + ", type=" + type + ", description=" + description
				+ ", cost=" + cost + ", datecreated=" + dateCreated + ", count=" + count + "]";
	}
	

	
	
	
	

}
