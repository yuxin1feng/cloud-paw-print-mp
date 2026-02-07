# cloud-paw-print-mp
# 宠物云养互动与知识服务平台

## 项目简介
基于微信小程序的宠物云养互动平台，支持内容发布、监督提醒、积分系统与 AI 辅助问答。

## 技术栈
- 前端：微信小程序（原生）
- 后端：Java 17 + Spring Boot
- 数据库：MySQL（后续接入）
- AI：第三方大模型 API（初期 Mock）

## 运行环境
- JDK 17+
- Maven 3.8+
- 微信开发者工具

## 后端启动
```
cd server
mvn spring-boot:run
```

## 前端启动
- 使用微信开发者工具打开 `miniprogram/` 目录
- 小程序 AppID：`touristappid`

## 仓库结构
- `miniprogram/`：微信小程序前端代码
- `server/`：Spring Boot 后端服务
- `docs/`：系统设计文档与论文相关材料

