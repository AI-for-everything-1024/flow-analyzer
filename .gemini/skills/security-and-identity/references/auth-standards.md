# 认证与授权标准 (OAuth2.0 & JWT)

定义微服务架构下的统一身份认证与令牌管理规范。

## 1. 认证模式 (OAuth 2.1)
*   **授权码模式 (Authorization Code)**: 适用于具有服务器端后端的应用。
*   **客户端模式 (Client Credentials)**: 适用于服务间 (M2M) 通讯。
*   **刷新令牌 (Refresh Token)**: 用于在不重新登录的情况下延长访问有效期。

## 2. JWT (JSON Web Token) 规范
*   **结构**: `Header.Payload.Signature`
*   **算法**: 必须使用非对称加密算法（如 RS256），严禁使用 HS256（对称加密）。
*   **Payload 声明**: 
    - 标准字段: `iss` (签发者), `exp` (过期时间), `sub` (用户ID)。
    - 自定义字段: `roles` (用户角色), `tenantId` (租户ID)。
*   **存储**: 令牌不应包含敏感信息，且必须保持短小的过期时间（如 30-60 分钟）。

## 3. 服务间安全 (Internal M2M)
*   微服务间调用必须携带有效的 JWT 令牌。
*   使用网关 (Gateway) 进行统一的令牌校验，下游服务通过解析 Header 获取用户信息，无需重复校验。
