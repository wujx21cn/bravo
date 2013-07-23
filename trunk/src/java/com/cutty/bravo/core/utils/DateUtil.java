/* com.cutty.bravo.core.utils.DateUtil.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-7-28 上午05:47:17, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.MissingResourceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cutty.bravo.core.ConfigurableConstants;


/**
 *
 * <p>
 * <a href="DateUtil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

public class DateUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);

	private static String defaultDatePattern = null;

	private static String timePattern = "HH:mm";

	public static final String yyMMdd = "yy-MM-dd";

	public static final String yyyyMMdd = "yyyy-MM-dd";

	public static final String HHmmss = "HH:mm:ss";

	public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";

	public static final String yyMMddHHmmss = "yy-MM-dd HH:mm:ss";

	public static Date parseToDate(String s, String style) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(style);
		Date date = null;
		if (s == null || s.length() < 8) {
			return null;
		}
		try {
			date = simpleDateFormat.parse(s);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date;
	}

	public static String parseToString(Date date, String style) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(style);
		String str = null;
		if (date == null) {
			return null;
		}
		str = simpleDateFormat.format(date);
		return str;
	}

	public static String getDatePattern() {
		try {
			defaultDatePattern = ConfigurableConstants.getProperty("convert.format.date", "yyyy-MM-dd");
		} catch (MissingResourceException mse) {
			defaultDatePattern = "yyyy-MM-dd";
		}

		return defaultDatePattern;
	}

	public static String getDateTimePattern() {
		return DateUtil.getDatePattern() + " HH:mm:ss";
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	public static final String getDateTimeFromlong(String aMask, long dateLong) {
		SimpleDateFormat df = null;
		String returnValue = "";
		Long aDate = new Long(dateLong);
		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	public static final String getDateTime(String aMask, String aDate) {
		Date date = new Date(new Long(aDate).longValue());
		return getDateTime(aMask, date);
	}

	public static final long DateString2Long(String aMask, Date date) {
		SimpleDateFormat df = null;
		long returnValue = 0;

		if (date == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			try {
				String tmp = df.format(date);
				returnValue = df.parse(tmp).getTime();

			} catch (ParseException e) {
				log.error("aDate is invalid!");
				log.error(e);
			}
		}

		return (returnValue);
	}

	public static final String long2DateString(String mask, String date) {
		SimpleDateFormat df = null;
		String rs = null;
		if (date == null) {
			log.error("aDate is null!");
		} else {
			Date tmp = new Date(new Long(date).longValue());
			df = new SimpleDateFormat(mask);
			rs = df.format(tmp);
		}
		return rs;
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * 
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate)
			throws ParseException {
		Date aDate = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + getDatePattern());
			}

			aDate = convertStringToDate(getDatePattern(), strDate);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate
					+ "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());

		}

		return aDate;
	}

	public static String toDateString(String sMask, String input) {
		SimpleDateFormat sf = new SimpleDateFormat(sMask);
		String output = "";
		if (input != null && !"".equals(input)) {
			try {
				Date temp = new Date(new Long(input).longValue());
				output = sf.format(temp);
			} catch (Exception ex) {
				output = "";
			}
		}
		return output;
	}

	public static long DateString2Long(String sMask, String input) {
		SimpleDateFormat sf = new SimpleDateFormat(sMask);
		long output = 0;
		if (input != null && !"".equals(input)) {
			try {
				output = sf.parse(input).getTime();
			} catch (ParseException e) {
				log.debug(e.getMessage());
				output = 0;
			}
		}
		return output;
	}
	/**
	 * @author kellen xie
	 * 两个timestamp相减天数之差 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static long difDays(Date t1,Date t2){
				
		long dif = (t2.getTime()/1000-t1.getTime()/1000)/(24*60*60);  //相差天数
		
		log.debug(" 相差天数:"+dif);
		return dif;
	}
	
	/**
	 * @author kellen xie
	 * 两个timestamp相减小时之差 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static long difHours(Date t1,Date t2){
	
		long dif = (t2.getTime()/1000-t1.getTime()/1000)/(60*60);  //相差小时
		
		log.debug(" 相差小时:"+dif);
		return dif;
	}
	/**
	 * @author kellen xie
	 * 两个timestamp相减小时之差 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static long difMinutes(Date t1,Date t2){
				
		long dif = (t2.getTime()/1000-t1.getTime()/1000)/(60);  //相差分钟
		
		log.debug(" 相差分钟:"+dif);
		return dif;
	}
	
	 public static java.sql.Date string2Date(String dateString)   
	      throws java.lang.Exception {   
	          DateFormat dateFormat;   
	          dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	          dateFormat.setLenient(false);   
	          java.util.Date timeDate = dateFormat.parse(dateString);//util类型   
	         // log.debug(timeDate.toString());   
	          java.sql.Date dateTime = new java.sql.Date(timeDate.getTime());//sql类型   
	          return dateTime;   
	      }   

	 /**date transfer to timestamp  by hand add by ewen 2007-11-08
	  * 
	  * @param dateString
	  * @param sMask is the dateString format,is not the format what will be transferred
	  * @return Timestamp
	  * @throws Exception
	  */
	 public static  Timestamp stringToTimestamp(String dateString,String sMask){
		SimpleDateFormat sdf = new SimpleDateFormat(sMask);
		Timestamp sqlTimestamp;
	    Date date;
		try {
			date = sdf.parse(dateString);
			sqlTimestamp=new java.sql.Timestamp(date.getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 //e.printStackTrace();
			 sqlTimestamp=null;
		}
		 
	    return sqlTimestamp;
	 }
	 
	public static void main(String[] args) throws ParseException, FileNotFoundException, IOException {
		//log.debug(parseToString(new Date(),yyyyMMddHHmmss));
		//DateFormat dateFormat=DateFormat.getDateInstance();
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    try{
	    Date date = sdf.parse("2007-07-15");
	    java.sql.Timestamp sqlTimestamp=new java.sql.Timestamp(date.getTime());
	    
	    log.debug(sqlTimestamp);
	    System.out.println(sqlTimestamp);
	    }catch(Exception e){
	    e.printStackTrace();
	    }*/
	    /*
	    //代码里直接读取资源文件内容
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("ApplicationResources.properties");
		Properties props = new Properties();
		props.load(inputStream); 
		System.out.println(props.getProperty("common.batchEdit.message"));
		char g = '\u6D82';
		Character dd=new Character(g);
		System.out.println(g);*/
        System.out.println(getMonth(new Date()));
	}
	
	public static String getMonth(Date date){
		return String.format(Locale.US,"%tb", date);
	}
	public static Date getMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, (dayOfWeek - 2) * (-1));
		return cal.getTime();
	}
	
	public static Date addDay(Date source,int days){
		Calendar cal = Calendar.getInstance();
		cal.setTime(source);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	public static Date addMinute(Date source,int imnutes){
		Calendar cal = Calendar.getInstance();
		cal.setTime(source);
		cal.add(Calendar.MINUTE, imnutes);
		return cal.getTime();
	}
	
	public static Date addMonth(Date source,int months){
		Calendar cal = Calendar.getInstance();
		cal.setTime(source);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}
	
}
