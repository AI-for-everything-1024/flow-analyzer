# 测试金字塔与验证规范

定义多层级的自动化测试体系，确保代码质量与系统稳定性。

## 1. 单元测试 (Unit Tests)
*   **目标**: 验证最小可测试单元（方法、类）的逻辑正确性。
*   **工具**: JUnit 5, Mockito, AssertJ。
*   **规范**: 
    - 隔离外部依赖（使用 Mock）。
    - 关键逻辑行覆盖率必须 > 80%。
    - 遵循 FIRST 原则（Fast, Independent, Repeatable, Self-Validating, Timely）。

## 2. 集成测试 (Integration Tests)
*   **目标**: 验证多个组件或模块间的交互（如：DB 访问、Feign 调用）。
*   **工具**: Testcontainers (Redis/MySQL/Kafka), RestAssured。
*   **规范**: 
    - 使用真实的容器环境而非 H2，以保证环境的一致性。
    - 验证 API 合约与数据持久化逻辑。

## 3. 端到端测试 (E2E/Smoke Tests)
*   **目标**: 验证核心业务链路的完整性。
*   **工具**: Selenium, Playwright 或基于脚本的接口拨测。
*   **规范**: 部署后自动运行，作为发布前的最后一道防线。
