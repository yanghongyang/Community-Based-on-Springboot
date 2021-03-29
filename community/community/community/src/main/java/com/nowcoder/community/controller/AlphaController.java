package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {
    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }
    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }
    @RequestMapping("/http")
    public void http(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        // 获取请求数据
        System.out.println(httpServletRequest.getMethod());
        System.out.println(httpServletRequest.getServletPath());
        Enumeration<String> enumeration = httpServletRequest.getHeaderNames();
        while(enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = httpServletRequest.getHeader(name);
            System.out.println(name + " : " + value);
        }
        System.out.println(httpServletRequest.getParameter("code"));
        // 返回响应数据
        httpServletResponse.setContentType("text/html; charset=utf-8");
        try (PrintWriter printWriter = httpServletResponse.getWriter()) {
            printWriter.write("<h1> 牛客网</h1>");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // GET 请求
    // 查询学生，当前第1页，每页20个学生
    // /students?current=1&limit=20
    // 只能处理 GET 请求（method = RequestMethod.GET）
    // 只要方法的参数和 ?之后的参数相同即可(current, limit)
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(@RequestParam(value = "current", required = false, defaultValue = "1") int current, @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        System.out.println(current + ";" + limit);
        return "some students";
    }

    // /student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }

    // POST 请求
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age){
        System.out.println(name + " " + age);
        return "Save success!";
    }

    // 响应 html 数据
    // 不加 @ResponseBody 默认返回 html
    // 方法一
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", "张三");
        modelAndView.addObject("age", "22");
        modelAndView.setViewName("/demo/view");  //这里找的是resources/templates下的文件
        return modelAndView;
    }
    // 方法二
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name", "BUAA");
        model.addAttribute("age", "53");
        return "/demo/view";
    }

    // 响应 JSON 数据
    // 通常用于响应异步数据
    // Java对象 通过 JSON对象 转成 JS 对象
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("salary", 8000.00);
        emp.put("age", 23);
        return emp;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("salary", 8000.00);
        emp.put("age", 23);
        list.add(emp);
        emp.put("name", "张五");
        emp.put("salary", 5000.00);
        emp.put("age", 24);
        list.add(emp);
        emp.put("name", "张四");
        emp.put("salary", 3000.00);
        emp.put("age", 29);
        list.add(emp);
        return list;
    }

    // cookie 示例
    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response) {
        // 创建 Cookie
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        // 设置 cookie 生效的范围
        cookie.setPath("/community/alpha"); // 只有在/community/alpha下有效
        // cookie 默认存在浏览器里，浏览器一关闭， cookie 就消失
        // 但是如果经过设置的话，cookie 就会存在硬盘里，长期有效直到过期
        cookie.setMaxAge(60 * 10); // 单位是秒
        // 把 cookie 放到 response 中（发送cookie）
        response.addCookie(cookie);

        return "set cookie";
    }
    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code) {
        System.out.println(code);
        return "get cookie";
    }

    // session 示例
    @RequestMapping(path="/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session) {
        session.setAttribute("id", 1);
        session.setAttribute("name", "Test");
        return "set session";
    }
    @RequestMapping(path="/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }

}
