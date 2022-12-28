package com.dsi.currency.model;

import javax.persistence.*;

@Entity
@Table(name = "currency")
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name")
	private String name;

	public Currency() {

	}

	public Currency(String name) {
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
		return "Currency [id=" + id + ", name=" + name + "]";
	}

}
