# 从 BDD 场景到测试用例映射

建立业务需求与测试验证之间的强关联。

## 1. 映射逻辑
每个 [requirements-and-scenarios] 中定义的 BDD 场景必须映射到至少一个测试用例。

*   **BDD Given** -> 测试初始化 (Setup / Mock data)。
*   **BDD When** -> 触发操作 (Execution / Method call)。
*   **BDD Then** -> 断言验证 (Assertion)。

## 2. 测试覆盖率管理
*   **场景覆盖**: 确保所有正向、负向和破坏性场景都有对应的自动化脚本。
*   **追溯矩阵**: 在测试报告中体现该测试对应的 User Story ID。

## 3. 数据驱动测试 (Data-Driven)
*   针对解析类逻辑（如 NetFlow），使用 `packet-samples.md` 中的多组 Hex 数据驱动同一个测试方法，验证不同版本的兼容性。
