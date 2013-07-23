/* com.cutty.bravo.core.Hello.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2010-12-14 下午01:41:48, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 cutty Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * <p>
 * <a href="Hello.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class Hello {
	public Hello() {
		super() ;
		}

		public static void main(String[] args) {
		String packageName = "javax/servlet/";
		URL url = Thread.currentThread().getContextClassLoader().getResource(packageName) ;
		String protocol = url.getProtocol() ;
		try {
		if (protocol.equals("jar")) {
		JarURLConnection con = (JarURLConnection) url.openConnection() ;
		JarFile file = con.getJarFile() ;
		Enumeration enu = file.entries() ;
		String className = "" ;
		String entryName = "" ;
		while ( enu.hasMoreElements() ) {
		JarEntry element = (JarEntry) enu.nextElement() ;
		entryName = element.getName() ;
		className = entryName.substring(entryName.lastIndexOf("/") + 1) ;
		if (!className.equals("") && entryName.equals(packageName + className)) {
		System.out.println("className = "+ className) ;
		}
		}
		} else {
		File file = new File(new URI(url.toExternalForm())) ;
		File[] files = file.listFiles() ;
		for (int i = 0; i < files.length; i++) {
		if (!files[i].isDirectory()) {
		System.out.println("className = "+ files[i].getName()) ;
		}
		}
		}
		} catch ( IOException ex ) {
		ex.printStackTrace();
		} catch ( URISyntaxException ex ) {
		ex.printStackTrace();
		}
		}
		}
