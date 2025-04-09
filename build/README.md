构建打包目录.

> 生成CA证书：./generate-CA.sh
> <br/>
> 生成客户端证书：./generate-CA.sh client {用户名}

## Docker
### 设备中心
```bash
cd ..
docker build -f build/docker/Dockerfile.develop.device-center -t devicelinks/device-center:latest . --load
```
### 控制台
```bash
cd ..
docker build -f build/docker/Dockerfile.develop.console -t devicelinks/console:latest . --load
```


