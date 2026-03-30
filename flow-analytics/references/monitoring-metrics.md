# 流量分析可观测性规范 (Flink)

## 1. 核心监控指标 (Metrics)
所有 Flink 任务必须通过 `PrometheusReporter` 暴露以下自定义指标：

*   **输入端**: `flow_input_total` (从 Kafka 摄取的原始记录数)。
*   **处理端**:
    *   `flow_enriched_total`: 成功补全维度的记录数。
    *   `flow_enrichment_failed_total`: 补全失败数（如 Redis 响应超时）。
    *   `ddos_alert_triggered_total`: 触发的告警总数。
*   **输出端**: `flow_output_total` (成功写入 Doris 的聚合记录数)。
*   **延迟**: `flow_end_to_end_latency_ms` (报文进入系统到最终告警/入库的耗时)。

## 2. Flink 状态与性能监控
*   **状态大小**: 监控 RocksDB 状态文件的大小，防止磁盘溢出。
*   **Checkpoint 耗时**: 告警如果单个 Checkpoint 耗时超过窗口周期（如 1 分钟）。
*   **反压 (Backpressure)**: 实时监控算子的反压指数，自动触发 Flink 弹性缩扩容。

## 3. 告警上报 (Alerting)
*   **级别**:
    *   `Critical`: 数据摄取中断、反压持续 > 10 分钟。
    *   `Warning`: 补全失败率 > 1%。
*   **渠道**: Kafka -> 告警网关 -> 邮件、企业微信、飞书。
