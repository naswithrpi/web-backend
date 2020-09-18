package com.nasrpi.home;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ActivityRowMapper implements RowMapper<UserActivityModel>{

	@Override
	public UserActivityModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		UserActivityModel activityModel = new UserActivityModel();
		
		activityModel.setActivity(rs.getString("activity"));
		activityModel.setUsername(rs.getString("username"));
		
		return activityModel;
	}

	
}
