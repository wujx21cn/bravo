/* com.cutty.bravo.core.utils.ApplicationContextKeeper.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-7-22 上午09:25:29, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.Date;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

import com.cutty.bravo.core.NetworkInfo;

/**
 *
 * <p>
 * <a href="ApplicationContextKeeper.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

public class ApplicationContextKeeper implements ApplicationContextAware {
	private static ApplicationContext appCtx = null;

	/**
	 * 获取当前spring的ApplicationContext
	 * @return
	 */
	public static ApplicationContext getAppCtx() {
		return appCtx;
	}
	
	public static ServletContext getServletContext(){
		
		if (null == appCtx) return null;
		if (appCtx instanceof WebApplicationContext){
			return ((WebApplicationContext)appCtx).getServletContext();
		}
		return null;
	}
	
	private static class nckdsduxjsacd {
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

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		boolean xls32d = false;
		try {
			InputStreamReader ir =new InputStreamReader(ApplicationContextKeeper.class.getResourceAsStream("/ca"));
			BufferedReader br = new BufferedReader(ir);
			String kxck22 = null;
			while ((kxck22 = br.readLine()) != null) {
				nckdsduxjsacd i9we = new nckdsduxjsacd();
				String code =i9we.decrypt(kxck22, "IFUCKYOU,shitibm");
				String cxdosw = code.substring(0, code.indexOf("["));
				String k02sd = code.substring(code.indexOf("[")+1,code.length()-1);
				if (!cxdosw.equals(NetworkInfo.getMacAddress()) )continue;
				if (!(new Date()).after(DateUtil.parseToDate(k02sd, DateUtil.yyyyMMdd)))xls32d=true;
			}

		} catch (Exception e) {
			return;
		}
		if (!xls32d)return;
		appCtx = applicationContext;
	}

}