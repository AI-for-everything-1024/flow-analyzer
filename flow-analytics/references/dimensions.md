# 维度补全与多维溯源

## 1. 维度溯源
为了追踪攻击来源，实时对流量进行补全。

*   **AS 号 (ASN)**: 将 IP 映射到 BGP 源 AS (源和目的)。
*   **地理位置**: 将 IP 映射到国家、省份和城市。
*   **基础设施**: 将 Exporter IP 映射到路由器名称和位置。
*   **逻辑接口**: 将路由器 + 索引映射到接口名称 (入/出)。

## 2. 补全架构
*   **广播状态 (Broadcast State)**: 用于较小的元数据，如路由器和接口映射。
*   **基于 AsyncIO 的 Redis 缓存**: 用于高并发的 IP 到地理位置/ASN 的查找。
*   **Flink 侧输入 (Side-Inputs)**: 用于静态维度数据。

## 3. 实时取证查询 (最近 24 小时)
使用 Doris DWD 层对最近 24 小时的记录进行深度钻取。

```sql
-- 维度取证：攻击来自哪个国家？
SELECT src_country, src_province, SUM(bytes) as vol
FROM dwd_flow_enriched
WHERE dst_ip = '1.2.3.4' AND timestamp > NOW() - INTERVAL 1 DAY
GROUP BY src_country, src_province
ORDER BY vol DESC;

-- 接口分析：哪个路由器的哪个接口看到了流量激增？
SELECT router_id, in_if_name, SUM(packets) as pps
FROM dwd_flow_enriched
WHERE timestamp > NOW() - INTERVAL 1 HOUR
GROUP BY router_id, in_if_name
ORDER BY pps DESC;
```
