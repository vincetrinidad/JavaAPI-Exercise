package com.vince.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "type_tbl")
public class Type {

	@Id
	@JsonProperty("id")
	@Column(name="type_id")
	private String id;
	
	@Column(name="type_name")
	@JsonProperty("name")
	private String name;
	
	@Column(name="type_description")
	@JsonProperty("description")
	private String description;
	
	@Column(name="type_cost")
	@JsonProperty("cost")
	private String cost="0";
	
	@Column(name="type_datecreated")
	private String dateCreated="";
	
	@Column(name="type_createdby")
	private String createdBy="";
	
	@Column(name="type_active")
	private Boolean active=true;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "Type [type_id=" + id + ", type_name=" + name + ", type_description=" + description
				+ ", type_cost=" + cost + ", type_datecreated=" + dateCreated + ", type_createdby="
				+ createdBy + ", type_active=" + active + "]";
	}
	
	
	
	
}
