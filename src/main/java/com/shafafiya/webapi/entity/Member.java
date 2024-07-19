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
public class Member {
	
	private String  ID;
	private String  Relation;
	private String  RelationTo;
	
	private Contract contract;


}
