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
public class StockOut {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String gst;
	private String remarks;
	private Double totalQuantity;
	private Double rate;
	private Double totalPrice;

	@Temporal(TemporalType.DATE) @DateTimeFormat(pattern = "yyyy-MM-dd") private Date date;

	@Transient private List<String> stockOutParts;
	@OneToMany(mappedBy="stockOut", cascade = CascadeType.ALL) private List<Roll> rollList;
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name ="customer") private Customer customer;


	//Billing Fields
	private String billNo;
	private String billType;
	private String hsnCode;
	private String transport;
	private String transportBillNo;
	private String transportGstNo;

	private Double discount;
	private Double cgst;
	private Double sgst;
	private Double igst;
	private Double labourCharge;
	private Double netAmount;
	private Double totalPaid;
	private Double totalDue;

	@Temporal(TemporalType.DATE) @DateTimeFormat(pattern = "yyyy-MM-dd") private Date paymentDate;


	//Methods
	public void processParts(RollRepo rollRepo) {

		if(this.totalQuantity == null) this.totalQuantity = 0.0;
		if(this.rate == null) this.rate = 0.0;
		if(this.totalPrice == null) this.totalPrice = 0.0;
		if(this.discount == null) this.discount = 0.0;
		if(this.cgst == null) this.cgst = 0.0;
		if(this.sgst == null) this.sgst = 0.0;
		if(this.igst == null) this.igst = 0.0;
		if(this.labourCharge == null) this.labourCharge = 0.0;
		if(this.totalPaid == null) this.totalPaid = 0.0;
		if(this.totalDue == null) this.totalDue = 0.0;

		if("GST".equalsIgnoreCase(this.billType)) {
			this.netAmount = this.totalPrice - this.discount + this.labourCharge
					+ (this.totalPrice*(this.sgst/100))
					+ (this.totalPrice*(this.cgst/100))
					+ (this.totalPrice*(this.igst/100));

		} else {
			this.netAmount = this.totalPrice + this.labourCharge - this.discount;

		}
		this.totalDue = this.netAmount - this.totalPaid;

		rollRepo.removeStockOutReference(this.id);

		this.totalQuantity = 0.0;
		this.totalPrice = 0.0;
		
		if(this.stockOutParts != null) {
			this.rollList = new ArrayList<>();
			Roll roll = null;

			for (String stockOutPartString : stockOutParts) {
				String[] arr = stockOutPartString.split("\\|\\~\\|", -1);
				roll = rollRepo.findById(Long.valueOf(arr[0] !="" ? arr[0] : "0" )).get();
				roll.setStockOut(this);
				roll.setStockOutIndicator(true);
				roll.setStockOutPrice(Double.valueOf(arr[1] !="" ? arr[1] : "0.0" ));
				this.rollList.add(roll);

				this.totalQuantity += roll.getQuantity();
				this.totalPrice += roll.getStockOutPrice();

			}
		}

	}



	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder(gst)
				.append("~").append(rate)
				.append("~").append(remarks);

//		if(rollList != null) sb.append("~").append(rollList.toString());
		if(customer != null) sb.append("~").append(customer.getName());

		return sb.toString();

	}

}
