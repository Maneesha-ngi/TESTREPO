package com.shafafiya.webapi.repository;


import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.shafafiya.webapi.entity.Nationality;
import com.shafafiya.webapi.entity.Person;
import com.shafafiya.webapi.entity.Policy;

import jakarta.annotation.PostConstruct;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Repository
public class InhouseRepo extends JdbcDaoSupport {
	
	
	 private static final Logger logger = LogManager.getLogger(InhouseRepo.class);

	
	@Autowired 
	@Qualifier("datasourceinhouse")
	DataSource dataSource;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}
	
	
	
	public Optional<Person> getInhouseMemberDetails(String CardNo,String polNo,String endNo)  {
		
		logger.info("Entering PolDetailsRepo getPolicyDetails");
		
		try {
		String sql=   "SELECT  HAAD_PER_UID as UID_NUMBER,HAAD_PER_FIRST_NAME_EN as FIRST_NAME,HAAD_PER_MIDDLE_NAME_EN as MIDDLE_NAME,HAAD_PER_LAST_NAME_EN as LAST_NAME,HAAD_PER_FIRST_NAME_AR as FIRST_NAME_L2,HAAD_PER_MIDDLE_NAME_AR as MIDDLE_NAME_L2,\r\n"
				+ "HAAD_PER_LAST_NAME_AR as LAST_NAME_L2,HAAD_PER_CONTACT_NUMBER as CONTACT_NO, TO_CHAR( HAAD_PER_BIRTH_DATE,'dd/mm/yyyy') as DOB,HAAD_PER_GENDER as GENDER,HAAD_PER_NATIONALITY as NATIONALITY,HAAD_PER_NATION_CODE as NATIONALITY_CODE,HAAD_PER_CITY as CITY,\r\n"
				+ "HAAD_PER_PASS_NO as PASSPORT_NO,HAAD_PER_EID as EMIRATES_ID,HAAD_PER_SPONSOR_CARNO as SPONSOR_NO,HAAD_PER_SPONSOR_NAME_EN as SPONSOR_NAME,HAAD_PER_SPONSOR_NAME_AR as SPONSOR_NAME_L2,HAAD_MEM_RELATION as RELATION,\r\n"
				+ "HAAD_MEM_RELATION_TO as RELATION_TO,HAAD_CONT_PACKAGE_NAME as PACKAGE_NAME,TO_CHAR( HAAD_CONT_START_DATE,'dd/mm/yyyy')  as START_DATE,TO_CHAR( HAAD_CONT_REN_DATE,'dd/mm/yyyy')  as RENEWAL_DATE,TO_CHAR( HAAD_CONT_EXP_DATE,'dd/mm/yyyy') as EXPIRY_DATE,HAAD_CONT_COMPANY_ID as COMPANY_ID,\r\n"
				+ "HAAD_CONT_COL_PREM as PREMIUM,HAAD_CONT_GROSS_PREM as GROSS_PREMIUM,HAAD_CONT_VAT as VAT_AMOUNT,HAAD_CONT_VAT_PER as VAT_PERCENT,HAAD_CONT_STATUS as CONTRACT_STATUS,HAAD_POLICY_HOLDER as POLICY_HOLDER,\r\n"
				+ "HAAD_UPLOAD_STATUS,HAAD_RESP_MSG,HAAD_END_SRL_NO as PMBRH_ENDT_SRL,HAAD_MEM_CARD_NO as MEMBER_ID"
	            + " from HAAD_MEM_UPLOAD_DETAIL WHERE  HAAD_MEM_CARD_NO='"+CardNo+"' and HAAD_POL_NO='"+polNo+"' and HAAD_END_SRL_NO='"+endNo+"'";
		
		        logger.info("Info message  QUERY : "+sql);
		        
		        
		        Optional<Person> person=Optional.ofNullable((Person) getJdbcTemplate().queryForObject(sql, new MemberRowMapper ()));
		
		        
		return person;
		 } catch (EmptyResultDataAccessException e) {
		        return null;
		    }
	
		
	}

	
	
	
	
	public Optional<Integer>  createPersonEntry(Person person) throws SQLIntegrityConstraintViolationException {
		
		
		 int result=0;
				try {
					
					
					String sql="insert into HAAD_MEM_UPLOAD_DETAIL values (?,?,?,?,?,?,?,?,?,?,?,to_date(?, 'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,to_date(?, 'dd/mm/yyyy') ,to_date(?, 'dd/mm/yyyy'),to_date(?, 'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?)";
			        		   			   		 
								
	          // String sql="insert into HAAD_MEM_UPLOAD_DETAIL values ('"+person.getMember().getID()+"','"+person.getMember().getContract().getCompanyID()+"',"+person.getEndSrl()+",'"+person.getUnifiedNumber()+"','"+person.getFirstNameEn()+"','"+person.getMiddleNameEn()+"','"+person.getLastNameEn()+"','"+person.getFirstNameAr()+"',"
	        	//	   +"'"+person.getMiddleNameAr()+"','"+person.getLastNameAr()+"','"+person.getContactNumber()+"',TO_DATE('"+person.getBirthDate()+"', 'dd/mm/yyyy'),'"+person.getGender()+"','"+person.getNationality()+"','"+person.getNationalityCode()+"',"
	        	//			   +"'"+person.getCity()+"','"+person.getPassportNumber()+"','"+person.getEmiratesIDNumber()+"','"+person.getSponsorNumber()+"','"+person.getSponsorNameEn()+"','"+person.getSponsorNameAr()+"','"+person.getMember().getRelation()+"',"
	        	//					   +"'"+person.getMember().getRelationTo()+"','"+person.getMember().getContract().getPackageName()+"',TO_DATE('"+person.getMember().getContract().getStartDate()+"', 'dd/mm/yyyy'),TO_DATE('"+person.getMember().getContract().getRenewalDate()+"', 'dd/mm/yyyy'),TO_DATE('"+person.getMember().getContract().getExpiryDate()+"', 'dd/mm/yyyy'),'"+person.getMember().getContract().getCompanyID()+"',"+Double.valueOf(person.getMember().getContract().getCollectedPremium())+","
	        		//						   +Double.valueOf(person.getMember().getContract().getGrossPremium())+","+Double.valueOf(person.getMember().getContract().getVAT())+","+Integer.valueOf(person.getMember().getContract().getVATPercent())+",'"+person.getMember().getContract().getStatus()+"','"+person.getMember().getContract().getPolicyHolder()+"','"+person.getUploadStatus()+"','"+person.getUploadResponse()+"')";

	           logger.info("Sql  ::"+sql);         		
	                    
	           result=getJdbcTemplate().update(sql ,person.getMember().getID(),person.getMember().getContract().getCompanyID(),person.getEndSrl(),person.getUnifiedNumber(),person.getFirstNameEn(),person.getMiddleNameEn(),person.getLastNameEn(),person.getFirstNameAr()
	        		   ,person.getMiddleNameAr(),person.getLastNameAr(),person.getContactNumber(),person.getBirthDate(),person.getGender(),person.getNationality(),person.getNationalityCode(),
    				   person.getCity(),person.getPassportNumber(),person.getEmiratesIDNumber(),person.getSponsorNumber(),person.getSponsorNameEn(),person.getSponsorNameAr(),person.getMember().getRelation(),
    						 person.getMember().getRelationTo(),person.getMember().getContract().getPackageName(),person.getMember().getContract().getStartDate(),person.getMember().getContract().getRenewalDate(),person.getMember().getContract().getExpiryDate(),person.getMember().getContract().getCompanyID(),Double.valueOf(person.getMember().getContract().getCollectedPremium()),
    								   Double.valueOf(person.getMember().getContract().getGrossPremium()),Double.valueOf(person.getMember().getContract().getVAT()),Integer.valueOf(person.getMember().getContract().getVATPercent()),person.getMember().getContract().getStatus(),person.getMember().getContract().getPolicyHolder(),person.getUploadStatus(),person.getUploadResponse());
				  
	           logger.info("Result::"+result);  
	           
	           
			
				}
				catch (DuplicateKeyException e) {
					return Optional.of(9);
				}
				catch (EmptyResultDataAccessException e) {
				
				logger.error("Exception :"+e.getMessage());
				logger.error("Exception toString(): "  + e.toString());
				logger.error("Exception getMessage(): " + e.getMessage());
				logger.error("Exception StackTrace: ");
		        e.printStackTrace();
			

			}catch(Exception e) {
				
				logger.error("Exception :"+e.getMessage());
				logger.error("Exception toString(): "  + e.toString());
				logger.error("Exception getMessage(): " + e.getMessage());
				logger.error("Exception StackTrace: ");
		        e.printStackTrace();
			}  
	
				return Optional.of(result);
			
		}
	
	
	public Optional<Integer>  updatePersonEntry(Person person)  {
		
		
		 int result=0;
				try {
					
					
					
						
					//String sql="update HAAD_MEM_UPLOAD_DETAIL set HAAD_PER_UID='"+person.getUnifiedNumber()+"',HAAD_PER_FIRST_NAME_EN='"+person.getFirstNameEn()+"',HAAD_PER_MIDDLE_NAME_EN='"+person.getMiddleNameEn()+"',HAAD_PER_LAST_NAME_EN='"+person.getLastNameEn()+"',"
					//		+ "HAAD_PER_FIRST_NAME_AR='"+person.getFirstNameAr()+"',HAAD_PER_MIDDLE_NAME_AR='"+person.getMiddleNameAr()+"',HAAD_PER_LAST_NAME_AR='"+person.getLastNameAr()+"',HAAD_PER_BIRTH_DATE=TO_DATE('"+person.getBirthDate()+"', 'dd/mm/yyyy'),HAAD_PER_GENDER='"+person.getGender()+"',"
					//		+ "HAAD_PER_CONTACT_NUMBER='"+person.getContactNumber()+"',HAAD_PER_NATIONALITY='"+person.getNationality()+"',HAAD_PER_NATION_CODE='"+person.getNationalityCode()+"',"
					//		+ "HAAD_PER_CITY='"+person.getCity()+"',HAAD_PER_PASS_NO='"+person.getPassportNumber()+"',HAAD_PER_EID='"+person.getEmiratesIDNumber()+"',"
					//		+ "HAAD_PER_SPONSOR_CARNO='"+person.getSponsorNumber()+"',HAAD_PER_SPONSOR_NAME_EN='"+person.getSponsorNameEn()+"',HAAD_PER_SPONSOR_NAME_AR='"+person.getSponsorNameAr()+"',"
					//		+ "HAAD_MEM_RELATION='"+person.getMember().getRelation()+"',HAAD_MEM_RELATION_TO='"+person.getMember().getRelationTo()+"',"
					//		+ "HAAD_UPLOAD_STATUS='"+person.getUploadStatus()+"',HAAD_RESP_MSG='"+person.getUploadResponse()+
					//		"' where HAAD_MEM_CARD_NO='"+person.getMember().getID()+"' and HAAD_POL_NO='"+person.getMember().getContract().getCompanyID()+"' and HAAD_END_SRL_NO='"+person.getEndSrl()+"'";
						
					
					String sql="update HAAD_MEM_UPLOAD_DETAIL set HAAD_PER_UID=?,HAAD_PER_FIRST_NAME_EN=?,HAAD_PER_MIDDLE_NAME_EN=?,HAAD_PER_LAST_NAME_EN=?,"
							+ "HAAD_PER_FIRST_NAME_AR=?,HAAD_PER_MIDDLE_NAME_AR=?,HAAD_PER_LAST_NAME_AR=?,HAAD_PER_BIRTH_DATE=TO_DATE(?, 'dd/mm/yyyy'),HAAD_PER_GENDER=?,"
							+ "HAAD_PER_CONTACT_NUMBER=?,HAAD_PER_NATIONALITY=?,HAAD_PER_NATION_CODE=?,"
							+ "HAAD_PER_CITY=?,HAAD_PER_PASS_NO=?,HAAD_PER_EID=?,"
							+ "HAAD_PER_SPONSOR_CARNO=?,HAAD_PER_SPONSOR_NAME_EN=?,HAAD_PER_SPONSOR_NAME_AR=?,"
							+ "HAAD_MEM_RELATION=?,HAAD_MEM_RELATION_TO=?,"
							+ "HAAD_UPLOAD_STATUS=?,HAAD_RESP_MSG=?"
							+" where HAAD_MEM_CARD_NO=? and HAAD_POL_NO=? and HAAD_END_SRL_NO=?";
					
					
					
				         		
					   logger.info("Sql  ::"+sql);         		
                    
			           result=getJdbcTemplate().update(sql,person.getUnifiedNumber(),person.getFirstNameEn(),person.getMiddleNameEn(),person.getLastNameEn(),
								person.getFirstNameAr(),person.getMiddleNameAr(),person.getLastNameAr(),person.getBirthDate(),person.getGender(),
								person.getContactNumber(),person.getNationality(),person.getNationalityCode(),
								person.getCity(),person.getPassportNumber(),person.getEmiratesIDNumber(),
								person.getSponsorNumber(),person.getSponsorNameEn(),person.getSponsorNameAr(),
								person.getMember().getRelation(),person.getMember().getRelationTo(),
								person.getUploadStatus(),person.getUploadResponse(),
								person.getMember().getID(),person.getMember().getContract().getCompanyID(),person.getEndSrl());						  ;
						  
			           logger.info("Result::"+result); 
			
				}
				catch (DuplicateKeyException e) {
					return Optional.of(9);
				}
				catch (EmptyResultDataAccessException e) {
				
				logger.error("Exception :"+e.getMessage());
				logger.error("Exception toString(): "  + e.toString());
				logger.error("Exception getMessage(): " + e.getMessage());
				logger.error("Exception StackTrace: ");
		        e.printStackTrace();
			

			}catch(Exception e) {
				
				logger.error("Exception :"+e.getMessage());
				logger.error("Exception toString(): "  + e.toString());
				logger.error("Exception getMessage(): " + e.getMessage());
				logger.error("Exception StackTrace: ");
		        e.printStackTrace();
			}  
	
				return Optional.of(result);
			
		}

	
	
	
	
	
}
