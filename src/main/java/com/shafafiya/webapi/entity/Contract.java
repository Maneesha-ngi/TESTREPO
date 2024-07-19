package com.shafafiya.webapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contract {
	private String  PackageName;
	private String  StartDate;
	private String  RenewalDate;
	private String  ExpiryDate;
	private String  GrossPremium;
	private String  PolicyHolder;
	private String  CompanyID;
	private String  CollectedPremium;
	private String  VAT;
	private String  VATPercent;
	private String  Status;

}
