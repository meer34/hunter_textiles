package com.hunter.web.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Roll {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String sortNo;
	private String rollNo;
	private double quantity;
	private double bostaKg;
	private double stockInPrice;
	private boolean stockOutIndicator;
	private double stockOutPrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="stockIn")
	private StockIn stockIn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="stockOut")
	private StockOut stockOut;
	
	public Roll(String rollNo, String quantity, String bostaKg, String stockInPrice, StockIn stockIn) {
		this.sortNo = stockIn.getSortNo();
		this.rollNo = rollNo;
		this.quantity = Double.valueOf(quantity!=""? quantity: "0");
		this.bostaKg = Double.valueOf(bostaKg!=""? bostaKg: "0");
		this.stockInPrice = Double.valueOf(stockInPrice!=""? stockInPrice: "0");
		this.stockIn = stockIn;
	}
	
	@Override
	public String toString() {
		return new StringBuilder(String.valueOf(id))
				.append("~").append(sortNo)
				.append("~").append(rollNo)
				.append("~").append(quantity)
				.append("~").append(bostaKg)
				.append("~").append(stockInPrice)
				.append("~").append(stockOutPrice)
				.toString();
	}

}
