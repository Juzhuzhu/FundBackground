package com.fund.utils;

import com.google.common.base.Preconditions;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

/**
 * 生成JWT工具类
 * <p>
 * Create at 2023/03/04 00:36
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/04
 * @since 1.0.0
 */
public class JwtUtils {
    private static final Long TOKEN_EXPIRATION = (long) (1000 * 60 * 60 * 24);
    //    private final static Long TOKEN_EXPIRATION = (long) (1000 * 10);
    /**
     * token密钥
     */
    private static final String TOKEN_SING_KEY = "juZhuZhuFundSystem";

    public JwtUtils() {
    }

    private static Key getKeyInstance() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] encode = Base64.getUrlEncoder().encode(TOKEN_SING_KEY.getBytes());
        return new SecretKeySpec(encode, signatureAlgorithm.getJcaName());
    }

    /**
     * 创建token
     *
     * @param id   用户id
     * @param name 用户名
     * @return token字符串
     */
    public static String createToken(String id, String name) {
        //没加token过期时间                 .setExpiration(new Date(System.currentTimeMillis()+TOKEN_EXPIRATION))
        return Jwts.builder()
                .setSubject("FUND-USER")
                .claim("id", id)
                .claim("name", name)
                .signWith(SignatureAlgorithm.HS512, getKeyInstance())
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    /**
     * 校验token是否过期
     *
     * @param token 令牌
     * @return true or false
     */
    public static boolean checkToken(String token) {
        Preconditions.checkNotNull(token, "token为空");
        JwtParser parser = Jwts.parser();
        try {
            parser.setSigningKey(getKeyInstance()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 用户id
     *
     * @param token 令牌
     * @return 用户id
     */
    public static String getId(String token) {
        Claims claims = getClaims(token);
        return (String) claims.get("id");
    }

    /**
     * 获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getName(String token) {
        Claims claims = getClaims(token);
        return (String) claims.get("name");
    }

    /**
     * 校验token并返回Claims
     *
     * @param token 令牌
     * @return Claims
     */
    private static Claims getClaims(String token) {
        Preconditions.checkNotNull(token, "token为空");
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
        return claimsJws.getBody();
    }


}
