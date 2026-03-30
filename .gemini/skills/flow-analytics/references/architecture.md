# 流量分析架构

本文档概述了处理网络流量数据（NetFlow, IPFIX, sFlow）的高层级架构。

## 核心组件

*   **Apache Flink (流处理引擎)**: 摄取归一化后的 `FlowRecord` 对象。执行实时过滤、聚合和异常检测。
*   **RocksDB (状态后端)**: 对于在 Flink 中管理高基数状态（如 Top-N IP 列表）至关重要。
*   **Redis (内存数据库)**: 用于快速数据补全和查找。
*   **Apache Doris (MPP 分析型数据库)**: 分析型数据仓库。**注**: 关于 Doris 的模式设计和数仓分层，请参考 `architecture-and-standards` 技能中的 [database-design.md]。

## 数据流水线

1.  **摄取与归一化**: 原始报文 -> 解析器 -> Kafka 主题。
2.  **实时处理 (Flink)**:
    *   **补全 (Enrichment)**: 关联维度数据（来自 Redis 或广播状态）。
    *   **聚合**: 针对 1 分钟/5 分钟指标的滚动窗口。汇入 Doris。
    *   **DDoS 检测**: 使用 CEP 进行模式匹配的滑动窗口。
3.  **存储与查询 (Doris)**:
    *   遵循项目标准中定义的 5 层架构 (ODS/DIM/DWD/DWS/ADS)。
