package com.nowcoder.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository("MyBatis")
//可以自定义 Bean 的名字，只需要在 @Repository("name") 的 name 里写上名字就行了
@Primary
//加上 @Primary 之后，即便一个接口有多个实现，在获取 Bean 的时候也会优先执行有 @Primary 的类（避免了一个接口多重实现的混乱）
public class AlphaDaoMyBatisImpl implements AlphaDao{
    @Override
    public String select() {
        return "MyBatis";
    }
}
