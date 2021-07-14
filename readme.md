### 使用普罗米修斯对hikari链接池进行埋点

本意是监控到链接池的每个连接生命周期，但是hikariCP只提供了基本的指标

- hikaricp_connections_usage_seconds_count
- hikaricp_connections_usage_seconds_sum
- hikaricp_connections_usage_seconds_max
- hikaricp_connections_creation_seconds_max
- hikaricp_connections_creation_seconds_count
- hikaricp_connections_creation_seconds_sum
- hikaricp_connections_acquire_seconds_count
- hikaricp_connections_acquire_seconds_sum
- hikaricp_connections_acquire_seconds_max
- hikaricp_connections_pending
- hikaricp_connections
- hikaricp_connections_idle
- hikaricp_connections_max
- hikaricp_connections_timeout_total
- hikaricp_connections_min
- hikaricp_connections_active

#### 环境

|  服务   | 端口  |
|  ----  | ----  |
| prometheus  | 9090 |
| mysql  | 3306 |
| grafana  | 3000 |
| application| 1234|

#### 食用方式

1、docker-compose up

2、执行Application

3、浏览器访问 [http://localhost:1234/metrics] 查看暴露出来的指标数据

4、浏览器访问 [http://localhost:3000] 通过grafana导入plane.json查看指标数据