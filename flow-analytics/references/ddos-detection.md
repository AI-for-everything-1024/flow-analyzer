# DDoS 检测与模式匹配 (CEP)

使用 Flink 识别流量攻击并追踪来源的策略。

## 1. 基于 Flink CEP 的模式检测
使用 Flink CEP 检测非流量型或基于特征的攻击（例如：特定的 TCP 标识或源 CIDR 范围）。

```java
Pattern<FlowRecord, ?> pattern = Pattern.<FlowRecord>begin("start")
    .where(new SimpleCondition<FlowRecord>() {
        @Override
        public boolean filter(FlowRecord r) {
            // 特征模型：特定 TCP 标识 + CIDR 范围
            return r.getTcpFlags() == SYN_FLAG && r.getSrcIp().startsWith("10.0.0.");
        }
    })
    .times(100)
    .within(Time.seconds(5)); // 5 秒内检测到

CEP.pattern(flowStream, pattern).select(new AlertSelector());
```

## 2. 阈值特征模型 (BPS & PPS)
支持基于攻击模型的多维度阈值。

| 攻击类型 | 特征维度 | 阈值 (BPS/PPS) |
| :--- | :--- | :--- |
| **流量洪泛** | `dst_ip` | 1 Gbps / 500k PPS |
| **SYN 洪泛** | `dst_ip` + `TCP_SYN` | 100k PPS |
| **端口扫描** | `src_ip` + `COUNT(dst_port)` | > 100 端口/分钟 |
| **DNS 反射** | `src_port=53` + `dst_ip` | 500 Mbps |

## 3. 攻击上下文提取
触发告警时，应包含以下信息：
*   **攻击名称/ID**: 例如 "SYN_FLOOD_101"
*   **目标范围**: 受害者 CIDR (例如 1.2.3.0/24)
*   **关键维度**: 目的端口、TCP 标识、IP 协议。
*   **可溯源性**: 标记是实时检测还是基于批处理的报告。
