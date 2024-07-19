package com.shafafiya.webapi.repository;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.shafafiya.webapi.entity.Policy;

public class PolicyRowMapper implements RowMapper<Policy> {

	@Override
	public Policy mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		Policy policy = new Policy();
		policy.setCardNo(rs.getString("PMBRH_MED_CARD_NO"));
		policy.setEidNo(rs.getString("PMBRH_CIVIL_ID"));
		policy.setEndName(rs.getString("ENDT_NAME"));
		policy.setEntryDate(rs.getString("PMBRH_ENTRY_DT"));
		policy.setMemName(rs.getString("FULL_NAME"));
		policy.setMemType(rs.getString("MEMBER_TYPE"));
		policy.setPolNo(rs.getString("POLH_NO"));
		policy.setRelation(rs.getString("RELATION"));
		policy.setVisaRegion(rs.getString("VISA_REGION"));
		policy.setExitDate(rs.getString("PMBRH_EXIT_DT"));
		policy.setValidStatus(rs.getString("PMBRH_AUTH_VERIFY_STATUS"));
		policy.setUploadStatus(rs.getString("PMBRH_AUTH_UPLOAD_STATUS"));
		policy.setEndNo(rs.getString("ECM_ENDT_TYPE"));
		policy.setEndSrl(rs.getString("PMBRH_ENDT_SRL"));
		return policy;
	}
	
	

}
