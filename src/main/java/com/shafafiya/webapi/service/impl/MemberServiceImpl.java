package com.shafafiya.webapi.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.shafafiya.webapi.entity.Person;
import com.shafafiya.webapi.repository.InhouseRepo;
import com.shafafiya.webapi.repository.PolDetailsRepo;
import com.shafafiya.webapi.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{

	
	@Autowired 
	PolDetailsRepo policyRepository;
	
	@Autowired 
	InhouseRepo inhouseRepository;
	
	@Override
	public Optional<Person> getInhouseMemberDetails(String CardNo, String PolNo,String EndNo) {
		// TODO Auto-generated method stub
		
		Optional<Person> person=null;
		
		person=inhouseRepository.getInhouseMemberDetails(CardNo, PolNo, EndNo);
			
		return person;		
		//return policyRepository.getMemberDetails(CardNo.trim(), PolNo.trim(), EndNo.trim());
	}
	
	@Override
	public Optional<Person> getMemberDetails(String CardNo, String PolNo,String EndNo) {
		// TODO Auto-generated method stub
		
		Optional<Person> person=null;
		
		person=inhouseRepository.getInhouseMemberDetails(CardNo, PolNo, EndNo);
		if(person ==null) {
			person=Optional.ofNullable(policyRepository.getMemberDetails(CardNo.trim(), PolNo.trim(), EndNo.trim()));	
		}
		
		return person;		
		//return policyRepository.getMemberDetails(CardNo.trim(), PolNo.trim(), EndNo.trim());
	}

	@Override
	public Optional<Integer> createPerson(Person person) {
		// TODO Auto-generated method stub
		try {
			return inhouseRepository.createPersonEntry(person);
		} catch (SQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			return Optional.of(9);
		}
		
	
	}

	@Override
	public Optional<Integer> updatePerson(Person person) {
		
			return inhouseRepository.updatePersonEntry(person);
		
	}
	
	
	

}
