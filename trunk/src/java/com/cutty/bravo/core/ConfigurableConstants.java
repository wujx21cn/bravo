/* com.cutty.bravo.core.ConfigurableConstants.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-7-28 上午02:21:08, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * <p>
 * <a href="ConfigurableConstants.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class ConfigurableConstants {
	 protected static Log logger = LogFactory.getLog(ConfigurableConstants.class);
	 protected static Properties p = new Properties();

	    /**
	     * 从config.properties中读取各配置变量
	     */
	    public final static String MESSAGE_BUNDLE_KEY = getProperty("constant.message_bundle_key", "i18n/messages");
	    public final static String ERROR_BUNDLE_KEY = getProperty("constant.error_bundle_key", "i18n/errors");
	    public final static int DEFAULT_PAGE_SIZE = Integer.parseInt(getProperty("constant.default_page_size", String.valueOf(25)));
	    public final static String USER_IN_SESSION = getProperty("constant.user_in_session", "loginUser");
		
	    static {
	        init("config.properties");
	    }
	    
	    /**
	     * 静态读入属性文件到Properties p变量中
	     */
	    protected static void init(String propertyFileName) {
	        InputStream in = null;
	        try {
	        	//class.getClassLoader()获得该class文件加载的web应用的目录，如WEB-INF/classes/就是根目录
	        	//getResourceAsStream(relativeFilePath):定位该文件且获得它的输出流
	            in = ConfigurableConstants.class.getClassLoader().getResourceAsStream(propertyFileName);
	            if (in != null)
	            	//load输出流到Properties对象中
	                p.load(in);
	        } catch (IOException e) {
	            logger.error("load " + propertyFileName + " into Constants error!");
	        }
	        finally {
	            if (in != null) {
	                try {
	                    in.close();
	                } catch (IOException e) {
	                    logger.error("close " + propertyFileName + " error!");
	                }
	            }
	        }
	    }


	    /**
	     * 封装了Properties类的getProperty函数,使p变量对子类透明.
	     *
	     * @param key          property key.
	     * @param defaultValue 当使用property key在properties中取不到值时的默认值.
	     */
	    public static String getProperty(String key, String defaultValue) {
	        return StringUtils.trim(p.getProperty(key, defaultValue));
	    }
}
