package com.shafafiya.webapi.repository;


import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.shafafiya.webapi.entity.Nationality;
import com.shafafiya.webapi.entity.Person;
import com.shafafiya.webapi.entity.Policy;

import jakarta.annotation.PostConstruct;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Repository
public class PolDetailsRepo extends JdbcDaoSupport {
	
	
	 private static final Logger logger = LogManager.getLogger(PolDetailsRepo.class);

	
	@Autowired 
	@Qualifier("datasourcecore")
	DataSource dataSource;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}
	
	public List<Nationality> getNationality(){
		logger.info("Entering PolDetailsRepo getPolicyDetails");
		
		String sql="SELECT   AUTHCM_MOI_CODE,AUTHCM_CODE_DESC"
				+ " FROM   MIM_AUTH_CODES_MAPPING"
				+ " WHERE   AUTHCM_AUTH_CODE = 'HAAD'"
				+ " AND AUTHCM_TYPE = 'NATIONALITY'"
				+ " AND AUTHCM_ACTIVE_YN = 'Y'";
		
		
		
		try {
			return getJdbcTemplate().query(sql, new NationalityRowMapper());
		
			}catch(Exception e ){
				logger.error("Exception in PolDetailsRepo getPolicyDetails"+e.getMessage());
				e.printStackTrace();
				return null;
				
			}	
		
	}
	
	
	
	public List<Policy> getPolicyDetails(String CardNo,String EID,String fromDate,String toDate){
		
		
		logger.info("Entering PolDetailsRepo getPolicyDetails");
		
			
		String sql="SELECT POLH_NO,(PMBRH_FIRST_NAME || PMBRH_MIDDLE_NAME || PMBRH_LAST_NAME) AS FULL_NAME ,LI_DESC AS MEMBER_TYPE,CC_DESC AS VISA_REGION,PMBRH_ENDT_SRL,"
				//+ "ECM_ENDT_TYPE,"
				//+ " CASE"
				//+ "    WHEN PMBRH_ENDT_SRL <> 0  THEN"
				//+ "      (ECM_ENDT_TYPE||' - '||ECM_NAME )"
				//+ "    ELSE"
				//+ "      (ECM_ENDT_TYPE||' - Original Policy')"
				//+ "  END "
				//+ " AS ENDT_NAME,"	
				
				+ " CASE    WHEN PMBRH_ENDT_SRL <> 0  THEN      (ECM_ENDT_TYPE)    ELSE      ('0')  END  AS ECM_ENDT_TYPE, "
				+ " CASE    WHEN PMBRH_ENDT_SRL <> 0  THEN      (ECM_ENDT_TYPE||' - '||ECM_NAME )    ELSE      (PMBRH_ENDT_SRL||' - Original Policy')  END  AS ENDT_NAME, "
				+ " CASE"
				+ " WHEN PMBRH_PARENT_UID IS NULL"
				+ " THEN"
				+ " 'Principal'"
				+ " ELSE"
				+ " DECODE (PMBRH_RELATION,"
				+ " 'PR', 'Principal',"
				+ " 'SP', 'Spouse',"
				+ " 'CH', 'Child',"
				+ " 'Principal')"
				+ " END"
				+ " RELATION,"
				
				//+ " PTY_NAME AS ASSURED_NAME,"
				+ "PMBRH_CIVIL_ID,PMBRH_MED_CARD_NO,to_char(PMBRH_ENTRY_DT,'dd/mm/yyyy') as PMBRH_ENTRY_DT,to_char(PMBRH_EXIT_DT,'dd/mm/yyyy') as PMBRH_EXIT_DT,"
			    + " PMBRH_AUTH_VERIFY_STATUS,"
			    + " CASE"
			    + " WHEN PMBRH_AUTH_UPLOAD_STATUS <>'A'"
                        
                + " THEN"
                + " '0'"
                + " ELSE"
                + " '1'"
                + " END  PMBRH_AUTH_UPLOAD_STATUS"
				+ " FROM MIH_POL_MEMBER INNER JOIN MIH_POLICY"
				+ " ON POLH_UID=PMBRH_POL_UID AND POLH_ENDT_SRL=PMBRH_ENDT_SRL"
				//+ " INNER JOIN GIM_PARTY"
				//+ " ON PMBRH_ASSR_CODE=PTY_CODE"
				+ " LEFT JOIN MIM_ENDT_CONFIG"
				+ " ON ECM_ENDT_TYPE=POLH_ENDT_TYPE"
				+ " INNER JOIN STD_LIST_ITEM"
				+ " ON LI_CODE = PMBRH_TYPE"
				+ " INNER JOIN STD_CODES "
				+ " ON CC_CODE = PMBRH_VISA_REGION"
				+ " WHERE   LI_TYPE = 'MEMBER_TYPE' AND CC_TYPE = 'PROVINCE'  ";
			
				if((CardNo!=null) && !(CardNo.equals(null)) && !(CardNo.trim().equals("")))
					sql=sql+ " AND PMBRH_MED_CARD_NO='"+CardNo+"'";
				if((EID!=null) && !(EID.equals(null)) && !(EID.trim().equals("")))
					sql=sql+ " AND PMBRH_CIVIL_ID='"+EID+"'";
				
				if((fromDate!=null) && !(fromDate.equals(null)) && !(fromDate.trim().equals("")) && (toDate!=null) && !(toDate.equals(null)) && !(toDate.trim().equals(""))  )
					sql=sql+ " AND ( PMBRH_ENTRY_DT <= to_date('"+fromDate+"','DD/MM/YYYY') OR PMBRH_EXIT_DT >= to_date('"+toDate+"','DD/MM/YYYY') )";
				else {
				if((fromDate!=null) && !(fromDate.equals(null)) && !(fromDate.trim().equals("")))
					sql=sql+ " AND  PMBRH_ENTRY_DT <= to_date('"+fromDate+"','DD/MM/YYYY')";
				if((toDate!=null) && !(toDate.equals(null)) && !(toDate.trim().equals("")))
					sql=sql+ " AND PMBRH_EXIT_DT >= to_date('"+toDate+"','DD/MM/YYYY')";
				}
				
				sql=sql+ " order by PMBRH_ENTRY_DT desc,PMBRH_ENDT_SRL desc";
		        logger.info("Info message  QUERY : "+sql);
		        
		
		try {
		return getJdbcTemplate().query(sql, new PolicyRowMapper());
	
		}catch(Exception e ){
			logger.error("Exception in PolDetailsRepo getPolicyDetails"+e.getMessage());
			e.printStackTrace();
			return null;
			
		}	
	}
	
	

	
	public Person getMemberDetails(String CardNo,String polNo,String endNo){
		
		
		logger.info("Entering PolDetailsRepo getPolicyDetails");
		
				
		String sql=   "SELECT  'NA' as HAAD_UPLOAD_STATUS, 'NA' as HAAD_RESP_MSG, PMBRH_UID,"
				+ "  PMBRH_ENDT_SRL,"
				+ "   UID_NUMBER,"
				+ "              CONTACT_NO,"
				+ "   FIRST_NAME,"
				+ "   MIDDLE_NAME,"
				+ "   LAST_NAME,"
				+ "   FULL_NAME,"
				+ "   FIRST_NAME_L2,"
				+ "   MIDDLE_NAME_L2,"
				+ "   LAST_NAME_L2,"
				+ "   FULL_NAME_L2,"
				+ "   MOBILE_NO,"
				+ "   DOB,"
				+ "   BIRTH_DATE,"
				+ "   GENDER,"
				+ "   NATIONALITY,"
				+ "   NATIONALITY_CODE,"
				+ "   CITY,"
				+ "   PASSPORT_NO,"
				+ "   EMIRATES_ID,"
				+ "   MEMBER_ID,"
				+ "   RELATION,"
				+ "   RELATION_ORDER,"
				+ "   RELATION_TO,"
				+ "   PACKAGE_NAME,"
				+ "   START_DATE,"
				+ "   EXPIRY_DATE,"
				+ "   CASE"
				+ "   WHEN CONTRACT_STATUS = 'New' THEN START_DATE"
	            + "   ELSE RENEWAL_DATE"
	            		+ "   END"
	            		+ "   RENEWAL_DATE,"
	              + "   GROSS_PREMIUM,"
	              + "  POLICY_HOLDER,"
	              + "  PREMIUM,"
	              + "  VAT_AMOUNT,"
	              + "  VAT_PERCENT,"
	              + "  COMPANY_ID,"
	              + "  PRINCIPAL_UPLOAD_STS,"
	              + "  PMBRH_PARENT_UID,"
	              + "  CONTRACT_STATUS,"
	              + "  SPONSOR_NO,"
	              + "  SPONSOR_NAME,"
	              + "  SPONSOR_NAME_L2,"
	              + "  PMBRH_REC_TYPE"
	       + "  FROM   (SELECT   A.*,"
	                        + "  CASE"
	                          + "   WHEN UPPER (PMBRH_AUTH_UPLOAD_REMARKS) LIKE"
	                             + "        '%TUPXX%'"
	                              + "    OR UPPER (PMBRH_AUTH_UPLOAD_REMARKS) LIKE"
	                              + "       '%STARTDATE MUST%'"
	                              + "    OR UPPER (PMBRH_AUTH_UPLOAD_REMARKS) LIKE"
	                                + "       '%RENEWALDATE MUST%'"
	                           + "  THEN"
	                            + "    NVL ("
	                              + "     DFUN_GET_MBR_START_DATE (PMBRH_MED_CARD_NO,"
	                                                         + "   PMBRH_CIVIL_ID,"
	                                                         + "   PMBRH_UNIQUE_ID),"
	                                 + "  TO_CHAR (PMBRH_ENTRY_DT, 'DD/MM/RRRR')"
	                              + "  )"
	                           + "  ELSE"
	                            + "    TO_CHAR (PMBRH_ENTRY_DT, 'DD/MM/RRRR')"
	                       + "   END"
	                        + "     START_DATE,"
	                       + "   CASE"
	                        + "     WHEN PMBRH_REC_TYPE = 'D'"
	                         + "    THEN"
	                          + "      'Cancelled'"
	                          + "   ELSE"
	                           + "     CASE"
	                            + "       WHEN UPPER (PMBRH_AUTH_UPLOAD_REMARKS) LIKE"
	                                   + "        '%TUPXX%'"
	                                    + "    OR UPPER (PMBRH_AUTH_UPLOAD_REMARKS) LIKE"
	                                     + "        '%STARTDATE MUST%'"
	                                     + "   OR UPPER (PMBRH_AUTH_UPLOAD_REMARKS) LIKE"
	                                       + "      '%RENEWALDATE MUST%'"
	                                 + "  THEN"
	                                 + "     'Renewed'"
	                                 + "  ELSE"
	                                  + "    DFUN_GET_MBR_CONTRACT_STATUS ("
	                                   + "      POLH_UID,"
	                                    + "     PMBRH_UID,"
	                                      + "   PMBRH_ENDT_SRL,"
	                                      + "   PMBRH_MED_CARD_NO,"
	                                      + "   PMBRH_CIVIL_ID,"
	                                      + "   PMBRH_ENTRY_DT,"
	                                      + "   PMBRH_EXIT_DT,"
	                                      + "   PMBRH_REC_TYPE"
	                                   + "   )"
	                              + "  END"
	                       + "   END"
	                         + "    CONTRACT_STATUS"
	                 + "  FROM   (SELECT   PMBRH_UID,"
	                                + "    PMBRH_ENDT_SRL,"
	                                + "    REPLACE (PMBRH_UNIQUE_ID, '-', '') UID_NUMBER,"
	                                 + "   TRIM (PMBRH_FIRST_NAME) FIRST_NAME,"
	                                 + "   TRIM (PMBRH_MIDDLE_NAME) MIDDLE_NAME,"
	                                 + "   TRIM (PMBRH_LAST_NAME) LAST_NAME,"
				+ "                                  NVL (PMBRH_PHONE, 'NA') CONTACT_NO,"
	                                 + "   PMBRH_REC_TYPE,"
	                                 + "   PMBRH_FULL_NAME FULL_NAME,"
	                                 + "   PMBRH_FIRST_NAME_L2 FIRST_NAME_L2,"
	                                 + "   PMBRH_MIDDLE_NAME_L2 MIDDLE_NAME_L2,"
	                                 + "   PMBRH_LAST_NAME_L2 LAST_NAME_L2,"
	                                 + "   NULL FULL_NAME_L2,"
	                                 + "   PMBRH_CIVIL_ID,"
	                                 + "   PMBRH_EXIT_DT,"
	                                 + "   PMBRH_ENTRY_DT,"
	                                 + "   PMBRH_MED_CARD_NO,"
	                                 + "   POLH_UID,"
	                                 + "   PMBRH_UNIQUE_ID,"
	                                 + "   DFUN_GET_FORMAT_MOB_NUMBER (PMBRH_MOBILE)"
	                                  + "     MOBILE_NO,"
	                                  + "  TO_CHAR (PMBRH_DOB, 'DD/MM/YYYY') DOB,"
	                                  + "  TO_CHAR (PMBRH_DOB, 'YYYY-MM-DD') BIRTH_DATE,"
	                                  + "  DECODE (PMBRH_GENDER,"
	                                   + "         'M', '1',"
	                                   + "         'F', '0',"
	                                   + "         '9')"
	                                    + "   GENDER,"
	                                  + "  NVL ("
	                                   + "    (SELECT   AUTHCM_CODE_DESC"
	                                    + "      FROM   MIM_AUTH_CODES_MAPPING"
	                                     + "    WHERE   AUTHCM_AUTH_CODE = 'HAAD'"
	                                        + "         AND AUTHCM_TYPE = 'NATIONALITY'"
	                                         + "        AND AUTHCM_SYS_CODE ="
	                                                + "       CASE"
	                                                  + "        WHEN PMBRH_NATION ="
	                                                   + "               'PSE'"
	                                                    + "      THEN"
	                                                     + "        'PLS'"
	                                                      + "    ELSE"
	                                                       + "      PMBRH_NATION"
	                                                     + "  END"
	                                               + "  AND AUTHCM_ACTIVE_YN = 'Y'),"
	                                    + "   PMBRH_NATION"
	                                 + "   )"
	                                   + "    NATIONALITY,"
	                                  + "  (SELECT   AUTHCM_MOI_CODE"
	                                   + "    FROM   MIM_AUTH_CODES_MAPPING"
	                                   + "   WHERE   AUTHCM_AUTH_CODE = 'HAAD'"
	                                         + "     AND AUTHCM_TYPE = 'NATIONALITY'"
	                                          + "    AND AUTHCM_SYS_CODE ="
	                                            + "        CASE"
	                                                  + "     WHEN PMBRH_NATION = 'PSE'"
	                                                  + "     THEN"
	                                                   + "       'PLS'"
	                                                   + "    ELSE"
	                                                     + "     PMBRH_NATION"
	                                                 + "   END"
	                                            + "  AND AUTHCM_ACTIVE_YN = 'Y')"
	                                     + "  NATIONALITY_CODE,"
	                                  + "  'ABU DHABI' CITY,"
	                                 + "   NVL (PMBRH_PASSPORT_NO, 'NA') PASSPORT_NO,"
	                                 + "   DFUN_GET_FORMAT_EMIRATE_ID (PMBRH_CIVIL_ID)"
	                                  + "     EMIRATES_ID,"
	                                 + "   PMBRH_MED_CARD_NO MEMBER_ID,"
	                                 + "   CASE"
	                                  + "     WHEN PMBRH_PARENT_UID IS NULL"
	                                   + "    THEN"
	                                    + "      'Principal'"
	                                    + "   ELSE"
	                                     + "     DECODE (PMBRH_RELATION,"
	                                            + "      'PR', 'Principal',"
	                                             + "     'SP', 'Spouse',"
	                                              + "    'CH', 'Child',"
	                                               + "   'Principal')"
	                                 + "   END"
	                                  + "     RELATION,"
	                                  + "  CASE"
	                                    + "   WHEN PMBRH_PARENT_UID IS NULL"
	                                    + "   THEN"
	                                      + "    '1'"
	                                     + "  ELSE"
	                                      + "    DECODE (PMBRH_RELATION,"
	                                           + "       'PR', '1',"
	                                            + "      'SP', '2',"
	                                             + "     'CH', '3',"
	                                              + "    '1')"
	                                 + "   END"
	                                  + "     RELATION_ORDER,"
	                                  + "  NVL ("
	                                   + "    (SELECT   B.PMBRH_MED_CARD_NO"
	                                    + "      FROM   MIH_POL_MEMBER B"
	                                     + "    WHERE   B.PMBRH_RELATION = 'PR'"
	                                        + "         AND B.PMBRH_REC_TYPE = 'A'"
	                                           + "      AND B.PMBRH_UID ="
	                                               + "        A.PMBRH_PARENT_UID),"
	                                     + "  A.PMBRH_MED_CARD_NO"
	                                  + "  )"
	                                   + "    RELATION_TO,"
	                                  + "  CASE"
	                                    + "   WHEN PMBRH_RELATION = 'PR'"
	                                     + "  THEN"
	                                      + "    'A'"
	                                     + "  ELSE"
	                                      + "    CASE"
	                                        + "     WHEN (SELECT   COUNT ( * )"
	                                           + "          FROM   MIH_POL_MEMBER B"
	                                             + "       WHERE   B.PMBRH_POL_UID ="
	                                                + "               A.PMBRH_POL_UID"
	                                                  + "          AND B.PMBRH_RELATION ="
	                                                    + "              'PR'"
	                                                       + "     AND B.PMBRH_UID ="
	                                                        + "          A.PMBRH_PARENT_UID"
	                                                         + "   AND PMBRH_AUTH_UPLOAD_STATUS ="
	                                                          + "        'A') > 0"
	                                          + "   THEN"
	                                           + "     'A'"
	                                          + "   ELSE"
	                                           + "     'P'"
	                                       + "   END"
	                                 + "   END"
	                                  + "     PRINCIPAL_UPLOAD_STS,"
	                                 + "   PMBRH_PARENT_UID,"
	                                 + "   (SELECT   PCATG_AUTH_APPR_CODE"
	                                  + "     FROM   MIT_POL_CATEGORY"
	                                  + "    WHERE   PCATG_POL_UID = POLH_UID"
	                                     + "         AND PCATG_CODE = PMBRH_MBRCATG_CODE)"
	                                   + "    PACKAGE_NAME,"
	                                  + "  TO_CHAR (PMBRH_EXIT_DT, 'DD/MM/RRRR')"
	                                   + "    EXPIRY_DATE,"
	                                  + "  TO_CHAR (PMBRH_EXIT_DT, 'DD/MM/RRRR')"
	                                  + "     RENEWAL_DATE,"
	                                  + "  CASE"
	                                   + "    WHEN NVL (PMBRH_LC_PREM, 0) < 600 THEN 600"
	                                   + "    ELSE NVL (PMBRH_LC_PREM, 0)"
	                                  + "  END"
	                                  + "     GROSS_PREMIUM,"
	                                  + "  6 POLICY_HOLDER,"
	                                 + "   NVL (PMBRH_LC_PREM, 0)"
	                                 + "   + NVL (PMBRH_PREM_LC_VAT, 0)"
	                                  + "     PREMIUM,"
	                                  + "  NVL (PMBRH_PREM_LC_VAT, 0) VAT_AMOUNT,"
	                                  + "  DECODE (NVL (PMBRH_PREM_LC_VAT, 0), 0, 0, 5)"
	                                   + "    VAT_PERCENT,"
	                                  + "  POLH_NO COMPANY_ID,"
	                                //  --'New' CONTRACT_STATUS,
	                                  + "   PMBRH_SPONSOR_NO SPONSOR_NO,"
	                                 + "   PMBRH_SPONSOR_NAME SPONSOR_NAME,"
	                                 + "   PMBRH_SPONSOR_NAME_L2 SPONSOR_NAME_L2,"
	                                 + " PMBRH_AUTH_UPLOAD_REMARKS"
	                           + "  FROM   MIH_POLICY,"
	                               + "   (SELECT   A.*"
	                                  + "   FROM   MIH_POL_MEMBER A, MIH_POLICY B"
	                                   + " WHERE   B.POLH_UID = PMBRH_POL_UID"
	                                        + "    AND B.POLH_ENDT_SRL ="
	                                              + "    PMBRH_ENDT_SRL"
	                                           + " AND A.PMBRH_VISA_REGION = '01'"
	                                           // --AND A.PMBRH_REC_TYPE IN ('A')
	                                           // --AND PMBRH_AUTH_UPLOAD_STATUS = 'V'
	                                           // --AND ROWNUM < 501
	                                           // --AND PMBRH_MED_CARD_NO = '90BBEB7C10CE6DCD'
	                                           //+ " AND POLH_FM_DT >"
	                                               // + "  TO_DATE ('30/11/2022',"
	                                                     // + "     'DD/MM/RRRR')"
	                                                      + ") A"
	                         + " WHERE   "
	                         //+ "POLH_FM_DT >"
	                                  // + "  TO_DATE ('30/11/2022', 'DD/MM/RRRR') AND"
	                                 + " POLH_UID = PMBRH_POL_UID"
	                                  + " AND POLH_ENDT_SRL = PMBRH_ENDT_SRL and PMBRH_MED_CARD_NO='"+CardNo+"' and POLH_NO='"+polNo+"' and PMBRH_ENDT_SRL='"+endNo+"') A)"
	   + " ORDER BY   RELATION_ORDER, MEMBER_ID";
				

		
		
			
				
		
				
		        logger.info("Info message  QUERY : "+sql);
		        
		
		try {
		return (Person) getJdbcTemplate().queryForObject(sql, new MemberRowMapper ());
	
		}catch(Exception e ){
			logger.error("Exception in PolDetailsRepo getPolicyDetails"+e.getMessage());
			e.printStackTrace();
			return null;
			
		}	
	}

	
	


	
	
	
	
	
}
