# 质量门禁与自动化检查指标

定义系统上线前必须通过的硬性质量门禁。

## 1. 静态代码分析 (SonarQube)
*   **代码异味 (Code Smells)**: 禁止出现 `Blocker` 或 `Critical` 级别的异味。
*   **重复率**: 全项目平均重复率必须 < 5%。单文件重复率必须 < 10%。
*   **认知复杂度 (Cognitive Complexity)**: 
    - 单个方法复杂度必须 < 15。
    - 单个类复杂度必须 < 200。

## 2. 单元测试深度 (Jacoco & PIT)
*   **行覆盖率 (Line Coverage)**: 核心业务模块必须 > 85%。
*   **分支覆盖率 (Branch Coverage)**: 核心逻辑必须 > 80%。
*   **变异测试 (Mutation Testing)**: 推荐使用 PITest 验证测试质量。变异杀死率 (Mutation Score) 应 > 65%。

## 3. 供应链安全 (SCA)
*   **漏洞扫描**: 使用 `mvn dependency-check:check`。
*   **门禁要求**: 禁止引入任何具有 `High` 或 `Critical` 级别 CVE 漏洞的依赖包。

## 4. 代码风格 (Checkstyle)
*   必须通过 `google_checks.xml` 或项目自定义的 Checkstyle 验证。
*   严禁在 `main` 分支代码中出现未使用的导入、多余的空格或非标准的命名。
