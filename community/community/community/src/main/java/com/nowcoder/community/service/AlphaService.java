package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
// @Service 表示这是一个业务组件
@Scope()
// @Scope 表示在整个程序运行过程中是单例还是多个实例
// @Scope("prototype") 表示多个实例
// 默认情况下是单例的，@Scope() == @Scope("single")
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;
    public AlphaService() {
        System.out.println("实例化AlphaService");
    }
    @PostConstruct
    // @PostConstruct 意味着这个方法会在对象初始化之后调用
    public void init() {
        System.out.println("初始化 AlphaService");
    }

    @PreDestroy
    // @PreDestroy 意味着在对象销毁之前调用
    public void destroy() {
        System.out.println("销毁 AlphaService");
    }
    // 这个例子告诉我们， 默认情况（@Scope() 或 @Scope("single")）下Bean 只创建一次，只销毁一次，是单例的

    public String find() {
        return alphaDao.select();
    }
}
