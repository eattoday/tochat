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

## Quartz

1.配置 pom.xml
