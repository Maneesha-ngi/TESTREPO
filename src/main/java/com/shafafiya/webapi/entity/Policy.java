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
public class Policy {
	
	private String PolNo;
	private String endName;
	private String memName;
	private String eidNo;
	private String cardNo;
	private String memType;
	private String entryDate;
	private String exitDate;
	private String relation;
	private String visaRegion;
	private String validStatus;
	private String uploadStatus;
	private String endNo;
	private String endSrl;
	private String inhouseuploadStatus;
	private String inhouseuploadResponse;
	

}
