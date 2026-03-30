# Nacos 配置最佳实践

## DataID 命名策略
*   **公共配置**: `common-config.yaml`（跨服务共享）。
*   **服务特定配置**: `${spring.application.name}-${spring.profiles.active}.yaml`。

## 命名空间 (Namespace) 管理
*   `dev`: 开发环境。
*   `test`: 测试/集成环境。
*   `prod`: 生产环境。

## 引导配置 (application.yml)
使用 Spring Boot 3.0+ 时，请确保开启 `spring.cloud.bootstrap.enabled=true` 或使用新的 `config-import` 方式。

```yaml
spring:
  application:
    name: user-service
  cloud:
    nacos:
      discovery:
        server-addr: ${NACOS_ADDR:127.0.0.1:8848}
        namespace: ${NACOS_NAMESPACE:dev}
      config:
        server-addr: ${NACOS_ADDR:127.0.0.1:8848}
        file-extension: yaml
        namespace: ${NACOS_NAMESPACE:dev}
```
