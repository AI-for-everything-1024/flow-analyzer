# GEMINI.md - 指令上下文与自动编排规则

本文档为 Gemini CLI 提供本项目全局背景，并定义了技能（Skills）的自动激活与编排规则。

## 1. 项目背景
本项目是一个高性能网络流量分析系统，基于微服务架构（Spring Cloud Alibaba）和流式计算（Flink），涉及大规模数据存储（Doris）与严格的质量门禁。

## 2. 自动编排规则 (Implicit Orchestration Rules)

当执行以下操作时，我必须主动联想并根据需要激活对应的技能组：

### A. 需求与设计阶段 (Plan & Design)
*   **指令包含“新功能”、“业务逻辑”**: 必须联想 `requirements-and-scenarios`（查阅业务用例）。
*   **指令包含“模块”、“接口”、“通讯”**: 必须联想 `architecture-and-standards`（检查通讯模式与 SOLID 原则）。
*   **涉及“技术选型”或“重大变更”**: 必须提醒并引导使用 `adr-generator`。

### B. 开发与数据契约阶段 (Development & Contract)
*   **处理 `.proto` 或 `FlowRecord`**: 必须联想 `uniform-service-platform`（确保契约一致性）。
*   **修改 `pom.xml` 或微服务配置**: 必须联想 `spring-cloud-alibaba-scaffold`。
*   **编写 Java 底层工具**: 必须联想 `uniform-foundation-platform`。

### C. 质量与分析阶段 (Quality & Analytics)
*   **执行 `mvn` 命令或代码编写完成**: 必须联想 `testing-and-quality`（核对覆盖率与复杂度门禁）。
*   **涉及“Flink”、“算子”、“指标”**: 必须联想 `flow-analytics` 和 `observability-and-insights`。

### D. 部署与运维阶段 (Ops)
*   **指令包含“发布”、“部署”、“K8s”**: 必须联想 `ci-cd-deployment-ops`。
*   **处理“报错”、“异常”、“性能下降”**: 必须联想 `ops-runbooks` 执行 SOP。
*   **涉及“存储占用”、“Doris 分区”**: 必须联想 `data-governance-lifecycle`。

## 3. 渐进式披露执行原则
在激活任何技能后，我必须遵循“**先读 SKILL.md 导航，再按需读取 references/ 细节**”的原则，严禁一次性加载所有参考文档。
