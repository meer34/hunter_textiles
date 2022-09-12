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
public class StockIn {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String sortNo;
	private String billNo;
	
	@ManyToOne
	@JoinColumn(name ="party")
	private Party mahajan;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	private double rate;
	private double totalPrice;
	private String receivedBy;
	private String remarks;
	
	private int totalQuantity;
	
	@Transient
	private List<String> stockInParts;
	
	@OneToMany(mappedBy="stockIn", cascade = CascadeType.ALL)
	private List<StockInRoll> stockInRollList;
	
	public void processParts() {
		if(stockInParts == null) return;
		
		stockInRollList = new ArrayList<>();
		StockInRoll stockInPart = null;
		
		for (String stockInPartString : stockInParts) {
			String[] arr = stockInPartString.split("\\|\\~\\|", -1);
			stockInPart = new StockInRoll(arr[0], arr[1], arr[2], this);
			stockInRollList.add(stockInPart);
			
			totalPrice += Double.valueOf(arr[2]);
			totalQuantity += Integer.valueOf(arr[2]);
			
		}
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder(sortNo)
				.append("~").append(billNo)
				.append("~").append(receivedBy)
				.append("~").append(remarks);
		
		if(stockInParts != null) sb.append("~").append(stockInParts.toString());
		if(mahajan != null) sb.append("~").append(mahajan.getName());
		
		return sb.toString();
		
	}

}
