/* com.cutty.bravo.core.web.filter.GZipFilter.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2011-6-28 下午01:23:52, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.web.filter;
import java.io.ByteArrayOutputStream;   
import java.io.IOException;   
import java.util.zip.GZIPOutputStream;   
  
import javax.servlet.Filter;   
import javax.servlet.FilterChain;   
import javax.servlet.FilterConfig;   
import javax.servlet.ServletException;   
import javax.servlet.ServletOutputStream;   
import javax.servlet.ServletRequest;   
import javax.servlet.ServletResponse;   
import javax.servlet.http.HttpServletRequest;   
import javax.servlet.http.HttpServletResponse;  

import com.cutty.bravo.core.web.warpper.GZipWrapper;
/**
 *
 * <p>
 * <a href="GZipFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class GZipFilter  implements Filter {   
  
    public void destroy() {   
    }   
      /**  
       * 判断浏览器是否支持GZIP  
       * @param request  
       * @return  
       */  
      private static boolean isGZipEncoding(HttpServletRequest request){   
        boolean flag=false;   
        String encoding=request.getHeader("Accept-Encoding");   
        if(encoding.indexOf("gzip")!=-1){   
          flag=true;   
        }   
        return flag;   
      }   
         
    public void doFilter(ServletRequest request, ServletResponse response,   
            FilterChain chain) throws IOException, ServletException {   
        HttpServletResponse resp = (HttpServletResponse) response;   
        HttpServletRequest req=(HttpServletRequest)request;   
        if(isGZipEncoding(req)){   
        	GZipWrapper wrapper = new GZipWrapper(resp);   
            chain.doFilter(request, wrapper);   
            byte[] gzipData = gzip(wrapper.getResponseData());   
            resp.addHeader("Content-Encoding", "gzip");   
            resp.setContentLength(gzipData.length);   
            ServletOutputStream output = response.getOutputStream();   
            output.write(gzipData);   
            output.flush();   
        } else {   
            chain.doFilter(request, response);   
        }           
  
    }   
  
    public void init(FilterConfig arg0) throws ServletException {   
  
    }   
  
    private byte[] gzip(byte[] data) {   
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(10240);   
        GZIPOutputStream output = null;   
        try {   
            output = new GZIPOutputStream(byteOutput);   
            output.write(data);   
        } catch (IOException e) {   
        } finally {   
            try {   
                output.close();   
            } catch (IOException e) {   
            }   
        }   
        return byteOutput.toByteArray();   
    }   
  
}   
