# 数据库版本管理规范 (Flyway)

为了确保微服务在开发、测试和生产环境下的数据库 Schema 保持高度一致，必须使用 Flyway 进行版本化管理。

## 1. 核心原则
*   **严禁手动执行**: 生产环境和测试环境的 SQL 变更严禁手动执行，必须通过代码触发。
*   **不可变性**: 一旦 V 版本脚本提交并执行，严禁修改其内容。若需变更，必须新建一个版本号更高的脚本。
*   **与代码同行**: 数据库变更脚本必须与微服务源码存放在同一个 Git 仓内。

## 2. 目录结构
脚本存放在 `src/main/resources/db/migration` 目录下。

*   **V版本脚本 (Versioned)**: `V1.0.1__init_user_table.sql`（用于结构变更）。
*   **R版本脚本 (Repeatable)**: `R__upsert_default_config.sql`（用于可重复执行的数据初始化，如配置项）。

## 3. 命名规范
`V<Version>__<Description>.sql`
*   `Version`: 推荐使用语义化版本号（如 1.0.1）或时间戳（如 202603281000）。
*   `__`: 两个下划线。
*   `Description`: 简短描述变更内容（下划线分隔词）。

## 4. Spring Boot 配置
```yaml
spring:
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true # 针对存量库自动创建基准
    clean-disabled: true     # 生产环境严禁清空数据库
```

## 5. 开发建议
*   在本地开发时，启动应用即自动执行 SQL 迁移。
*   如果执行失败，根据 `flyway_schema_history` 表查看失败的 Checksum 并进行修正。
