/* com.cutty.bravo.core.utils.BravoIdGenerator.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-21 上午02:18:10, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.exception.JDBCExceptionHelper;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerationException;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.mapping.Table;
import org.hibernate.type.Type;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.exception.BizException;
import com.cutty.bravo.core.saas.SaasGroupUtils;
import com.cutty.bravo.core.utils.IsolaterUtils.IsolatedWork;
import com.cutty.bravo.core.web.handler.SaasCodeHandler;

/**
 *
 * <p>
 * <a href="BravoIdGenerator.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 * 
 */
public class BravoIdGenerator implements PersistentIdentifierGenerator, Configurable {
	
	private static final Log log = LogFactory.getLog(BravoIdGenerator.class);

	private String tableName;
	private String columnName;
	private String query;
	private String update;
	private String insert;

	public void configure(Type type, Properties params, Dialect dialect) {
		
		tableName = ConfigurableConstants.getProperty("id.generator.table", "hibernate_unique_key");
		columnName = ConfigurableConstants.getProperty("id.generator.column", "next_hi");
		String schemaName = params.getProperty(SCHEMA);
		String catalogName = params.getProperty(CATALOG);

		if ( tableName.indexOf( '.' )<0 ) {
			tableName = Table.qualify( catalogName, schemaName, tableName );
		}

		insert = "insert into " +
			tableName +
			"(id,"+columnName +") values (?,0)" ;
		
		query = "select " + 
			columnName + 
			" from " + 
			dialect.appendLockHint(LockMode.UPGRADE, tableName) +
			" where id= ? "+
			dialect.getForUpdateString();

		update = "update " + 
			tableName + 
			" set " + 
			columnName + 
			" = ? where " + 
			columnName + 
			" = ? and id= ?";
	}
	
	@Override
	public synchronized Serializable generate(SessionImplementor session, Object object)
		throws HibernateException {
		System.out.println(System.currentTimeMillis());
		long result = ( (Long) doWorkInNewTransaction(session,object) ).longValue();
		System.out.println(System.currentTimeMillis());
		return new Long(result);
	}

	@Override
	public String[] sqlCreateStrings(Dialect dialect) {
		return new String[] {
			dialect.getCreateTableString() + " " + tableName + " ( " + columnName + " " + dialect.getTypeName(Types.INTEGER) + " )",
			"insert into " + tableName + " values ( 0 )"
		};
	}
	@Override
	public String[] sqlDropStrings(Dialect dialect) {
		StringBuffer sqlDropString = new StringBuffer( "drop table " );
		if ( dialect.supportsIfExistsBeforeTableName() ) {
			sqlDropString.append( "if exists " );
		}
		sqlDropString.append( tableName ).append( dialect.getCascadeConstraintsString() );
		if ( dialect.supportsIfExistsAfterTableName() ) {
			sqlDropString.append( " if exists" );
		}
		return new String[] { sqlDropString.toString() };
	}
	@Override
	public Object generatorKey() {
		return tableName;
	}
	
	public Serializable doWorkInNewTransaction(final SessionImplementor session,final  Object object)
	throws HibernateException {
		class Work implements IsolatedWork {
			Serializable generatedValue;
			public void doWork(Connection connection) throws HibernateException {
				String sql = null;
				try {
					generatedValue = doWorkInCurrentTransaction( connection, sql,object );
				}
				catch( SQLException sqle ) {
					throw JDBCExceptionHelper.convert(
							session.getFactory().getSQLExceptionConverter(),
							sqle,
							"could not get or update next value",
							sql
						);
				}
			}
		}
		Connection connection = null;
		Work work = new Work();
		try {
			SaasGroupUtils SaasGroupUtils = (SaasGroupUtils)ApplicationContextKeeper.getAppCtx().getBean("saasGroupUtils");
			connection = SaasGroupUtils.getSaasGroupDBObject(SaasCodeHandler.DEFAULT_SAAS_KEY).getConnection();
			IsolaterUtils.doIsolatedWork( work, connection );
		} catch (BizException e) {
			throw new HibernateException(e);
		} catch (SQLException e) {
			throw JDBCExceptionHelper.convert(
					session.getFactory().getSQLExceptionConverter(),
					e,
					"could not get or update next value",
					""
				);
		} finally {
			if (null != connection)
				try {
					connection.close();
				} catch (SQLException e) {
					throw JDBCExceptionHelper.convert(
							session.getFactory().getSQLExceptionConverter(),
							e,
							"could not close connection for Default saas",
							""
						);
				}
		}
		return work.generatedValue;
	}
	
	public Serializable doWorkInCurrentTransaction(Connection conn, String sql,Object object) throws SQLException {
		long result;
		int rows;
		do {
			// The loop ensures atomicity of the
			// select + update even for no transaction
			// or read committed isolation level
			String objectClassName = object.getClass().getName();
			sql = query;
			//SQL.debug(query);
			PreparedStatement qps = conn.prepareStatement(query);
			PreparedStatement inps = null;
			try {
				qps.setString(1,objectClassName);
				ResultSet rs = qps.executeQuery();
				boolean hasResult = rs.next();
				if ( !hasResult ) {
					inps = conn.prepareStatement(insert);
					inps.setString(1,objectClassName);
					inps.execute();
					rs = qps.executeQuery();
					hasResult = rs.next();
				}
				if ( !hasResult) {
					String err = "could not read a hi value - you need to populate the table: " + tableName;
					log.error(err);
					throw new IdentifierGenerationException(err);
				}
				result = rs.getLong(1);
				rs.close();
			}
			catch (SQLException sqle) {
				log.error("could not read a hi value", sqle);
				throw sqle;
			}
			finally {
				if (null != inps){
					inps.close();
				}
				qps.close();
			}

			sql = update;
			//SQL.debug(update);
			PreparedStatement ups = conn.prepareStatement(update);
			try {
				ups.setLong( 1, result + 1 );
				ups.setLong( 2, result );
				ups.setString( 3, objectClassName);
				rows = ups.executeUpdate();
			}
			catch (SQLException sqle) {
				log.error("could not update hi value in: " + tableName, sqle);
				throw sqle;
			}
			finally {
				ups.close();
			}
		}
		while (rows==0);
		return new Long(result);
	}
}
