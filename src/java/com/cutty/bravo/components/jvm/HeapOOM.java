/* com.cutty.bravo.components.jvm.HeapOOM.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2012-9-8 上午11:36:40, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <p>
 * <a href="HeapOOM.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class HeapOOM {
	static class OOM0bject {}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<OOM0bject> list = new ArrayList<OOM0bject>();
		while (true){
			list.add(new OOM0bject());
			
		}
	}

}
