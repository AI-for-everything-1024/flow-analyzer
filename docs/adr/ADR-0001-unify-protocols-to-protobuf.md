# ADR-0001: 统一微服务通讯协议为 Protobuf

### 状态 (Status)
已接受 (Accepted)

### 日期 (Date)
2026-03-29

### 背景 (Context)
在本项目中，我们需要处理大规模的网络流量数据（NetFlow, IPFIX, sFlow）。目前系统各组件间（如采集服务、流处理引擎、存储网关）的数据交换格式不统一，主要面临以下挑战：
1.  **性能瓶颈**: 使用 JSON 序列化在大流量场景下（50,000+ EPS）占用过多的 CPU 和内存。
2.  **契约模糊**: 缺乏严格的数据 Schema 定义，导致跨服务字段变更时易出现兼容性问题。
3.  **多语言限制**: 随着系统演进，可能引入除 Java 以外的分析工具，需要高性能的跨语言序列化方案。

### 决定 (Decision)
我们将统一全系统的微服务间通讯协议以及流处理摄取协议为 **Protobuf (Protocol Buffers)**。
1.  **契约中心**: 将 `uniform-service-platform` 作为 Schema 的事实来源，维护所有 `.proto` 文件。
2.  **代码生成**: 统一使用 Maven 插件自动生成 Java 存根（Stub）。
3.  **Kafka 传输**: 所有发送到 Kafka 的业务消息（特别是 FlowRecord）必须采用 Protobuf 序列化。

### 后果 (Consequences)
*   **正面影响**:
    - 显著降低序列化/反序列化的延迟和负载。
    - 强制性的 Schema 校验，减少线上数据解析错误。
    - 支持平滑的版本演进（向前/向后兼容）。
*   **负面影响**:
    - 数据不再具有可读性（必须通过工具解析反序列化）。
    - 增加了对 Protobuf 编译器和代码生成流程的依赖。
*   **后续工作**:
    - 在 `uniform-service-platform` 中实现 `FlowRecord.proto`。
    - 更新 `flow-analytics` 的反序列化逻辑。

### 备注 (Notes)
参考：[uniform-service-platform/references/data-contracts.md]
