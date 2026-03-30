# 压测期间的性能监控

在压力测试过程中，必须同步观察系统各层级的健康指标。

## 1. 资源层 (基础设施)
*   **CPU**: 使用率是否均衡？是否存在由于锁竞争导致的单核跑满？
*   **Memory**: 堆内存 (JVM Heap) 消耗曲线是否平稳？非堆内存 (Direct Buffer) 是否持续增长（内存泄漏信号）？
*   **Network**: 带宽是否达到瓶颈？UDP 丢包率（针对 NetFlow 摄取侧）是多少？

## 2. 应用层 (微服务/Flink)
*   **GC 频率与耗时**: 是否出现频繁的 Full GC？STW 耗时是否影响了实时解析延迟？
*   **线程池**: 工作线程池是否处于满负荷状态？
*   **Kafka 堆积**: 消费者的位点落后 (Lag) 趋势。
*   **Flink 反压**: 检查 Web UI 中的 Backpressure 监控。

## 3. 数据库与存储 (Doris/MySQL)
*   **Doris Profile**: 慢查询日志分析。
*   **写入吞吐**: 观察 Doris 的 Stream Load 频率和事务耗时。
*   **连接池**: 数据库连接池活跃数是否达到 MaxActive。

## 4. 观察工具
*   **Prometheus + Grafana**: 实时查看各项指标面板。
*   **Arthas**: 压测期间对瓶颈方法进行 `thread -n` 或 `profiler start` 采样分析。
