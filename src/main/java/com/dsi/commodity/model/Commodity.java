package com.dsi.commodity.model;

import javax.persistence.*;

@Entity
@Table(name = "commodities")
public class Commodity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name")
	private String name;

	public Commodity() {

	}

	public Commodity(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Commodity [id=" + id + ", name=" + name + "]";
	}

}
