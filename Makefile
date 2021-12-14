docker build -t tochat-api .
docker tag tochat-api ccr.ccs.tencentyun.com/ychj/ychj:tochat-api-v1.0.1
docker push ccr.ccs.tencentyun.com/ychj/ychj:tochat-api-v1.0.1