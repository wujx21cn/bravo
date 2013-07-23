/* com.cutty.bravo.core.utils.IsolaterUtils.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2011-9-19 上午10:39:33, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2011 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cutty.bravo.core.exception.BizException;

/**
 *
 * <p>
 * <a href="IsolaterUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class IsolaterUtils {

	protected static final Log logger = LogFactory.getLog(IsolaterUtils.class);

	public static void doIsolatedWork(IsolatedWork work,Connection connection) throws BizException {
			new JdbcDelegate( connection ).delegateWork( work, true );
	}

	public static void doNonTransactedWork(IsolatedWork work,Connection connection) throws BizException {
			new JdbcDelegate( connection ).delegateWork( work, false );
	}


	private static interface Delegate {
		public void delegateWork(IsolatedWork work, boolean transacted) throws BizException;
	}


	/**
	 * An isolation delegate for JDBC-based transactions.  Basically just
	 * grabs a new connection and does the work on that.
	 */
	public static class JdbcDelegate implements Delegate {
		private final Connection connection;

		public JdbcDelegate(Connection connection) {
			this.connection = connection;
		}

	public void delegateWork(IsolatedWork work, boolean transacted) throws BizException {
		boolean wasAutoCommit = false;
		try {
			if ( transacted ) {
				if ( connection.getAutoCommit() ) {
					wasAutoCommit = true;
					connection.setAutoCommit( false );
				}
			}
			work.doWork( connection );
			if ( transacted ) {
				connection.commit();
			}
		} catch( Exception e ) {
			try {
				if ( transacted && !connection.isClosed() ) {
					connection.rollback();
				}
			}
			catch( Exception ignore ) {
				logger.error( "unable to rollback connection on exception [" + ignore + "]" );
			} if ( e instanceof SQLException ) {
			}
			else {
				throw new BizException( "error performing isolated work");
			}
		}
		finally {
			if ( transacted && wasAutoCommit ) {
				try {
					connection.setAutoCommit( true );
				}
				catch( Exception ignore ) {
					logger.error( "was unable to reset connection back to auto-commit" );
				}
			}
			try {
				connection.close();
			}
			catch ( Exception ignore ) {
				logger.error( "Unable to release isolated connection [" + ignore + "]" );
			}
		}
	}
	}
	public interface IsolatedWork {
		/**
		 * Perform the actual work to be done.
		 *
		 * @param connection The JDBC connection to use.
		 * @throws HibernateException
		 */
		public void doWork(Connection connection) throws BizException;
	}

}
