# Spring Cloud Alibaba 项目结构 (JDK 17)

## 推荐的模块架构

一个典型的服务 (`service-name`) 应拆分为：

*   **`service-name-api`**: Feign 客户端、DTO（使用 JDK 17 `record`）和通用接口。
*   **`service-name-biz`**: 实现（Spring Boot 应用）、MyBatis Mapper、业务逻辑。
*   **`service-name-common`**: 共享工具类、基础实体和标准响应包装类。

## Maven 依赖管理 (BOM)

在根 `pom.xml` 中，使用以下版本以确保 Spring Cloud Alibaba 与 JDK 17 的兼容性：

```xml
<properties>
    <java.version>17</java.version>
    <spring-boot.version>3.0.x</spring-boot.version>
    <spring-cloud.version>2022.0.x</spring-cloud.version>
    <spring-cloud-alibaba.version>2022.0.0.0</spring-cloud-alibaba.version>
    <mybatis-plus.version>3.5.3.1</mybatis-plus.version>
    <mysql-connector.version>8.0.32</mysql-connector.version>
</properties>
```

## 标准响应模式

利用 `record` 定义不可变响应：

```java
public record Result<T>(int code, String message, T data) {
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "成功", data);
    }
}
```
