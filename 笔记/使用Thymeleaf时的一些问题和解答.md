#### Thymeleaf中 `href` 和  `th:href` 的区别

语法格式如下

```html
<a th:href="@{/channel/page/add}">添加渠道 </a>
<a href="/channel/page/add">添加渠道 </a>
```

在 **默认项目路径为空** 时，打Jar包 **单独运行** 时。二者效果 **一致** 。

在使用 **Maven 内嵌 Tomcat** 或 **打War包部署到Servlet容器** ，或者 **在项目内执行App启动类，且有配置项目路径时** ，二者区别如下：

- href始终从 **端口** 开始作为根路径，如 `http://localhost:8080/channel/page/add`

- th:href会寻找 **项目路径** 作为根路径，如 `http://localhost:8080/dx/channel/page/add`

**参考自：** [Thymeleaf中href与 th:href的区别](https://www.cnblogs.com/q924152020/p/10602807.html)