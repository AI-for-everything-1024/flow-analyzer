# 常见故障排查标准操作程序 (SOP)

定义针对流量分析系统典型故障的快速恢复流程。

## 1. Flink 任务反压严重 (Backpressure)
*   **现象**: Flink Web UI 显示红色反压，吞吐量骤降。
*   **排查步骤**:
    1.  检查下游 Sink (Doris/Kafka) 是否写入缓慢。
    2.  查看 TaskManager GC 日志，确认是否频繁 Full GC。
    3.  使用 Arthas 的 `dashboard` 或 `thread -n 3` 查看哪个算子占用 CPU 最高。
*   **解决方法**: 增加并行度、优化 `AggregateFunction` 逻辑、调整 RocksDB 缓存。

## 2. Doris 查询响应慢 (Slow Query)
*   **现象**: 仪表盘加载超过 5 秒。
*   **排查步骤**:
    1.  执行 `EXPLAIN ANALYZE <SQL>` 获取执行计划。
    2.  检查是否出现了全表扫描，索引是否命中。
    3.  查看 BE 节点的 CPU 和 IO 使用情况。
*   **解决方法**: 建立 Rollup 预聚合表、调整分桶 (Buckets) 数、优化 SQL 连接逻辑。

## 3. Nacos 实例频繁掉线 (Service Instability)
*   **现象**: 微服务间调用报 503 错误。
*   **排查步骤**:
    1.  检查 Nacos Server 的网络连通性。
    2.  查看微服务的健康检查探针日志。
    3.  确认微服务的内存占用是否接近 Limits 导致被 K8s OOMKill。
*   **解决方法**: 调整 CPU/Memory Limits、优化 Nacos 心跳超时配置。
