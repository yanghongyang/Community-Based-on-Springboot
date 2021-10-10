package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

@Service
// @Service 表示这是一个业务组件
@Scope()
// @Scope 表示在整个程序运行过程中是单例还是多个实例
// @Scope("prototype") 表示多个实例
// 默认情况下是单例的，@Scope() == @Scope("single")
public class AlphaService {

    private static final Logger logger = LoggerFactory.getLogger(AlphaService.class);

    @Autowired
    private AlphaDao alphaDao;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

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

    // 两种事务隔离的实现
    /*  Propagation 的常用常量
        REQUIRED(0), 支持当前事务（调用者，也叫做外部事务），如果不存在则创建新事务
        REQUIRES_NEW(3), 创建一个新事务，并且暂停当前事务（外部事务）
        NESTED(6), 如果当前存在事务（外部事务），则嵌套在该事务中执行（有独立的提交和回滚），否则就和 REQUIRED 一样
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Object save1() {
        // 先增加用户
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5("123" + user.getSalt()));
        user.setEmail("12345@qq.com");
        user.setHeaderUrl("http://image.nowcoder.com/head/99t.png");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);
        // 再增加帖子
        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(user.getId());
        discussPost.setTitle("12346");
        discussPost.setContent("hello");
        discussPost.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(discussPost);

        Integer.valueOf("abc");
        return "ok";
    }

    public Object save2() {
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                // 先增加用户
                User user = new User();
                user.setUsername("alph");
                user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
                user.setPassword(CommunityUtil.md5("123" + user.getSalt()));
                user.setEmail("12345@qq.com");
                user.setHeaderUrl("http://image.nowcoder.com/head/99t.png");
                user.setCreateTime(new Date());
                userMapper.insertUser(user);
                // 再增加帖子
                DiscussPost discussPost = new DiscussPost();
                discussPost.setUserId(user.getId());
                discussPost.setTitle("12346dssf");
                discussPost.setContent("helloasddf");
                discussPost.setCreateTime(new Date());
                discussPostMapper.insertDiscussPost(discussPost);

                Integer.valueOf("abc");
                return null;
            }
        });
    }

    // 可以让该方法在多线程环境下，被异步调用
    @Async
    public void execute1() {
        logger.debug("execute1");
    }

    /*@Scheduled(initialDelay = 10000, fixedRate = 1000)
    public void execute2() {
        logger.debug("execute2");
    }*/
}
