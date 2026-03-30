# 全链路可观测性规范 (Metrics, Tracing, Logging)

定义统一的观测标准，确保问题可被发现、可被定位、可被复盘。

## 1. 统一分布式追踪 (Distributed Tracing)
*   **协议**: 遵循 OpenTelemetry 标准。
*   **传播链**: 必须在以下组件间无缝透传 `traceId`:
    - `Gateway` -> `OpenFeign` -> `Service`
    - `Service` -> `Kafka Header` -> `Flink`
    - `Flink` -> `Doris/Redis` (作为记录标签存储)。
*   **工具**: SkyWalking 或 Zipkin。

## 2. 结构化日志 (Logging)
*   **格式**: 严格使用 JSON 格式。
*   **核心字段**: `timestamp`, `level`, `traceId`, `spanId`, `serviceName`, `env`, `message`, `stackTrace` (仅在 ERROR 时)。
*   **业务埋点**: 关键业务转换（如：NetFlow -> FlowRecord）必须记录计数日志。

## 3. 指标体系 (Metrics)
采用 Prometheus 指标格式。
*   **通用指标**: JVM 堆栈、线程池状态、HTTP 请求延迟。
*   **业务指标**: 
    - 解析侧: `flow_parsed_count_total`
    - 计算侧: `window_process_time_ms`
    - 存储侧: `doris_load_throughput_mb`
*   **分位数**: 响应时间指标必须包含 P95 和 P99。
