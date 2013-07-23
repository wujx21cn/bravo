package com.cutty.bravo.core.saas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.domain.SaasGroup;
import com.cutty.bravo.core.web.handler.SaasCodeHandler;

public class SaasGroupUtils implements BeanFactoryAware,InitializingBean{

	protected final static Log logger = LogFactory.getLog(SaasGroupUtils.class);
	private static Map<String, SaasGroup> saasGroups = new HashMap<String, SaasGroup>();
	private static String driver = ConfigurableConstants.getProperty("jdbc.driverClassName","false");
	private static String dbURL =  ConfigurableConstants.getProperty("jdbc.url","false");
	private static String username =  ConfigurableConstants.getProperty("jdbc.username","false");
	private static String password =  ConfigurableConstants.getProperty("jdbc.password","false");
	
	private DefaultListableBeanFactory beanFactory;	
	@Override
	public void afterPropertiesSet() throws Exception {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		SaasGroup saasGroup = new SaasGroup();
		saasGroup.setCode(SaasCodeHandler.DEFAULT_SAAS_KEY);
		saasGroup.setName("非saas状态下地默认数据源");
		//该字段目前没用，随便给一个值，考虑去掉
		saasGroup.setDatabaseName("BRAVO");
		saasGroup.setDriverClassName(driver);
		saasGroup.setDatabaseUrl(dbURL);
		saasGroup.setUsername(username);
		saasGroup.setPassword(password);
		regiestSaasGroupDBObject(saasGroup);
		saasGroups.put(saasGroup.getCode(), saasGroup);
	   try {
		   //从数据库获取
			String saasGroupQuery = "select * from bravo_saas_group";
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(saasGroupQuery);
			while (rs.next()) { 
			 saasGroup = new SaasGroup();
			 saasGroup.setCode(rs.getString("code"));
			 saasGroup.setName(rs.getString("name"));
			 saasGroup.setDatabaseName(rs.getString("database_name"));
			 saasGroup.setDriverClassName(rs.getString("driver_className"));
			 saasGroup.setDatabaseUrl(rs.getString("database_url"));
			 saasGroup.setUsername(rs.getString("database_username"));
			 saasGroup.setPassword(rs.getString("database_password"));
			 regiestSaasGroupDBObject(saasGroup);
			 saasGroups.put(saasGroup.getCode(), saasGroup);
		   }
		} catch (SQLException e) {
			logger.error(e);
		} finally {
				try {
					if (null != rs) rs.close();
					if (null != stmt)stmt.close();
					if (null != con) con.close();
				} catch (SQLException e) {
					logger.error(e);
				}
		}
	}
	
	private Connection getConnection() throws SQLException {
		try {
			Class.forName(driver);
			return DriverManager.getConnection(dbURL, username, password);
		} catch (Throwable t) {
			throw new SQLException(t.getMessage());
		}
	}
	
	public Map<String, SaasGroup> getSaasGroups(){
		return saasGroups;
	}
	public Map<String, SaasGroup> loadSaasGroupDBConfig(){
		return saasGroups;
	}
	
	public SaasGroup getSaasGroupByCode(String code){
		if(null!= saasGroups.get(code))return  saasGroups.get(code);
		String saasGroupQuery = "select * from bravo_saas_group where code='"+code.toUpperCase()+"'";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		  try {
				con = getConnection();
				stmt = con.createStatement();
				rs = stmt.executeQuery(saasGroupQuery);
				while (rs.next()) { 
				 SaasGroup saasGroup = new SaasGroup();
				 saasGroup.setCode(rs.getString("code"));
				 saasGroup.setName(rs.getString("name"));
				 saasGroup.setDatabaseName(rs.getString("database_name"));
				 saasGroup.setDriverClassName(rs.getString("driver_className"));
				 saasGroup.setDatabaseUrl(rs.getString("database_url"));
				 saasGroup.setUsername(rs.getString("database_username"));
				 saasGroup.setPassword(rs.getString("database_password"));
				 saasGroups.put(saasGroup.getCode(), saasGroup);
			   }
			} catch (SQLException e) {
				logger.error(e);
			} finally {
					try {
						if (null != rs) rs.close();
						if (null != stmt)stmt.close();
						if (null != con) con.close();
					} catch (SQLException e) {
						logger.error(e);
					}
			}
		return saasGroups.get(code);
	}
	
	public DataSource getSaasGroupDBObject(String code){
		DataSource ds = (DataSource)beanFactory.getBean(code+"_DATABASE");
		return ds;
	}
	
	public void addSaasGroupDBObject(SaasGroup saasGroup){
		Connection con = null;
		Statement stmt = null;
		String insertSql = "INSERT INTO bravo_saas_group (code,name,database_name,name,driver_className," +
				"database_url,database_username,database_password) VALUES ('" +
				""+saasGroup.getCode()+"', '"+saasGroup.getName()+"', '"+saasGroup.getDatabaseName()+"', '"+saasGroup.getDriverClassName()
				+"', '"+saasGroup.getDatabaseUrl()+"', '"+saasGroup.getUsername()+"', '"+saasGroup.getPassword()+"') ";
	   try {
		   stmt = con.createStatement();
		   stmt.executeUpdate(insertSql);
		   saasGroups.put(saasGroup.getCode(), saasGroup);
		   regiestSaasGroupDBObject(saasGroup);
		} catch (SQLException e) {
			logger.error(e);
		} finally {
				try {
					if (null != stmt)stmt.close();
					if (null != con) con.close();
				} catch (SQLException e) {
					logger.error(e);
				}
		}
		
		
	}
	public void regiestSaasGroupDBObject(SaasGroup saasGroup){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(saasGroup.getDriverClassName());
		ds.setUsername(saasGroup.getUsername());
		ds.setPassword(saasGroup.getPassword());
		ds.setUrl(saasGroup.getDatabaseUrl());	
		beanFactory.registerSingleton(saasGroup.getCode()+"_DATABASE", ds);
	}
	
	
	public static void main(String[] args){
		System.out.println("c:\\aaa\\xxx\\ccc".indexOf("\\"));
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (DefaultListableBeanFactory)beanFactory;
		
	}


}
