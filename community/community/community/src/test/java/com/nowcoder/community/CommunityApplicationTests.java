package com.nowcoder.community;


import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class CommunityApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
/*	@Test
	public void testApplicationContext() {

		System.out.println(applicationContext);
		//第一种调用方法
		AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);
		System.out.println(alphaDao.select());
		//第二种调用方法
		alphaDao = applicationContext.getBean("MyBatis", AlphaDao.class);	//Bean 的名字是 MyBatis, 类型是 AlphaDao.class
		System.out.println(alphaDao.select());
	}*/
/*	@Test
	public void testBeanManagement() {	// 测试 Bean 管理
		AlphaService alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);
		alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);
	}*/
/*	@Test
	public void testBeanConfig() {
		SimpleDateFormat simpleDateFormat = applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date()));
	}*/

	@Autowired
	// @Autowired 自动注入，下面的句子说明，希望 Spring 容器能够将 AlphaDao 注入到 alphaDao 这个成员变量（属性）中
	@Qualifier("alphaHibernate")
	// @Qualifier 可以制定注入的类型（适用于一个接口多个实现的情况）
	private AlphaDao alphaDao;
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	 @Test
	 // 测试依赖注入
	public void testDI() {
	 	System.out.println(alphaDao);
	 	System.out.println(simpleDateFormat);
	 }
	 // 整个项目由 controller -> service -> dao 实现，互通是通过 @Autowired 实现的
}
