package com.vince.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "item_tbl")
public class Item {
	
	@Id
	@Column(name="item_id")
	private String id="";
	
	@Column(name="item_name")
	@JsonProperty("name")
	private String name;
	
	@Column(name="item_type")
	@JsonProperty("type")
	private String type;
	
	@Column(name="item_datecreated")
	private String dateCreated="";
	
	@Column(name="item_count")
	@JsonProperty("count")
	private String count = "0";
	
	@Column(name="item_active")
	private Boolean active = true;

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

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", type=" + type + ", dateCreated=" + dateCreated + ", count="
				+ count + ", active=" + active + "]";
	}

}
