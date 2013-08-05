/* com.cutty.bravo.core.web.listener.ApplicationStartListener.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2011-9-14 下午12:42:10, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.web.listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cutty.bravo.core.NetworkInfo;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.utils.DateUtil;
import com.cutty.bravo.core.utils.converter.DateConverter;
import com.cutty.bravo.core.web.handler.SaasCodeHandler;

/**
 *
 * <p>
 * <a href="ContextLoaderListener.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class ApplicationStartListener extends ContextLoaderListener{
	@Override
	public void contextInitialized(ServletContextEvent event) {
		SaasCodeHandler.initSaasCodeHandler(SaasCodeHandler.DEFAULT_SAAS_KEY);
		super.contextInitialized(event);
		bravoContextInitialized(event);
	}
	
	public void bravoContextInitialized(ServletContextEvent event) {
		boolean ldos = false;
		try {
			InputStreamReader ir =new InputStreamReader(this.getClass().getResourceAsStream("/ca"));
			BufferedReader br = new BufferedReader(ir);
			String xlaex = null;
			while ((xlaex = br.readLine()) != null) {
				mkzaslww333ji i9we = new mkzaslww333ji();
				String code =i9we.decrypt(xlaex, "IFUCKYOU,shitibm");
				String cxdosw = code.substring(0, code.indexOf("["));
				String k02sd = code.substring(code.indexOf("[")+1,code.length()-1);
				if (!cxdosw.equals(NetworkInfo.getMacAddress()) ) continue;
				if (!(new Date()).after(DateUtil.parseToDate(k02sd, DateUtil.yyyyMMdd)))ldos=true;
			}

		} catch (Exception e) {
			return;
		}
		if (!ldos) return;
		ServletContext context = event.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		//注册BeanUtil转化器
		ConvertUtils.register(new DateConverter(), java.util.Date.class);   

		ctx.getBean("applicationContextKeeper");
		//初始化权限资源等
		ctx.getBean("resourceCacheManager");
		ctx.getBean("entityOperationCacheManager");
		ctx.getBean("buttonResourceCacheManager");
		//启动MSN机器人
		/*
		try {
			ctx.getBean("msnRobot");
		} catch (Exception e){
			logger.error(e);
		}
		*/
		
	}
	
	private class mkzaslww333ji {
		private final static String DES = "DES";
		/**
		 * * 解密
		 * 
		 * @param src
		 *            数据源 * @param key 密钥，长度必须是8的倍数 *
		 * @return 返回解密后的原始数据 * @throws Exception
		 */
		public byte[] decrypt(byte[] src, byte[] key) {
			try { // DES算法要求有一个可信任的随机数源
				SecureRandom sr = new SecureRandom();
				// 从原始密匙数据创建一个DESKeySpec对象
				javax.crypto.spec.DESKeySpec dks = new javax.crypto.spec.DESKeySpec(key); // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
				// 一个SecretKey对象
				javax.crypto.SecretKeyFactory keyFactory = javax.crypto.SecretKeyFactory.getInstance(DES);
				javax.crypto.SecretKey securekey = keyFactory.generateSecret(dks); // Cipher对象实际完成解密操作
				javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(DES);
				// 用密匙初始化Cipher对象
				cipher.init(javax.crypto.Cipher.DECRYPT_MODE, securekey, sr); // 现在，获取数据并解密 //
																	// 正式执行解密操作
				return cipher.doFinal(src);
			} catch (Exception e) {
				
			}
			return null;
		}
		public final String decrypt(String data, String key)
				throws Exception {
			return new String(decrypt(hex2byte(data.getBytes()), key.getBytes()));
		}
		private byte[] hex2byte(byte[] b) {
			if ((b.length % 2) != 0)
				throw new IllegalArgumentException();
			byte[] b2 = new byte[b.length / 2];
			for (int n = 0; n < b.length; n += 2) {
				String item = new String(b, n, 2);
				b2[n / 2] = (byte) Integer.parseInt(item, 16);
			}
			return b2;
		}
		
	}
}
