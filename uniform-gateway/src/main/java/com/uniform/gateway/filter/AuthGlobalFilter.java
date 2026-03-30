package com.uniform.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 统一身份认证过滤器
 * 遵循 spring-cloud-alibaba-scaffold 网关设计规范
 * 参考 security-and-identity 认证逻辑规范
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * 标准响应包装类 (JDK 17 Record)
     */
    public record Result<T>(int code, String message, T data) {}

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(AUTHORIZATION_HEADER);

        // 1. 提取 Bearer Token
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return unauthorizedResponse(exchange, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(BEARER_PREFIX.length());

        // 2. 验证 Token (伪代码逻辑)
        // TODO: 调用认证服务进行 Token 校验 (RS256 算法, 校验 exp, iss 等)
        // boolean isValid = authService.validate(token);
        boolean isValid = validateToken(token); 

        if (!isValid) {
            return unauthorizedResponse(exchange, "Invalid or expired token");
        }

        // 3. 验证成功，继续过滤器链
        return chain.filter(exchange);
    }

    /**
     * 认证逻辑验证 Token 伪代码
     * 参考 security-and-identity 规范
     */
    private boolean validateToken(String token) {
        // 伪代码：此处应包含 JWT 解析、签名验证及过期检查
        // 1. 解析 Header.Payload.Signature
        // 2. 使用公钥校验签名 (RS256)
        // 3. 检查 exp 是否过期
        // 4. 提取 sub (用户ID) 并放入上下文或 Header 传递给下游
        return token != null && !token.isBlank();
    }

    /**
     * 返回 401 状态码和 Result 格式的错误信息
     * 符合 architecture-and-standards 中的异常处理规范
     */
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // 使用 Result.record 格式构造错误信息
        Result<Object> result = new Result<>(HttpStatus.UNAUTHORIZED.value(), message, null);
        
        // 此处通常使用 Jackson 将 Result 对象序列化为 JSON
        // 为保持代码简洁，此处使用字符串模拟序列化结果
        String body = String.format("{\"code\": %d, \"message\": \"%s\", \"data\": null}", 
                result.code(), result.message());
        
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        // 设置较高的优先级
        return -100;
    }
}
