/* com.cutty.bravo.components.jvm.ReferenceCountingGC.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2012-9-8 下午2:40:16, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jvm;

/**
 *
 * <p>
 * <a href="ReferenceCountingGC.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class ReferenceCountingGC {

	public Object instance = null;
	
	private static final int _1MB = 1024*1024;
	
	private byte[] byteSize = new byte[2*_1MB];
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ReferenceCountingGC objectA = new ReferenceCountingGC();
		ReferenceCountingGC objectB = new ReferenceCountingGC();
		objectA.instance = objectB;
		objectB.instance = objectA;
		
		objectA = null;
		objectB = null;
		System.out.println("Start GC :==========="+System.currentTimeMillis());
		System.gc();
		System.out.println();
		System.out.println("End   GC :==========="+System.currentTimeMillis());

	}

}
