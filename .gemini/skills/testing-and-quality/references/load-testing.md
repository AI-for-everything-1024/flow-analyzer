# 压力测试脚本编写 (JMeter & k6)

定义如何编写和执行可重复的性能压力测试。

## 1. 工具选择
*   **k6 (推荐)**: 基于 JavaScript，易于集成到 CI/CD，适合开发者。
*   **JMeter**: 功能最全面，支持复杂协议，适合大规模集群压测。

## 2. 脚本设计准则
*   **参数化**: 使用 CSV 或 JSON 文件注入测试数据（如：不同的源 IP、目的端口），避免单一请求导致的缓存命中。
*   **关联**: 提取响应中的 Token 供后续请求使用。
*   **断言**: 必须包含响应状态码（200）和响应时间（如 < 500ms）的断言。
*   **模拟真实场景**: 
    - 阶梯式加压 (Ramping up)。
    - 持续高压 (Soak testing)。
    - 瞬间峰值 (Spike testing)。

## 3. k6 示例脚本 (流量分析接口)
```javascript
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '1m', target: 100 }, // 1分钟内升至100个并发
    { duration: '5m', target: 100 }, // 持续5分钟
    { duration: '1m', target: 0 },   // 1分钟内降至0
  ],
};

export default function () {
  const res = http.get('http://api-gateway/flow/query?dstIp=1.2.3.4');
  check(res, { 'status was 200': (r) => r.status == 200 });
  sleep(0.1); // 模拟思考时间
}
```
