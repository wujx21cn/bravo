/* com.cutty.bravo.core.utils.FileUtils.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2011-1-11 下午02:31:55, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.StringTokenizer;

/**
 *
 * <p>
 * <a href="FileUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class FileUtils {
	public static void copyFile(String source, String target) throws IOException {
		File f1 = new File(source);
		File f2 = new File(target);
		int length = 2097152;
		FileInputStream in = new FileInputStream(f1);
		FileOutputStream out = new FileOutputStream(f2);
		byte[] buffer = new byte[length];
		while (true) {
			int ins = in.read(buffer);
			if (ins == -1) {
				in.close();
				out.flush();
				out.close();
				break;
			} else
				out.write(buffer, 0, ins);
		}
	}
	public static void createFolders(String path) {
		String splitChar = "/";
		if (path.indexOf("\\")>-1)splitChar="\\";
		 StringTokenizer   st=new   StringTokenizer(path,splitChar);
	       String   path1=st.nextToken()+splitChar;
	       String   path2 =path1;
	       while(st.hasMoreTokens())
	       {
	             path1=st.nextToken()+splitChar;
	             path2+=path1;
	             File inbox   =   new File(path2);
	             if(!inbox.exists())
	                  inbox.mkdir();
	       }

	}
	
	public static void writeStr2File(String fileName,String content) throws IOException{
		PrintStream p = null;
		 try {
		      File f = new File(fileName);
		      if (new File(f.getParent()).exists() == false) {
		        f.getParentFile().mkdirs();
		      }
		      f.createNewFile();
		      p = new PrintStream(new FileOutputStream(f, false));
		      p.println(content);
		    } catch (IOException e) {
		      throw e;
		    } finally{
		    	p.close();
		    }

	}
	
	public static void main(String[] args){
		System.out.println("c:\\aaa\\xxx\\ccc".indexOf("\\"));
		createFolders("c:\\aaa\\xxx\\ccc");
	}

}
