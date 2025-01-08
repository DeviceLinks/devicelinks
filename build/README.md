构建打包目录.

> 生成CA证书：./generate-CA.sh
> <br/>
> 生成客户端证书：./generate-CA.sh client {用户名}

## Docker
### 核心服务
```bash
cd ..
docker build -f build/docker/Dockerfile.develop.core-service -t devicelinks/core-service:latest . --load
```
### 控制台
```bash
cd ..
docker build -f build/docker/Dockerfile.develop.console -t devicelinks/console:latest . --load
```


