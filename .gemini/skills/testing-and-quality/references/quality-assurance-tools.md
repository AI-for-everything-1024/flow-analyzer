# 质量保障工具配置指南

为项目提供可落地的质量检查工具配置。

## 1. 覆盖率与变异测试 (Maven)
在 `pom.xml` 中配置 Jacoco 和 PIT：

```xml
<!-- Jacoco 覆盖率 -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.8</version>
    <executions>
        <execution>
            <goals><goal>prepare-agent</goal></goals>
        </execution>
    </executions>
</plugin>

<!-- PIT 变异测试 -->
<plugin>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-maven</artifactId>
    <version>1.9.0</version>
    <configuration>
        <targetClasses>
            <param>com.uniform.service.flow.decoder.*</param>
        </targetClasses>
    </configuration>
</plugin>
```

## 2. 依赖项安全扫描
运行以下命令生成安全报告：
```bash
mvn org.owasp:dependency-check-maven:check
```

## 3. 复杂度审计 (IDEA/Sonar)
*   **开发阶段**: 推荐安装 `MetricsReloaded` 或 `Cognitive Complexity` 插件。
*   **流水线阶段**: SonarQube 会自动扫描并应用 [quality-gates.md](quality-gates.md) 中定义的门禁。

## 4. 自动化质量报告
每次 CI 构建后，必须生成统一的 HTML 报告，汇总：
- 测试通过率
- 代码覆盖率
- 复杂度热点
- 安全漏洞列表
