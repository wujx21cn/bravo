/* com.cutty.bravo.core.web.filter.CharacterEncodingFilter.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Apr 7, 2009 2:48:18 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.ClassUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * <p>
 * <a href="CharacterEncodingFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class CharacterEncodingFilter  extends OncePerRequestFilter {

	private final static boolean responseSetCharacterEncodingAvailable = ClassUtils.hasMethod(
			HttpServletResponse.class, "setCharacterEncoding", new Class[] {String.class});


	private String encoding;

	private boolean forceEncoding;


	/**
	 * Set the encoding to use for requests. This encoding will be passed into a
	 * {@link javax.servlet.http.HttpServletRequest#setCharacterEncoding} call.
	 * <p>Whether this encoding will override existing request encodings
	 * (and whether it will be applied as default response encoding as well)
	 * depends on the {@link #setForceEncoding "forceEncoding"} flag.
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Set whether the configured {@link #setEncoding encoding} of this filter
	 * is supposed to override existing request and response encodings.
	 * <p>Default is "false", i.e. do not modify the encoding if
	 * {@link javax.servlet.http.HttpServletRequest#getCharacterEncoding()}
	 * returns a non-null value. Switch this to "true" to enforce the specified
	 * encoding in any case, applying it as default response encoding as well.
	 * <p>Note that the response encoding will only be set on Servlet 2.4+
	 * containers, since Servlet 2.3 did not provide a facility for setting
	 * a default response encoding.
	 */
	public void setForceEncoding(boolean forceEncoding) {
		this.forceEncoding = forceEncoding;
	}


	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {
			if (request.getMethod().equals("GET") && this.forceEncoding ) {
				request = new EncodingForGetRequest(request); 
			}
			request.setCharacterEncoding(this.encoding);
			if (this.forceEncoding && responseSetCharacterEncodingAvailable) {
				response.setCharacterEncoding(this.encoding);

			}
		}
		filterChain.doFilter(request, response);
	}

	private class EncodingForGetRequest extends HttpServletRequestWrapper {   
        public EncodingForGetRequest(HttpServletRequest request) {   
            super(request);   
        }   
        
        /**  
         * 转换由表单读取的数据的内码.  
         * 从 ISO 字符转到 utf-8(或gbk).  
         */  
        public String toChi(String input) {
			try {
				byte[] bytes = input.getBytes("ISO-8859-1");
				return new String(bytes, encoding);
			} catch (Exception ex) {
			}
			return null;
		}   
 
        /**
		 * Return the HttpServletRequest holded by this object.
		 */
		private HttpServletRequest getHttpServletRequest() {
			return (HttpServletRequest) super.getRequest();
		}

		/**  
		 * 读取参数 -- 修正了中文问题.  
		 */
		public String getParameter(String name) {
			return toChi(getHttpServletRequest().getParameter(name));
		}

		/**  
		 * 读取参数列表 - 修正了中文问题.  
		 */
		public String[] getParameterValues(String name) {
			String values[] = getHttpServletRequest().getParameterValues(name);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					values[i] = toChi(values[i]);
				}
			}
			return values;
		}
	}   
}
