<<<<<<< HEAD
# Gemini CLI 项目工作区

这是一个由 Gemini CLI 辅助驱动的自动化开发与架构工作区。本项目集成了从底层网络协议解析到上层大数据分析、微服务架构、数据治理以及自动化运维的全栈能力。

## 项目结构概览

```text
.
├── pom.xml                        # 父 POM (BOM 版本管理)
├── adr-generator/                 # 架构决策记录 (ADR) 生成器
├── architecture-and-standards/    # 4+1 视图、SOLID 与数据库设计规范
├── ci-cd-deployment-ops/          # 流水线、质量门禁与部署运维
├── data-governance-lifecycle/     # 数据分区管理、冷热分离与 TTL 策略
├── flow-analytics/                # Flink 增量聚合与 DDoS 检测
├── observability-and-insights/    # Metrics, Tracing, Logging 统一规范
├── ops-runbooks/                  # 故障排查 SOP 与诊断工具指南
├── requirements-and-scenarios/    # 用户故事与 BDD 需求定义
├── security-and-identity/         # OAuth2.0/JWT、RBAC 与数据加密
├── spring-cloud-alibaba-scaffold/ # 微服务脚手架与 Flyway 规范
├── testing-and-quality/           # 单元/集成/压力测试与变异测试
├── uniform-foundation-platform/   # 基础平台库 (JwtTokenUtil, StrUtil 等)
├── uniform-service-platform/      # 业务平台库 (Flow 解析、归一化、Protobuf)
├── uniform-gateway/               # 微服务网关模块 (AuthGlobalFilter)
├── GEMINI.md                      # Gemini CLI 指令上下文
└── README.md                      # 项目说明文档
```

## 已集成的 Agent 技能 (Skills Matrix)

| 类别 | 技能名称 | 核心职责 | 技术栈 |
| :--- | :--- | :--- | :--- |
| **治理** | **adr-generator** | 记录架构决策历史 (ADR) | Markdown, Git |
| **治理** | **architecture-and-standards** | 4+1 视图、库表设计、通讯模式 | MySQL, Doris |
| **需求** | **requirements-and-scenarios** | 用户故事 (INVEST)、BDD (G/W/T) | BDD, NFR 模板 |
| **平台库**| **uniform-foundation-platform** | 通用工具类库 (类似于 Hutool) | Java JAR |
| **平台库**| **uniform-service-platform** | 解析引擎、归一化模型、数据契约 | Netty, Protobuf |
| **微服务**| **spring-cloud-alibaba-scaffold**| 脚手架、Flyway、OpenAPI | Spring Cloud, Nacos |
| **大数据**| **flow-analytics** | Flink 预聚合、DDoS CEP 检测 | Flink, RocksDB |
| **数据运维**| **data-governance-lifecycle** | 数据生命周期管理、分区 TTL | Doris, S3 |
| **安全** | **security-and-identity** | 认证 (OAuth2)、授权 (RBAC) | JWT, AES |
| **质量** | **testing-and-quality** | 单元/压力测试、覆盖率门禁 | JUnit 5, k6, PIT |
| **观测** | **observability-and-insights** | Metrics, Tracing, Logging | Prometheus, OTEL |
| **运维** | **ci-cd-deployment-ops** | 质量门禁、IaC、自动化拨测 | K8s, Terraform |
| **运维** | **ops-runbooks** | 故障排查 SOP、Arthas 诊断 | SOP, Arthas |

## 快速开始

1.  **加载技能**: 在 Gemini CLI 中运行 `/skills reload`。
2.  **构建环境**: 运行 `export JAVA_HOME=/Library/Java/JavaVirtualMachines/zulu-17.jdk/Contents/Home && mvn clean install`。
3.  **核心工作流示例**: 
    *   “*基于数据契约规范，在 USP 中新增 IPv6 的 Protobuf 定义*”
    *   “*参照观测规范，为 Flink 任务配置 P99 延迟监控指标*”
    *   “*根据数据治理技能，为 Doris ODS 表配置冷热分离策略*”

## 维护建议

项目采用“**技能即文档**”的模式。请确保每一次重大的架构或流程变更都同步更新至对应技能的 `references/` 目录并重新打包。
=======
# flow-analyzer
>>>>>>> 57761d916aa7a4e748b7ec3e612c866be345482b
