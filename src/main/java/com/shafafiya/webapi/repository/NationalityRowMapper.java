package com.shafafiya.webapi.repository;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.shafafiya.webapi.entity.Nationality;


public class NationalityRowMapper implements RowMapper<Nationality> {

	@Override
	public Nationality mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		// TODO Auto-generated method stub
		
		Nationality nationality = new Nationality();
		nationality.setNationCode(rs.getString("AUTHCM_MOI_CODE"));
		nationality.setNationValue(rs.getString("AUTHCM_CODE_DESC"));
		return nationality;
	}
	
	

}
