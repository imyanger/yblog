package com.yanger.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 加密工具类
 * 
 * @author YangHao
 */
public class EncryptUtils {

	private static final String KEY_AES = "AES";

	/**
	 * 字符编码
	 */
	public final static String ENCODING = "UTF-8";

	/**
	 * @description MD5加密
	 * @author YangHao
	 * @date 2018年9月18日-上午12:03:25
	 * @param source
	 * @return
	 */
	public static String getMD5(String source) {
		return getMD5(source.getBytes());
	}

	/**
	 * @description MD5加密
	 * @author YangHao
	 * @date 2018年9月18日-上午12:03:25
	 * @param source
	 * @return
	 */
	public static String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte tmp[];
			synchronized (EncryptUtils.class) {
				md.update(source);
				tmp = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			}
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * @description AES加密
	 * @author YangHao
	 * @date 2018年9月18日-上午12:03:25
	 * @param source
	 * @return
	 */
	public static String AESEncrypt(String src, String key) throws Exception {
		if (key == null || key.length() != 16) {
			throw new Exception("key不满足条件");
		}
		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_AES);
		Cipher cipher = Cipher.getInstance(KEY_AES);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(src.getBytes());
		return byte2hex(encrypted);
	}

	/**
	 * @description AES解密
	 * @author YangHao
	 * @date 2018年9月18日-上午12:03:25
	 * @param source
	 * @return
	 */
	public static String AESDecrypt(String src, String key) throws Exception {
		if (key == null || key.length() != 16) {
			throw new Exception("key不满足条件");
		}
		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_AES);
		Cipher cipher = Cipher.getInstance(KEY_AES);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] encrypted1 = hex2byte(src);
		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original);
		return originalString;
	}

	private static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}
		return b;
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	/**
	 * @description Base64编码
	 * @param data：待编码数据
	 * @return String-编码数据
	 * @throws Exception
	 */
	public static String encode(String data) throws Exception {
		// 执行编码
		byte[] b = Base64.encodeBase64(data.getBytes(ENCODING));
		return new String(b, ENCODING);
	}

	/**
	 * @description Base64安全编码，遵循RFC 2045实现
	 * @param data：待编码数据
	 * @return String：编码数据
	 * @throws Exception
	 */
	public static String encodeSafe(String data) throws Exception {
		// 执行编码
		byte[] b = Base64.encodeBase64(data.getBytes(ENCODING), true);
		return new String(b, ENCODING);
	}

	/**
	 * @description Base64解码
	 * @param data：待解码数据
	 * @return String：解码数据
	 * @throws Exception
	 */
	public static String decode(String data) throws Exception {
		// 执行解码
		byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));
		return new String(b, ENCODING);
	}

}
