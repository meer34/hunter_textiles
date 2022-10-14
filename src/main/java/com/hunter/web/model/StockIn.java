package com.hunter.web.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import com.hunter.web.repo.RollRepo;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="mahajan")
	private Party mahajan;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;

	private Double totalQuantity;
	private Double rate;
	private Double totalPrice;
	private String receivedBy;
	private String remarks;

	@Transient
	private List<String> stockInParts;

	@OneToMany(mappedBy="stockIn", cascade = CascadeType.ALL)
	private List<Roll> rollList;

	public void processParts(RollRepo rollRepo) {

		if(this.totalQuantity == null) this.totalQuantity = 0.0;
		if(this.rate == null) this.rate = 0.0;
		if(this.totalPrice == null) this.totalPrice = 0.0;

		this.totalQuantity = 0.0;
		this.totalPrice = 0.0;

		if(this.stockInParts != null) {
			this.rollList = new ArrayList<>();
			Roll newRoll = null;
			
			List<Long> currentChildIds = new ArrayList<>();
			
			for (String stockInPartString : stockInParts) {
				String[] arr = stockInPartString.split("\\|\\~\\|", -1);
				
				if(arr.length == 4) {
					System.out.println("#####1" + stockInPartString);
					newRoll = new Roll(arr[0], arr[1], arr[2], arr[3], this);
					
					this.totalQuantity += Double.valueOf(arr[1] !="" ? arr[1] : "0");
					this.totalPrice += Double.valueOf(arr[3] !="" ? arr[3] : "0.0");
					
				} else {
					System.out.println("#####2" + stockInPartString);
					newRoll = new Roll(arr[1], arr[2], arr[3], arr[4], this);
					newRoll.setId(Long.parseLong(arr[0]));
					currentChildIds.add(newRoll.getId());
					
					this.totalQuantity += Double.valueOf(arr[2] !="" ? arr[2] : "0");
					this.totalPrice += Double.valueOf(arr[4] !="" ? arr[4] : "0.0");
				}
				
				this.rollList.add(newRoll);
			}
			
			if(this.id != 0) rollRepo.deleteStockInAndStockOutOrphanChilds(this.id, currentChildIds);
			
		}
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder(sortNo);

		if(mahajan != null) sb.append("~").append(mahajan.getName()); //Added in between for close(~) search

		sb.append("~").append(billNo)
		.append("~").append(receivedBy)
		.append("~").append(remarks);

		if(rollList != null) sb.append("~").append(rollList.toString());

		return sb.toString();

	}

}
