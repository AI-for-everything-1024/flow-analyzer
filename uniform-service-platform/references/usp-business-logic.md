# USP 核心业务逻辑规范

Unified Service Platform (USP) 是承载项目核心解析与归一化逻辑的共享库。

## 1. 核心领域包
*   **`usp-flow-core`**: 包含 NetFlow V5/V9, IPFIX, sFlow 的核心解析引擎（基于 Netty）。
*   **`usp-flow-model`**: 定义统一的 `FlowRecord` POJO、枚举（Protocol, TcpFlags）及常量。

## 2. 依赖关系
USP 必须建立在 UFP 之数，不得在 USP 中重新实现基础工具。
```text
[微服务/Flink任务] -> 依赖 -> [USP] -> 依赖 -> [UFP]
```

## 3. 设计原则
*   **模型一致性**: 确保全系统对 Flow 数据的理解一致。
*   **无状态解析**: 解析器应设计为无状态，以支持高并发环境。

## 4. 使用示例 (解析引擎)
```java
// 使用 USP 提供的解析器将字节流转换为统一模型
FlowRecord record = FlowParser.parse(byteBuf, remoteAddress);
```
