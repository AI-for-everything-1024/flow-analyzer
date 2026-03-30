# UFP 核心模块与能力规范

Unified Foundation Platform (UFP) 是一个与业务无关的通用 Java 工具库。

## 1. 核心模块划分
*   **`ufp-core`**: 包含字符串处理 (`StrUtil`)、集合增强 (`CollUtil`)、反射工具 (`ReflectUtil`) 及 Bean 拷贝。
*   **`ufp-net`**: 封装高层级的 TCP/UDP 客户端与服务端，提供 IP 处理工具 (`NetUtil`)。
*   **`ufp-crypto`**: 统一的加密解密入口（支持 AES, RSA, BCrypt, SM2/3/4）。
*   **`ufp-json`**: 基于高性能 JSON 库（如 Jackson/Fastjson2）的二次封装。
*   **`ufp-concurrent`**: 自定义线程池工厂、分布式锁抽象接口。

## 2. 设计原则
*   **零依赖 (Low Dependency)**: 核心模块尽量不依赖第三方库，或将其设为 `optional`。
*   **流式编程 (Fluent API)**: 尽可能提供链式调用方法。
*   **线程安全**: 所有工具类方法必须是无状态且线程安全的。
*   **高性能**: 关键路径（如 Hex 转换、Byte 读写）应进行极致优化。

## 3. 使用示例 (StrUtil)
```java
// 判断非空且非空白
if (StrUtil.isNotBlank(str)) { ... }
// 格式化字符串
String msg = StrUtil.format("Hello {}, current time is {}", name, DateUtil.now());
```
