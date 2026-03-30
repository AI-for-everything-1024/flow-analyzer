# MyBatis & MySQL 8 最佳实践 (JDK 17)

## Record 映射
JDK 17 的 `record` 类型非常适合作为 MyBatis 的 DTO。请确保使用 MyBatis 3.5.10+ 以获得原生 `record` 支持。

```java
public record UserRecord(Long id, String username, String email) {}
```

## MyBatis Plus 配置 (推荐)
使用 MyBatis Plus 减少样板代码，同时保持对 MySQL 8 特性的兼容性。

```java
@Configuration
@MapperScan("com.example.mapper")
public class MyBatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
```

## MySQL 8 连接
始终使用 `com.mysql.cj.jdbc.Driver` 并正确指定时区。

```yaml
spring:
  datasource:
    url: jdbc:mysql://${DB_ADDR:localhost:3306}/db_name?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
```
