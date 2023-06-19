# redis-start
docker redis container <br>
```
docker run -p [host port]:[container port] -name [container name] 0ec8ab59a35f
```

docker redis-stat (모니터링)

```
docker run --name redis-stat --link [your redis container id]:redis -p [host port]:63790 -d insready/redis-stat --server redis
```

# 아키텍처
![image](https://github.com/JxxHxxx/redis-start/assets/87173870/2e22a9d0-9e81-46b9-a2a4-13e1d0856aaa)
