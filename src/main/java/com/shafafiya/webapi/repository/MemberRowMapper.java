package com.shafafiya.webapi.repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.shafafiya.webapi.entity.Contract;
import com.shafafiya.webapi.entity.Member;
import com.shafafiya.webapi.entity.Person;

public class MemberRowMapper implements RowMapper<Person> {

	@Override
	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		// TODO Auto-generated method stub
		
		Person person = new Person();
		person.setUnifiedNumber(rs.getString("UID_NUMBER"));
		person.setFirstNameEn(rs.getString("FIRST_NAME"));
		person.setMiddleNameEn(rs.getString("MIDDLE_NAME"));
		person.setLastNameEn(rs.getString("LAST_NAME"));
		person.setFirstNameAr(rs.getString("FIRST_NAME_L2"));
		person.setMiddleNameAr(rs.getString("MIDDLE_NAME_L2"));
		person.setLastNameAr(rs.getString("LAST_NAME_L2"));
		person.setBirthDate(rs.getString("DOB"));
		person.setGender(rs.getString("GENDER"));
		person.setNationality(rs.getString("NATIONALITY"));
		person.setNationalityCode(rs.getString("NATIONALITY_CODE"));
		person.setCity(rs.getString("CITY"));
		person.setPassportNumber(rs.getString("PASSPORT_NO"));
		person.setEmiratesIDNumber(rs.getString("EMIRATES_ID"));
		person.setSponsorNumber(rs.getString("SPONSOR_NO"));
		person.setSponsorNameEn(rs.getString("SPONSOR_NAME"));
		person.setSponsorNameAr(rs.getString("SPONSOR_NAME_L2"));
		person.setContactNumber(rs.getString("CONTACT_NO"));
		person.setEndSrl(rs.getString("PMBRH_ENDT_SRL"));
		person.setUploadStatus(rs.getString("HAAD_UPLOAD_STATUS"));
		person.setUploadResponse(rs.getString("HAAD_RESP_MSG"));
		//person.setEndNo(rs.getString("END_NO"));
		
		//Optional<String> Upload_sts=Optional.ofNullable(rs.getString("HAAD_UPLOAD_STATUS"));
		//person.setUploadStatus(Upload_sts.orElse("NA"));
		//Optional<String> Upload_Resp=Optional.ofNullable(rs.getString("HAAD_RESP_MSG"));
		//person.setUploadResponse(Upload_Resp.orElse("NA"));
		
		
		
		Member member = new Member();
		member.setID(rs.getString("MEMBER_ID"));
		member.setRelation(rs.getString("RELATION"));
		member.setRelationTo(rs.getString("RELATION_TO"));
		
		person.setMember(member);
		
				
				

		Contract contract =new Contract();
		contract.setPackageName(rs.getString("PACKAGE_NAME"));
		contract.setStartDate(rs.getString("START_DATE"));
		contract.setRenewalDate(rs.getString("RENEWAL_DATE"));
		contract.setExpiryDate(rs.getString("EXPIRY_DATE"));
		contract.setGrossPremium(rs.getString("GROSS_PREMIUM"));
		contract.setPolicyHolder(rs.getString("POLICY_HOLDER"));
		contract.setCompanyID(rs.getString("COMPANY_ID"));
		contract.setCollectedPremium(rs.getString("PREMIUM"));
		contract.setVAT(rs.getString("VAT_AMOUNT"));
		contract.setVATPercent(rs.getString("VAT_PERCENT"));
		contract.setStatus(rs.getString("CONTRACT_STATUS"));
		member.setContract(contract);
		
		
		
		
		
		
		
		
		return person;
	}
	
	

}
