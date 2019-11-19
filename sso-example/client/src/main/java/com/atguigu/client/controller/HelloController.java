package com.atguigu.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String toHello(Model model, @CookieValue(value="token", required = false)String token, HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        if (StringUtils.isEmpty(token)){
            String url = request.getRequestURL().toString();
            response.sendRedirect("http://sso.atguigu.com:8081/toLogin?redirect_url=" + url);
        }
        model.addAttribute("msg", "token: " + token);
        return "hello";
    }

    @GetMapping("hello1")
    public String toHello1(Model model, @CookieValue(value="token", required = false)String token, HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        if (StringUtils.isEmpty(token)){
            String url = request.getRequestURL().toString();
            response.sendRedirect("http://sso.atguigu.com:8081/toLogin?redirect_url=" + url);
        }
        model.addAttribute("msg", "token: " + token);
        return "hello";
    }
}
