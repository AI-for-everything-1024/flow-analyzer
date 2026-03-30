# ADR-0002: 选择 Flink 滑动窗口模型检测 DNS 放大攻击

### 状态 (Status)
已接受 (Accepted)

### 日期 (Date)
2026-03-29

### 背景 (Context)
DNS 放大攻击（DNS Amplification）是一种典型的利用 UDP 无连接特性进行的容量型攻击。我们需要一种能平衡“检测精度”与“内存消耗”的方法，实时识别出流量中的异常放大倍率（通常 > 10倍）。

### 决定 (Decision)
我们将采用 Flink 的 **Sliding EventTime Window (窗口 10s, 步长 1s)** 结合自定义的 `AggregateFunction` 来实施检测。
1.  **多指标聚合**: 同时追踪受害者 IP 的入向/出向 bytes。
2.  **动态比例计算**: 在 `ProcessWindowFunction` 中计算 `bytes_in / bytes_out`。
3.  **状态保护**: 仅对 UDP 端口 53 的流量开启此计算分支，以节省 RocksDB 状态开销。

### 后功 (Consequences)
*   **正面影响**: 秒级检测延迟；通过 EventTime 保证了在数据乱序情况下的检测准确性。
*   **负面影响**: 窗口重叠会导致算子 CPU 负载略微上升。

### 后续工作
*   在 `uniform-service-platform` 中更新 `DdosAlertProto` 定义。
