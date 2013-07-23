/* com.cutty.bravo.core.utils.HtmlUtils.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Nov 5, 2008 8:54:09 PM, Created kukuxia.kevin.hw
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils;

import java.util.regex.Pattern;

/**
 *
 * <p>
 * <a href="HtmlUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.kevin.hw</a>
 */
public class HtmlUtils {
	  private static final String[][] invokesForJs = {
		   {
		      "\\\\", "/"}
		  ,{
		      "'", " "}
		  ,{
		      "[\\n\\r]", " "}
	  };

	  private static final String[][] invokes = {
	      {
	      "<", "&lt;"}
	      , {
	      ">", "&gt;"}
	      , {
		  " ", "&nbsp;"}
	      , {
	      "\"", "&#0034;"}
	      , {
	      "'", "&#0039;"}
	  };
	  
	  /**
	   * Filter the specified value string for characters that are sensitive
	   * in HTML.  This avoids potential attacks caused by including JavaScript
	   * codes in the request URL that is often reported in error messages.
	   */
	  private static String filter(String value, boolean isHtml2Action) {
	    int index = isHtml2Action ? 1 : 0;
	    for (int i = 0; i < invokes.length; i++) {
	    	value = value.replaceAll(invokes[i][1 - index], invokes[i][index]);
	    }
	    return value;
	  }
	  
	  /**Filter the specified value string for characters that are sensitive in JavaScript*/
	  public static String filter(String value) {
		 for (int i = 0; i < invokes.length; i++) {
		    value = value.replaceAll(invokes[i][1], invokes[i][0]);
		 }
		return value;
	  }
	  
	  public static String html2Text(String inputString){
		  String htmlStr = inputString;//含html标签的字符串
		  String textStr = "";
		  java.util.regex.Pattern p_script;
		  java.util.regex.Matcher m_script;
		  java.util.regex.Pattern p_style;
		  java.util.regex.Matcher m_style;		
		  java.util.regex.Pattern p_html;
		  java.util.regex.Matcher m_html;
		  
		  try{
			  String regEx_script = "<[\\s]*?script[^>]*?<[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
			  String regEx_style = "<[\\s]*?style[^>]*?<[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
			  String regEx_html = "<[^>]+>";
			  
			  p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
			  m_script = p_script.matcher(htmlStr);
			  htmlStr  = m_script.replaceAll("");
			  
			  p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
			  m_style = p_style.matcher(htmlStr);
			  htmlStr  = m_style.replaceAll("");
			  
			  p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
			  m_html = p_html.matcher(htmlStr);
			  htmlStr  = m_html.replaceAll("");
			  
			  textStr = htmlStr;
			  
		  }catch(Exception e){
			  System.err.println("过滤html错误:"+e.getMessage());
		  }
		  return textStr;
	  }

}
