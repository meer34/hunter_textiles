package com.hunter.web.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class StockInRoll {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String sortNo;
	private String rollNo;
	private int quantity;
	private double price;
	
	@ManyToOne
	@JoinColumn(name="stockIn")
	private StockIn stockIn;
	
	public StockInRoll(String rollNo, String quantity, String price, StockIn stockIn) {
		this.sortNo = stockIn.getSortNo();
		this.rollNo = rollNo;
		this.quantity = Integer.valueOf(quantity);
		this.price = Double.valueOf(price);
		this.stockIn = stockIn;
	}
	
	@Override
	public String toString() {
		return new StringBuilder(sortNo)
				.append("~").append(rollNo)
				.append("~").append(quantity)
				.append("~").append(price)
				.toString();
	}

}
