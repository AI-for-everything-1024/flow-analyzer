# API 文档与日志规范

## 1. OpenAPI / Swagger 规范
使用 SpringDoc (OpenAPI 3) 为微服务提供标准、可交互的接口文档。

*   **接入方式**: 依赖 `springdoc-openapi-starter-webmvc-ui`。
*   **注解规范**: 
    - 类级: `@Tag(name = "User Management")`
    - 方法级: `@Operation(summary = "Get user details")`
    - 字段级: `@Schema(description = "Primary Key", example = "1")`
*   **UI 路径**: `/swagger-ui.html`

## 2. 结构化日志 (JSON)
为了便于 ELK (Elasticsearch, Logstash, Kibana) 采集，微服务日志必须支持结构化输出。

*   **格式要求**: 包含 `timestamp`, `level`, `traceId`, `serviceName`, `message`。
*   **Logback 配置**: 使用 `LogstashLogbackEncoder`。
*   **TraceId 注入**: 
    - 使用 MDC (Mapped Diagnostic Context) 将全链路追踪 ID 注入每一行日志。
    - 所有 OpenFeign 拦截器必须透传 `X-B3-TraceId`。

## 3. 敏感数据脱敏 (Logging)
*   **严禁日志记录**: 用户密码、API 密钥、个人身份信息 (PII)。
*   **手机号/身份证**: 在日志输出前使用 `String.replaceAll` 或自定义注解进行掩码处理（如：`138****5678`）。

## 4. 日志分级
*   `ERROR`: 系统异常、依赖故障。
*   `WARN`: 业务异常、资源临界。
*   `INFO`: 关键业务节点（如订单提交、告警触发）。
*   `DEBUG`: 详细开发调试（严禁生产环境开启）。
