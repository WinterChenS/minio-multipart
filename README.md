
## 介绍
springboot集成minio实现了分片上传功能


vue版本的：[前端](https://github.com/WinterChenS/airportal-frontend) [后端](https://github.com/WinterChenS/airportal)

## 快速开始

### 后端

修改配置文件`application.yml`:
```yaml
minio:
  endpoint: 
  accessKey: 
  secretKey: 
  bucketName: 
  downloadUri: #配置下载的ip和端口
  path: #如果生产环境配置nginx域名解析，这里可以配置分片上传的ip和端口或者域名
```

### 前端页面

修改`frontend/js/upload.js`:
```javascript

'http://127.0.0.1:8080/file/multipart/create'

'http://127.0.0.1:8080/file/multipart/complete'

```
改为你的后端地址即可

## 使用
在系统文件下打开`frontend/upload.html`运行，选择大于5m的文件上传。
