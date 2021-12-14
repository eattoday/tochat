# 项目说明

> 此项目为部署测试项目
>
>用来验证配置文件在 SpringBoot 中如何发挥作用

## Mybatis-plus

1.配置 pom.xml

```xml
    <!-- mysql -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    
    <!-- mybatis-plus -->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.4.3.4</version>
    </dependency>
```

2.配置数据源 [applycation-dev.yml](./src/main/resources/application-dev.yml)

3.创建配置文件 [application-config-mybatis.yml](./src/main/resources/application-config-mybatis.yml)

4.创建配置类 [MybatisPlusConfig.java](./src/main/java/com/dawu/tochat/config/MybatisPlusConfig.java)

5.创建代码,此处可以使用插件生成

- 创建 [SpUser.java](./src/main/java/com/dawu/tochat/domain/SpUser.java)
- 创建 [SpUserMapper.java](./src/main/java/com/dawu/tochat/mapper/SpUserMapper.java) 继承 BaseMapper

6.创建sql文件,用来编写自定义sql

- 创建 [SpUserMapper.xml](./src/main/resources/mapper/SpUserMapper.xml)

7.验证测试

- 自动注入SpUserMapper

```java
    @Autowired
    private SpUserMapper spUserMapper;
```

- 使用父类接口实现的方法

```java
    List<SpUser> spUsers=spUserMapper.selectList(null);
```

## Swagger(knife4j)

1.配置 pom.xml

```xml
    <dependency>
        <groupId>com.github.xiaoymin</groupId>
        <artifactId>knife4j-spring-boot-starter</artifactId>
        <version>3.0.3</version>
    </dependency>
```

2.创建配置类 [Knife4jConfiguration.java](./src/main/java/com/dawu/tochat/config/Knife4jConfiguration.java)

3.写入注解

- 类上增加注解

```java
    @Api(tags = "测试模块")
```

- 方法上增加注解

```java
    @ApiOperation(value = "测试1")
    @ApiImplicitParam(name = "tableName", value = "表名", required = true)
```

4.启动工程后[验证测试](http://localhost:8080/doc.html)

5.注:SpringBoot版本对此插件有影响,过高的版本会不兼容

## Log

1.配置 pom.xml

```xml
    <!--  swagger 增强   -->
    <dependency>
        <groupId>com.github.xiaoymin</groupId>
        <artifactId>knife4j-spring-boot-starter</artifactId>
        <version>3.0.3</version>
    </dependency>

    <!--   lombok     -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
```

2.配置 application.yml

```yaml
logging:
  level:
    com.dawu: trace
    org.springframework: warn
  config: classpath:logback.xml
```

3.创建配置文件 [logback.xml](./src/main/resources/logback.xml)

4.在需要使用日志的类上面添加注解

```java
@Slf4j
```

5.在代码中打印日志

```java
    //日志的级别
    //从低到高
    //可以调整输出的日志级别；日志就只会在这个级别以后的高级别生效
    log.trace("这是trace日志");
    log.debug("这是debug信息");
    //SpringBoot默认给的是info级别，如果没指定就是默认的root级别
    log.info("这是info日志");
    log.warn("这是warn信息");
    log.error("这是Error信息");
```

## Scheduled

1.创建配置类 [TestJob.java](./src/main/java/com/dawu/tochat/job/TestJob.java)

2.配置类上增加注解

```java
@EnableScheduling
```

3.编写定时任务方法

```java
    // 定时每分钟提醒提醒
    @Scheduled(cron = "0 * * * * ?")
    private void testJobByCron(){
        log.trace("开始任务 : "+LocalDateTime.now());
        log.trace("定时任务时间为 : "+LocalDateTime.now());
        log.trace("结束任务 : "+LocalDateTime.now());
    }

    // 直接指定时间间隔，例如：5秒   
    @Scheduled(fixedRate = 5000)
    private void testJobByFixedRate(){
        log.trace("开始任务 : "+LocalDateTime.now());
        log.trace("间隔5秒任务 : "+LocalDateTime.now());
        log.trace("结束任务 : "+LocalDateTime.now());
    }
```

## Redisson && Spring Cache

1.配置 pom.xml

```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
```

2.配置数据源 [applycation-dev.yml](./src/main/resources/application-dev.yml)

3.创建配置文件 [application-config-redisson.yml](./src/main/resources/application-config-redisson.yml)

4.创建配置类 [RedisConfig.java](./src/main/java/com/dawu/tochat/config/RedisConfig.java)
  创建配置文件 [RedissonProperties.java](./src/main/java/com/dawu/tochat/config/RedissonProperties.java)

5.创建工具类 [RedisUtils.java](./src/main/java/com/dawu/tochat/util/RedisUtils.java)

6.Redis 使用示例 

```java
    RedisUtils.setCacheObject("testString", "test1");
    String testString = RedisUtils.getCacheObject("testString");  // testString = test1

    Map map=new HashMap();
    map.put("test1","11");
    map.put("test2","22");
    RedisUtils.setCacheMap("testMap", map);
    Map testMap = RedisUtils.getCacheMap("testMap");    // testMap = {"test2":"22","test1":"11"}

    RedisUtils.setCacheList("testList", Arrays.asList("111","222"));
    List testList = RedisUtils.getCacheList("testList");    // testList = ["111","222"]

    Set set=new HashSet();
    set.add("111");
    set.add("222");
    RedisUtils.setCacheSet("testSet", set);
    Set testSet = RedisUtils.getCacheSet("testSet");    // testSet = ["222","111"]
```

7.Cache 使用示例,在需要配置的方法上增加注解

```java
    // 先查看是否存在缓存,若存在则直接获取,若不存在则经过方法读取后存入缓存
    @Cacheable(cacheNames="redissonCacheMap", key="#tableName + #id")
    
    // 直接将结果存入缓存
    @CachePut(cacheNames="redissonCacheMap", key="#tableName + #id")
    
    // 删除对应缓存
    @CacheEvict(cacheNames="redissonCacheMap", key="#tableName + #id")
```

## SpringBoot Admin

1.服务端配置 pom.xml

```xml
   <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <dependency>
        <groupId>de.codecentric</groupId>
        <artifactId>spring-boot-admin-starter-server</artifactId>
    </dependency>
```

2.服务端配置 application.yml

```yaml
server:
  port: 9090
spring:
  security:
    user:
      name: ruoyi
      password: 123456
  boot:
    admin:
      context-path: /admin
```

3.服务端启动类增加注解(目前看来不加也可以)

```java
@EnableAdminServer
```

4.客户端配置 pom.xml

```xml
    <!--springboot Admin 客户端-->
    <dependency>
        <groupId>de.codecentric</groupId>
        <artifactId>spring-boot-admin-starter-client</artifactId>
        <version>2.5.1</version>
    </dependency>
```

5.客户端配置 application.yml

```yaml
--- # Spring Boot Admin Client 客户端的相关配置
spring:
  boot:
    admin:
      client:
        # 增加客户端开关
        enabled: true
        # 设置 Spring Boot Admin Server 地址
        url: http://localhost:9090/admin
        instance:
          prefer-ip: true # 注册实例时，优先使用 IP
        username: ruoyi
        password: 123456
        
management:
  endpoints:
    web:
      exposure:
        include: '*'
  endpoint:
    health:
      show-details: ALWAYS
```

6.启动两个工程,访问服务端 [验证测试](http://localhost:9090/admin)

## Quartz

1.配置 pom.xml
