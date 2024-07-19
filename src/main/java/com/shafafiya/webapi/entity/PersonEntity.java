package com.shafafiya.webapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name="PersonEntity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonEntity {
	
	

	@Id
	@Column(name="ID",nullable = false)
	private String contactId;
	
	@Column(name="EMAIL")
	private String email;
	
	
	@Column(name="CONTACTNAME")
	private String contactName;
	
	@Column(name="USERID")
	private String userId;
	
	
	
	
	
	
}
