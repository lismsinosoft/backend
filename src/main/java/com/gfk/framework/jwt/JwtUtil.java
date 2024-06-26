package com.gfk.framework.jwt;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gfk.common.constant.BaseConstants;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

/**
 * JWT工具类
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
public class JwtUtil {

    // Token过期时间?分钟（用户登录过期时间是此时间的两倍，以token在reids缓存时间为准）
    public static final long EXPIRE_TIME = 100* 120 * 60 * 1000;

    /**
     *生成签名，5min过期
     *
     * @param username   用户名
     * @param secret    密码
     * @return  加密token
     */
    public static String sign(String userType, String username, String secret, String userId) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create().withClaim("userType", userType).withClaim("username", username).withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
    }

    /**
     *生成临时签名，10min过期
     *
     * @param userId   用户Id
     * @param secret    密钥
     * @return  加密token
     */
    public static String temporarySign(String userId,String secret) {
        Date date = new Date(System.currentTimeMillis() + 600000);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带信息
        return JWT.create().withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
    }


    /**
     * 获得token中包含的用户类型
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的类型
     */
    public static String getUserType(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userType").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中包含的用户类型
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的类型
     */
    public static String getUserType() {
        return getTokenParam("userType", getToken());
    }

    /**
     * 获得token中包含的用户名
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中包含的用户名
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername() {
        return getTokenParam("username", getToken());
    }

    /**
     * 获得token中包含的参数
     *
     * @return 参数值
     */
    public static String getTokenParam(String key, String token) {
        if (StrUtil.isNotEmpty(token)) {
            try {
                DecodedJWT jwt = JWT.decode(token);
                return jwt.getClaim(key).asString();
            } catch (JWTDecodeException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 获得当前用户ID
     *
     * @return token中包含的用户ID
     */
    public static String getUserId() {
        return getTokenParam("userId", getToken());
    }

    /**
     * 获得当前用户的部门ID
     *
     * @return 部门ID
     */
    public static String getDeptId() {
        return getTokenParam("deptId", getToken());
    }

    /**
     * 校验 token 是否正确
     *
     * @param token    密钥
     * @param username 用户名
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            //在token中附带了username信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            //验证 token
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 校验 临时token 是否正确
     *
     * @param token    密钥
     * @param userId 用户Id
     * @return 是否正确
     */
    public static boolean verifyTemporary(String token, String userId, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            //在token中附带了userId信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userId", userId)
                    .build();
            //验证 token
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获取web请求的token参数
     * @return  token
     */
    public static String getToken() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return "";
        }
        return ((ServletRequestAttributes) attributes).getRequest().getHeader(JWTConstants.X_ACCESS_TOKEN);
    }

    /**
     * 管理员判断
     * @return  是否是管理员
     */
    public static boolean checkAdmin() {
        return BaseConstants.SYS_ADMIN.equals(JwtUtil.getUsername());
    }
}
