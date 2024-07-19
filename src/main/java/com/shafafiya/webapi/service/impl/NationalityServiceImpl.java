package com.shafafiya.webapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shafafiya.webapi.entity.Nationality;
import com.shafafiya.webapi.repository.PolDetailsRepo;
import com.shafafiya.webapi.service.NationalityService;

@Service
public class NationalityServiceImpl implements NationalityService{

	@Autowired 
	PolDetailsRepo policyRepository;
	
	@Override
	public List<Nationality> getNationality() {
		// TODO Auto-generated method stub
		return policyRepository.getNationality();
	}

}
