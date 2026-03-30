# 基础设施即代码 (IaC) 与自动化验证

将环境配置与发布验证代码化，确保环境的一致性与发布的确定性。

## 1. 基础设施定义 (Terraform / K8s)
所有微服务及其依赖（MySQL, Doris, Redis）必须通过代码定义。

*   **MySQL/Redis**: 使用云平台 Provider (如 AWS, Alibaba Cloud) 的 Terraform 定义。
*   **微服务**: 使用 Kubernetes YAML 或 Helm Chart 描述。
    - **资源限制**: 必须配置 `requests` 和 `limits` (特别是 Flink TaskManager)。
    - **探针**: 配置 `livenessProbe` 和 `readinessProbe`。

## 2. 自动化烟雾测试 (Smoke Tests)
部署完成后（特别是金丝雀/蓝绿切换前）必须自动运行。

*   **API 连通性**: 使用 `curl` 或 API 测试工具检查接口返回是否为 200。
*   **核心链路**: 
    - 触发一个模拟报文（使用 `packet_generator.py`）。
    - 查询 Doris 确认 ODS 层是否有新记录。
    - 验证告警推送是否成功。

## 3. 部署后验证脚本 (示例)
```bash
#!/bin/bash
# 验证 user-service 是否就绪
for i in {1..10}; do
  if curl -s http://user-service:8080/actuator/health | grep "UP"; then
    echo "Service is ready."
    exit 0
  fi
  sleep 5
done
echo "Service failed to start."
exit 1
```

## 4. 环境隔离与快照
*   使用 `k8s namespaces` 隔离 Dev/Test/Prod。
*   在测试集成前，使用存储快照恢复测试数据库到基准状态，确保测试结果的可重复性。
