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
public class Person {
	private String  UnifiedNumber;
	private String  FirstNameEn;
	private String  MiddleNameEn;
	private String  LastNameEn;
	private String  FirstNameAr;
	private String  MiddleNameAr;
	private String  LastNameAr;
	private String  ContactNumber;
	private String  BirthDate;
	private String  Gender;
	private String  Nationality;
	private String  NationalityCode;
	private String  City;
	private String  PassportNumber;
	private String  EmiratesIDNumber;
	private String  SponsorNumber;
	private String  SponsorNameEn;
	private String  SponsorNameAr;
	private String  endSrl;
	
	private String uploadStatus;
	private String uploadResponse;
	
	private Member member;


}
