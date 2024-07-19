package com.shafafiya.webapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shafafiya.webapi.entity.PersonNationality;
import com.shafafiya.webapi.entity.Policy;
import com.shafafiya.webapi.repository.PolDetailsRepo;
import com.shafafiya.webapi.service.NationalityService;
import com.shafafiya.webapi.service.PersonNationalityService;
import com.shafafiya.webapi.service.PolicyService;

@Service
public class PersonNationalityServiceImpl implements PersonNationalityService{
	
	
	@Autowired 
	PolicyService polservice;
	
	@Autowired 
	NationalityService nationalityservice;
	
	
	
	@Autowired 
	PolDetailsRepo policyRepository;

	

	@Override
	public PersonNationality getPesronNationDetails(String CardNo, String PolNo,String EndNo) {
		// TODO Auto-generated method stub
		PersonNationality personnation=new PersonNationality();
		 personnation.setPerson(policyRepository.getMemberDetails(CardNo.trim(), PolNo.trim(), EndNo.trim()));
		 personnation.setNationValue(policyRepository.getNationality());
		return personnation;
	}


	
	

}
