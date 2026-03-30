# 数据契约与 Protobuf 规范

定义高性能、跨语言的数据交换格式，确保 Schema 演进的兼容性。

## 1. 为什么选择 Protobuf
*   **性能**: 序列化体积小，解析速度极快，适合处理高吞吐量的 `FlowRecord`。
*   **契约化**: 通过 `.proto` 文件定义严格的字段类型，自动生成 Java 代码。
*   **兼容性**: 支持向后兼容（添加新字段不影响旧代码解析）。

## 2. Proto 文件定义规范
存放在 `usp-flow-model` 模块的 `src/main/proto` 目录下。

```protobuf
syntax = "proto3";
package com.uniform.service.flow.proto;

option java_multiple_files = true;

message FlowRecordProto {
    string protocol_type = 1;
    int64 timestamp = 2;
    string src_ip = 3;
    string dst_ip = 4;
    int32 src_port = 5;
    int32 dst_port = 6;
    // ... 其他字段
}
```

## 3. Schema 演进规则
*   **严禁修改 ID**: 禁止更改已分配的标签 ID（如 `protocol_type = 1`）。
*   **只增不删**: 增加新字段时，使用新的 ID。弃用字段应保留 ID，标记为 `[deprecated=true]`。
*   **默认值意识**: 记住 Protobuf 3 不支持区分“未设置”和“默认值”，设计模型时需考虑此特性。

## 4. 序列化工作流
1.  解析器产出 Java 对象。
2.  通过生成的 `FlowRecordProto.newBuilder()` 转化为二进制。
3.  推送到 Kafka。
4.  Flink 消费者使用生成的代码进行极速反序列化。
