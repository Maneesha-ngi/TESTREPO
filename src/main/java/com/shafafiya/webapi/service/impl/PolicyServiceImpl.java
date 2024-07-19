package com.shafafiya.webapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shafafiya.webapi.entity.Person;
import com.shafafiya.webapi.entity.Policy;
import com.shafafiya.webapi.repository.InhouseRepo;
import com.shafafiya.webapi.repository.PolDetailsRepo;
import com.shafafiya.webapi.service.PolicyService;

@Service
public class PolicyServiceImpl implements PolicyService{
	
	
	@Autowired 
	PolDetailsRepo policyRepository;
	
	@Autowired 
	InhouseRepo inhouseRepository;

	@Override
	public List<Policy> getPolicyDetails(String CardNo,String EID,String fromDate,String toDate) {
		List<Policy> policy= policyRepository.getPolicyDetails(CardNo.trim(),EID.trim(),fromDate.trim(),toDate.trim());
		//policy.stream().forEach((c) -> logger.info("xxxxxxxxxx = "+c));
		
		policy.stream().forEach((c) -> 
		{
			
			Optional<Person> p=inhouseRepository.getInhouseMemberDetails(c.getCardNo(), c.getPolNo(), c.getEndNo());
			if(p !=null) {
			c.setInhouseuploadStatus(p.isPresent()? p.get().getUploadStatus():"NA");
			c.setInhouseuploadResponse(p.isPresent()? p.get().getUploadResponse():"NA");}
			else {
				c.setInhouseuploadStatus("NA");
				c.setInhouseuploadResponse("NA");
			}
			
		});
		return policy;
	}


	
	

}
