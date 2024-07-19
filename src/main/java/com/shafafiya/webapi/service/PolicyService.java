package com.shafafiya.webapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shafafiya.webapi.entity.Policy;


public interface PolicyService {
	

	
	List<Policy> getPolicyDetails(String CardNo,String EID,String fromDate,String toDate);
	
	
}
