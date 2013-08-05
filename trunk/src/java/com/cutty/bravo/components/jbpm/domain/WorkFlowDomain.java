/* com.cutty.bravo.components.jbpm.domain.WorkFlowDomain.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-11-12 下午03:35:29, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.domain;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * <p>
 * <a href="WorkFlowDomain.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
@MappedSuperclass
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")   
public abstract class WorkFlowDomain extends WorkFlowBaseDomain{
	private static final long serialVersionUID = -8423096459309686819L;

}
