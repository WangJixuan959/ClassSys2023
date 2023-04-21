# ClassSys2023

## 开发环境：

jdk17

node v16.12.0

### 1.代码托管

```bash
git clone git@github.com:WangJixuan959/ClassSys2023.git
```

### 2.中间件

#### （1） redis

用作缓存中间件

#### （2）rabbitmq
http://localhost:15672/
#### （3）mysql

### 3.打包部署

1. 配置application.yml文件
2. 生成jar包
3. 部署在服务器上

### 4.代码维护

```bash
# 切换分支并与远程同步
git checkout dev
git pull

# 在dev分支创建自己的分支branch           
git checkout -b mybranch    
# 本地分支push到远程 
git push origin mybranch

# 修改代码并提交
git add .
git commit -m "commit something"


```

