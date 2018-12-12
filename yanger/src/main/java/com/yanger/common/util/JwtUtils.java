package com.yanger.common.util;

import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import com.yanger.common.vo.TokenMsg;

/**
 * @description jwt工具类
 * @author YangHao
 * @date 2018年9月23日-下午4:28:57
 */
public class JwtUtils {

	private static final String SECRET = "yanger#$%*!()<blog>";

	private static final String EXP = "exp";

	private static final String HEADER = "token";

	private final static int expiresSecond = 24 * 60 * 60 * 10000;

	/**
	 * @description 根据对象生成token
	 * @author YangHao
	 * @date 2018年9月23日-下午4:28:57
	 * @param object
	 * @return
	 */
	public static <T> String sign(T object) {
		try {
			final JWTSigner signer = new JWTSigner(SECRET);
			final Map<String, Object> claims = new HashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(object);
			claims.put(HEADER, jsonString);
			claims.put(EXP, System.currentTimeMillis() + expiresSecond);
			return signer.sign(claims);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @description 根据token解析出对象
	 * @author YangHao
	 * @date 2018年9月23日-下午4:28:57
	 * @param jwt
	 * @return
	 */
	public static <T> T parse(Class<T> classT, String jwt) {
		final JWTVerifier verifier = new JWTVerifier(SECRET);
		try {
			final Map<String, Object> claims = verifier.verify(jwt);
			if (claims.containsKey(EXP) && claims.containsKey(HEADER)) {
				long exp = (Long) claims.get(EXP);
				long currentTimeMillis = System.currentTimeMillis();
				if (exp > currentTimeMillis) {
					String json = (String) claims.get(HEADER);
					ObjectMapper objectMapper = new ObjectMapper();
					return objectMapper.readValue(json, classT);
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		TokenMsg v = new TokenMsg();
		v.setCode("11");
		v.setName("12");
		System.out.println(sign(v));
		;
	}

}