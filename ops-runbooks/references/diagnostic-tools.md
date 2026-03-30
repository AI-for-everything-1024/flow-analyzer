# 系统诊断与分析工具指南

掌握高效的工具组合，实现分钟级的故障定位。

## 1. Java 诊断利器: Arthas
*   **dashboard**: 查看 CPU、内存、线程池全局概览。
*   **thread -n 3**: 找出最忙的 3 条线程。
*   **watch <className> <method> "{params, returnObj}"**: 动态观察方法输入输出。
*   **profiler start**: 生成火焰图，定位 CPU 瓶颈。

## 2. 指标查询: PromQL 常用语句
*   **吞吐量**: `sum(irate(flow_input_total[5m]))`
*   **错误率**: `sum(irate(flow_parse_failed_total[5m])) / sum(irate(flow_parsed_total[5m]))`
*   **延迟 P99**: `histogram_quantile(0.99, sum(rate(flow_end_to_end_latency_ms_bucket[5m])) by (le))`

## 3. 日志聚合: ELK / Loki 查询
*   **TraceId 关联**: 通过 `traceId:"<ID>"` 跨服务查找完整调用链路。
*   **错误堆栈**: 过滤 `level:"ERROR"` 查看异常发生时的上下文。

## 4. 数据库监控
*   **MySQL**: `show processlist` (查看锁等候), `show index from <table>`。
*   **Doris**: `show load` (查看导入任务状态), `show backends` (查看节点状态)。
