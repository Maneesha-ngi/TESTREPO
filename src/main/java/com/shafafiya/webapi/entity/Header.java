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
public class Header {
	
	private String  SenderID;
	private String  ReceiverID;
	private String  TransactionDate;
	private String  RecordCount;
	private String  DispositionFlag;

}
