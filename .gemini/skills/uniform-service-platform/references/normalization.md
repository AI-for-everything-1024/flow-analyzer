# FlowRecord 归一化架构

为了统一处理各种流量协议（NetFlow V5/V9, IPFIX, sFlow），所有解析后的报文必须归一化为 `FlowRecord` POJO。

## 核心 FlowRecord 字段 (Java)

```java
public class FlowRecord {
    // 元数据
    private String protocolType; // "NETFLOW_V5", "NETFLOW_V9", "IPFIX", "SFLOW"
    private long timestamp;
    private String exporterIp;

    // L3/L4 层信息
    private String srcIp;
    private String dstIp;
    private int srcPort;
    private int dstPort;
    private int protocol; // TCP=6, UDP=17 等
    private int tos;

    // 流量统计
    private long bytes;
    private long packets;

    // 接口信息
    private int inputInterface;
    private int outputInterface;

    // TCP 标识
    private int tcpFlags;

    // sFlow 特定字段（如适用）
    private long samplingRate;

    // Getters and Setters ...
}
```

## 归一化策略

| 协议 | 映射逻辑 |
| :--- | :--- |
| **NetFlow V5** | 从固定长度记录直接映射。 |
| **NetFlow V9** | 需要模板（Template）管理。将模板字段映射到 `FlowRecord`。 |
| **IPFIX** | 类似于 V9（基于模板）。遵循 RFC 7011 字段 ID。 |
| **sFlow** | 从 Flow Sample / Counter Sample 中提取。处理采样率倍率。 |
