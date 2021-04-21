# /bin/bash

kubectl create secret docker-registry aliyun-secret \
    --namespace=beyond \
    --docker-username=xxxx \
    --docker-password=xxxx \
    --docker-server=registry.cn-shenzhen.aliyuncs.com
