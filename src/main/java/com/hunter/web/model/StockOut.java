package com.hunter.web.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class StockOut {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name ="customer")
	private Customer customer;
	
	private String gst;
	private String totalQuantity;
	private Double rate;
	private String totalPrice;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	private String remarks;
	
	@Transient
	private List<String> stockOutParts;
	
	@OneToMany(mappedBy="stockOut", cascade = CascadeType.ALL)
	private List<StockOutRoll> stockOutRollList;
	
	public void processParts() {
		if(stockOutParts == null) return;
		
		stockOutRollList = new ArrayList<>();
		StockOutRoll stockOutRoll = null;
		
		for (String stockOutPartString : stockOutParts) {
			String[] arr = stockOutPartString.split("\\|\\~\\|", -1);
			stockOutRoll = new StockOutRoll(arr[0], arr[1], arr[2], arr[3], this);
			stockOutRollList.add(stockOutRoll);
		}
		
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder(gst)
				.append("~").append(rate)
				.append("~").append(remarks);
		
		if(stockOutParts != null) sb.append("~").append(stockOutParts.toString());
		if(customer != null) sb.append("~").append(customer.getName());
		
		return sb.toString();
		
	}

}
