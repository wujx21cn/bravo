/* com.cutty.bravo.core.NetworkInfo.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2011-6-15 下午09:17:46, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

 */
package com.cutty.bravo.core;

import java.net.InetAddress;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import org.springframework.security.providers.encoding.Md5PasswordEncoder;

import com.cutty.bravo.core.utils.CryptUtils;

/**
 * 
 * <p>
 * <a href="NetworkInfo.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class NetworkInfo {
	public final static String getMacAddress() {
		String os = System.getProperty("os.name");
		if (os.startsWith("Windows")) {
			return windowsParseMacAddress(windowsRunIpConfigCommand());
		} else if (os.startsWith("Linux")) {
			return linuxParseMacAddress(linuxRunIfConfigCommand());
		} 
		return null;
	}

	/*
	 * Linux stuff
	 */
	private final static String linuxParseMacAddress(String ipConfigResponse)
			 {
		String localHost = null;
		try {
			localHost = InetAddress.getLocalHost().getHostAddress();
		} catch (java.net.UnknownHostException ex) {
		
		}
		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		String lastMacAddress = null;
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();
			boolean containsLocalHost = line.indexOf(localHost) >= 0;
			// see if line contains IP address
			if (containsLocalHost && lastMacAddress != null) {
				return lastMacAddress;
			}
			// see if line contains MAC address
			if (line.indexOf("72:09:42:e3:a9:ee") > 0) return "72:09:42:e3:a9:ee";
			int macAddressPosition = line.indexOf("HWaddr");
			if (macAddressPosition <= 0)
				 continue;
			
			String macAddressCandidate = line.substring(macAddressPosition + 6)
					.trim();
			if (linuxIsMacAddress(macAddressCandidate)) {
				lastMacAddress = macAddressCandidate;
				continue;
			}
		}
		return null;
	}

	private final static boolean linuxIsMacAddress(String macAddressCandidate) {
		// TODO: use a smart regular expression
		if (macAddressCandidate.length() != 17)
			return false;
		return true;
	}

	private final static String linuxRunIfConfigCommand() {
		Process p;
		String outputText = null;
		try {
			p = Runtime.getRuntime().exec("ifconfig");
			InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
			StringBuffer buffer = new StringBuffer();
			for (;;) {
				int c = stdoutStream.read();
				if (c == -1)
					break;
				buffer.append((char) c);
			}
			outputText = buffer.toString();
			stdoutStream.close();
		} catch (IOException e) {

		}

		return outputText;
	}

	/*
	 * Windows stuff
	 */
	private final static String windowsParseMacAddress(String ipConfigResponse) {
		String localHost = null;
		try {
			localHost = InetAddress.getLocalHost().getHostAddress();
		} catch (java.net.UnknownHostException ex) {

		}
		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		String lastMacAddress = null;
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();
			// see if line contains IP address
			if (line.endsWith(localHost) && lastMacAddress != null) {
				return lastMacAddress;
			}
			// see if line contains MAC address
			int macAddressPosition = line.indexOf(":");
			if (macAddressPosition <= 0)
				continue;
			String macAddressCandidate = line.substring(macAddressPosition + 1)
					.trim();
			if (windowsIsMacAddress(macAddressCandidate)) {
				lastMacAddress = macAddressCandidate;
				continue;
			}
		}
		return lastMacAddress;
	}

	private final static boolean windowsIsMacAddress(String macAddressCandidate) {
		// TODO: use a smart regular expression
		if (macAddressCandidate.length() != 17)
			return false;

		return true;
	}

	private final static String windowsRunIpConfigCommand(){
		Process p;
		String outputText=null;
		try {
			p = Runtime.getRuntime().exec("ipconfig /all");
			InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

			StringBuffer buffer = new StringBuffer();
			for (;;) {
				int c = stdoutStream.read();
				if (c == -1)
					break;
				buffer.append((char) c);
			}
			outputText = buffer.toString();
			stdoutStream.close();
		} catch (IOException e) {
		}
		return outputText;
	}

	/*
	 * Main
	 */
	public  static void main(String[] args) {
		try {
			Md5PasswordEncoder aa= new Md5PasswordEncoder();
			System.out.println(aa.encodePassword("WJXCWJ", null));
			
			//be:09:d3:ec:cc:4800:0C:29:D2:E9:63
			System.out.println(getMacAddress());
			String resultsss = CryptUtils.encrypt("00:0C:29:D2:E9:63[2018-08-01]", "IFUCKYOU,shitibm");
			System.out.println(resultsss);
			 resultsss = CryptUtils.encrypt("00:0C:29:D2:E9:63[2012-08-01]", "IFUCKYOU,shitibm");
			System.out.println(CryptUtils.encrypt(getMacAddress()+"[2015-06-01]", "IFUCKYOU,shitibm"));
			 resultsss = CryptUtils.encrypt("00:0C:29:D2:E9:63[2012-08-01]", "IFUCKYOU,shitibm");
			System.out.println(resultsss);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
