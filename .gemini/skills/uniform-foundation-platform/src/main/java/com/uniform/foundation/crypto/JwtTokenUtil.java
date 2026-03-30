package com.uniform.foundation.crypto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * JWT 令牌工具类 (RS256 非对称加密)
 * 遵循 security-and-identity 认证与加密规范
 * 遵循 architecture-and-standards 整洁代码规范
 */
public class JwtTokenUtil {

    private static final String CLAIM_ROLES = "roles";
    private static final String CLAIM_TENANT_ID = "tenantId";
    private static final String ISSUER = "gemini-platform";
    
    /**
     * 过期时间 (毫秒)，默认 1 小时 (3600000ms)
     */
    private static final long EXPIRATION_TIME = 3600_000L;

    /**
     * 签发令牌 (RS256)
     *
     * @param userId     用户ID (sub)
     * @param roles      角色列表
     * @param tenantId   租户ID
     * @param privateKey 私钥
     * @return 生成的 JWT 字符串
     */
    public String generateToken(String userId, List<String> roles, String tenantId, PrivateKey privateKey) {
        if (userId == null || roles == null || tenantId == null || privateKey == null) {
            throw new IllegalArgumentException("Parameters for generateToken cannot be null");
        }

        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userId)
                .claim(CLAIM_ROLES, roles)
                .claim(CLAIM_TENANT_ID, tenantId)
                .setIssuer(ISSUER)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    /**
     * 解析并验证令牌
     *
     * @param token     JWT 字符串
     * @param publicKey 公钥
     * @return 令牌负载 (Claims)
     * @throws io.jsonwebtoken.JwtException 如果令牌无效、伪造或已过期
     */
    public Claims parseToken(String token, PublicKey publicKey) {
        if (token == null || token.isEmpty() || publicKey == null) {
            throw new IllegalArgumentException("Token and PublicKey cannot be null or empty");
        }

        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 验证令牌是否有效 (包含过期检查)
     *
     * @param token     JWT 字符串
     * @param publicKey 公钥
     * @return 是否有效
     */
    public boolean validateToken(String token, PublicKey publicKey) {
        try {
            Claims claims = parseToken(token, publicKey);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从 Claims 中获取自定义 Payload 信息
     */
    public String getUserId(Claims claims) {
        return claims.getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(Claims claims) {
        return claims.get(CLAIM_ROLES, List.class);
    }

    public String getTenantId(Claims claims) {
        return claims.get(CLAIM_TENANT_ID, String.class);
    }
}
