# 认证、鉴权快速测试案例

## 一、前期准备

### 1.1 创建数据库表 

创建数据库表（这里仅创建认证鉴权必要的字段，其余的功能字段自行添加，以及修改实体类）
```sql
create table `sys_user` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
	`user_name` VARCHAR(100) NOT NULL COMMENT '用户名',
	`password` VARCHAR(100) NOT NULL COMMENT '密码',
	PRIMARY KEY(`id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户表'

CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_key` varchar(100) DEFAULT NULL COMMENT '角色权限字符串',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '菜单id',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关系表';

CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系表';

-- 创建一个用户，用户名为 admin, 密码为 123456
insert into sys_user values(1,'admin','$2a$10$jZv9z/T0R9PCb/zcjpMX8uZ.nagnH3ssyn1cU5XOitBlJd.EMv52W');
-- 创建2个角色分别为 r1,r2
insert into sys_role values(1,'r1'),(2,'r2');
-- 创建3个权限分别为 hello,hello2,hello3
insert into sys_menu values(1,'hello'),(2,'hello2'),(3,'hello3');
-- 用户 admin 拥有 r1 角色
insert into sys_user_role values(1,1);
-- 角色 r1 拥有 hello2 权限
insert into sys_role_menu values(1,2);
```

> 如果想自己添加用户，密码字段由于是需要加密的，可以使用测试类 `src/test/java/com/securitydemo/config/SecurityConfigTest.java` 来进行获取：
>
> ```java
> @SpringBootTest
> public class SecurityConfigTest {
> 
>     @Test
>     public void testBCryptPasswordEncoder() {
>         final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
>         final String encode = passwordEncoder.encode("123456");
>         System.out.println(encode);
>         final boolean matches = passwordEncoder.matches("123456", encode);
>         System.out.println(matches);
>     }
> }
> ```



### 1.2 配置数据库信息

修改 `src/main/resources/application.yml` 中的数据库连接信息

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/springsecuritydemo?characterEncoding=utf-8&serverTimezone=UTC
    username: 用户名
    password: 密码
    driver-class-name: com.mysql.cj.jdbc.Driver
```



### 1.3 创建权限测试接口

在 `com/securitydemo/controller` 包下创建 Controller :

```java
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/hello2")
    @PreAuthorize("hasAuthority('hello2')")
    public String hello2() {
        return "hello2";
    }

    @RequestMapping("/hello3")
    @PreAuthorize("hasRole('r1')")
    public String hello3() {
        return "hello3";
    }

    @RequestMapping("/hello4")
    @PreAuthorize("hasAuthority('hello4')")
    public String hello4() {
        return "hello4";
    }
  
    @RequestMapping("/hello5")
    @PreAuthorize("hasRole('r2')")
    public String hello5() {
        return "hello5";
    }
}
```



## 二、功能测试

启动项目

### 2.1 用户登陆认证

#### 登陆成功

![image-20230109022131185](https://picgo-wbw.oss-cn-hangzhou.aliyuncs.com/picgo-img/image-20230109022131185.png)

#### 登陆失败

输入错误密码：

![image-20230109022210271](https://picgo-wbw.oss-cn-hangzhou.aliyuncs.com/picgo-img/image-20230109022210271.png)



### 2.2 权限校验

#### 2.2.1 无需鉴权（但需要认证）

测试接口：

```java
@RequestMapping("/hello")
public String hello() {
  return "hello";
}
```

##### 未登陆状态下访问

![image-20230109022729525](https://picgo-wbw.oss-cn-hangzhou.aliyuncs.com/picgo-img/image-20230109022729525.png)

##### 登陆状态下访问

根据 2.1 中登陆成功获得到的 token，携带访问：

![image-20230109022905908](https://picgo-wbw.oss-cn-hangzhou.aliyuncs.com/picgo-img/image-20230109022905908.png)

#### 2.2.2 权限码校验

测试接口：

```java
@RequestMapping("/hello2")
@PreAuthorize("hasAuthority('hello2')")
public String hello2() {
  return "hello2";
}

@RequestMapping("/hello4")
@PreAuthorize("hasAuthority('hello4')")
public String hello4() {
  return "hello4";
}
```

用户 admin 拥有 hello2 权限。

##### 拥有权限访问（hello2）

![image-20230109023150757](https://picgo-wbw.oss-cn-hangzhou.aliyuncs.com/picgo-img/image-20230109023150757.png)

##### 没有权限访问（hello4）

![image-20230109023204637](https://picgo-wbw.oss-cn-hangzhou.aliyuncs.com/picgo-img/image-20230109023204637.png)

#### 2.2.3 角色校验

测试接口：

```java
@RequestMapping("/hello3")
@PreAuthorize("hasRole('r1')")
public String hello3() {
  return "hello3";
}

@RequestMapping("/hello5")
@PreAuthorize("hasRole('r2')")
public String hello5() {
  return "hello5";
}
```

##### 拥有权限访问（r1）

![image-20230109023528777](https://picgo-wbw.oss-cn-hangzhou.aliyuncs.com/picgo-img/image-20230109023528777.png)

##### 没有权限访问（r2）

![image-20230109023610679](https://picgo-wbw.oss-cn-hangzhou.aliyuncs.com/picgo-img/image-20230109023610679.png)