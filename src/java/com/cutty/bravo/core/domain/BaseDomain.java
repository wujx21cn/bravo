/* com.cutty.bravo.core.web.domain.BaseDomain.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-21 上午02:05:49, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;



/**
 *
 * <p>
 * <a href="BaseDomain.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@MappedSuperclass
public abstract class BaseDomain implements Serializable{
	private static final long serialVersionUID = 1273582655950226825L;
	//定义主键
	private Long id;
	

	@Id
	@GeneratedValue(generator = "Bravo-Generator")
    @GenericGenerator(name = "Bravo-Generator", 
    					strategy = "com.cutty.bravo.core.utils.BravoIdGenerator"
    )
	@Column(name="id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	
}
