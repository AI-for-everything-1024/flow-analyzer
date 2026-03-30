# 技能依赖与研发流转地图 (Skill Topology)

本文档定义了本项目中各 Agent 技能（Skills）之间的协作协议、数据流向以及在全研发周期（SDLC）中的位置。

## 1. 研发流转总览图

```text
[ 需求定义 ] -----------------------> [ 架构与决策 ] ----------------------> [ 数据契约 ]
requirements-and-scenarios        adr-generator                     uniform-service-platform
(BDD, NFR 指标)                   architecture-and-standards        (Protobuf, FlowRecord)
                                                                           |
                                                                           | 依赖 / 引用
       +-------------------------------------------------------------------+
       |                               |                                   |
[ 核心开发 ]                       [ 流处理分析 ]                      [ 安全与质量 ]
uniform-foundation-platform        flow-analytics                    security-and-identity
spring-cloud-alibaba-scaffold      (Flink 增量聚合)                   testing-and-quality
(JDK 17, Flyway, API)                                                (门禁, 覆盖率, 压测)
       |                               |                                   |
       +-------------------------------+-----------------------------------+
                               |
                        [ 交付与运营 ]
                        ci-cd-deployment-ops
                        observability-and-insights
                        data-governance-lifecycle
                        ops-runbooks
```

## 2. 关键依赖链说明

### A. 契约驱动链 (The Contract Chain)
*   **USP (uniform-service-platform)** 是全系统的核心。
*   `flow-analytics` 的 Flink 算子必须引用 USP 生成的 Protobuf 存根。
*   `uniform-gateway` 的认证逻辑必须识别 USP 定义的用户上下文模型。

### B. 质量反馈链 (The Quality Loop)
*   `requirements-and-scenarios` 产出的 **BDD Given/When/Then** 是 `testing-and-quality` 编写自动化用例的唯一来源。
*   `testing-and-quality` 的覆盖率和扫描结果直接决定了 `ci-cd-deployment-ops` 流水线的通过状态。

### C. 运维与观测链 (The Ops Feedback)
*   `observability-and-insights` 定义的埋点规范在 `scaffold` 和 `flow-analytics` 中实施。
*   生产环境告警触发后，运维人员（或 AI）首先查阅 `ops-runbooks` 执行 SOP，同时查阅 `data-governance-lifecycle` 处理存储压力。

## 3. 技能职责边界表

| 阶段 | 技能名称 | 核心产出物 | 下游技能 |
| :--- | :--- | :--- | :--- |
| **Plan** | `requirements-and-scenarios` | BDD 场景描述 | `testing-and-quality` |
| **Design** | `architecture-and-standards` | 库表 DDL, 通讯模式 | `spring-cloud-alibaba-scaffold` |
| **Define** | `uniform-service-platform` | `.proto` 契约, JAR 包 | `flow-analytics`, `scaffold` |
| **Build** | `spring-cloud-alibaba-scaffold` | 微服务源码, Flyway 脚本 | `ci-cd-deployment-ops` |
| **Verify** | `testing-and-quality` | 单元测试, 压测报告 | `ci-cd-deployment-ops` |
| **Run** | `observability-and-insights` | Grafana 面板, 告警策略 | `ops-runbooks` |

## 4. 如何使用此地图
*   **启动新功能时**: 首先调用 `requirements-and-scenarios` 明确需求，然后检查 `uniform-service-platform` 是否需要更新契约。
*   **遇到性能瓶颈时**: 参考 `observability-and-insights` 观察指标，使用 `testing-and-quality` 的压测工具复现，最后参考 `ops-runbooks` 进行调优。
