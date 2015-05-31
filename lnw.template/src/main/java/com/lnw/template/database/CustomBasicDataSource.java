package com.lnw.template.database;

import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class CustomBasicDataSource extends BasicDataSource {
	
	public CustomBasicDataSource(){
		
	}

	public void setUsername(String username) {
		super.setUsername(username);
	}
	
	public void setPassword(String password) {
		super.setPassword(password);
    }
	
	public void setDriverClassName(String driverClassName){
		super.setDriverClassName(driverClassName);
	}
	
	public void setUrl(String url){
		super.setUrl(url);
	}
	
	public void setInitialSize(int initialSize){
		super.setInitialSize(initialSize);
	}
	
	public void setMaxIdle(int maxIdle){
		super.setMaxIdle(maxIdle);
	}
	
	public void setMaxActive(int maxActive){
		super.setMaxActive(maxActive);
	}
	
	public void setPoolPreparedStatements(boolean poolingStatements){
		super.setPoolPreparedStatements(poolingStatements);
	}

	public void close() throws SQLException{
		super.close();
	}
	
}
