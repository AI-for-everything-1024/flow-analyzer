# Flink 高流量处理模式

## 1. 增量预聚合 (避免 OOM)
不要对高基数 Key 仅使用 `ProcessWindowFunction`。务必使用 `AggregateFunction` 进行预聚合。

```java
DataStream<FlowRecordEnriched> stream = ...;

stream
    .keyBy(r -> r.getDstIp()) // 高基数 Key
    .window(TumblingEventTimeWindows.of(Time.minutes(5)))
    // 将 AggregateFunction (低内存占用) 与 ProcessWindowFunction (丰富元数据) 结合使用
    .aggregate(new TrafficAggregator(), new TrafficSummaryProcessFunction())
    .addSink(new DorisSink(...));

// 聚合器在原地累加 bytes/packets，每个 Key 仅保留一个状态对象。
class TrafficAggregator implements AggregateFunction<FlowRecordEnriched, LongAccumulator, Long> {
    public LongAccumulator createAccumulator() { return new LongAccumulator(); }
    public void add(FlowRecordEnriched value, LongAccumulator acc) { acc.add(value.getBytes()); }
    public Long getResult(LongAccumulator acc) { return acc.get(); }
}
```

## 2. DDoS 检测状态机 (1 分钟周期)
使用 `KeyedProcessFunction` 配合 `ValueState<DdosState>` 追踪攻击生命周期。

*   **攻击开始 (Start)**: 首次 bps > 阈值。发送告警（ID、名称、开始时间）。
*   **攻击持续 (Continuous)**: 若 bps 持续 > 阈值。每隔 1 分钟发送一次“持续”报告。
*   **攻击结束 (End)**: 若 bps < 阈值并持续 N 分钟。发送“结束”报告。

```java
public void processElement(FlowRecord r, Context ctx, Collector<Alert> out) {
    long currentBps = ...; 
    DdosState state = ddosState.value();
    
    if (currentBps > threshold) {
        if (state == null) {
            // 新攻击
            state = new DdosState(ATTACK_START, ctx.timestamp());
            out.collect(new Alert(target, ATTACK_START));
        } else {
            // 检查 1 分钟上报周期是否已到
            if (ctx.timestamp() - state.lastReportTime >= 60000) {
                out.collect(new Alert(target, ATTACK_CONTINUE));
                state.lastReportTime = ctx.timestamp();
            }
        }
    } else if (state != null) {
        // 攻击结束
        out.collect(new Alert(target, ATTACK_END));
        ddosState.clear();
    }
}
```
