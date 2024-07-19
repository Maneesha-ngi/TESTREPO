package com.shafafiya.webapi.service;

import java.util.List;
import java.util.Optional;

import com.shafafiya.webapi.entity.Person;


public interface MemberService {
	
	Optional<Person> getInhouseMemberDetails(String CardNo,String PolNo,String EndNo);
	Optional<Person> getMemberDetails(String CardNo,String PolNo,String EndNo);
	Optional<Integer> createPerson(Person person);
	Optional<Integer> updatePerson(Person person);
	
}
