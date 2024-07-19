package com.shafafiya.webapi.service;

import java.util.List;


import com.shafafiya.webapi.entity.PersonNationality;


public interface PersonNationalityService {
	
	
	PersonNationality getPesronNationDetails(String CardNo, String PolNo,String EndNo);
	
}
