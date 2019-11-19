package com.atguigu.sso.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @RequestMapping("toLogin")
    public String toLogin(@RequestParam(value = "redirect_url", required = false)String url, Model model){
        model.addAttribute("url", url);
        return "login";
    }

    @RequestMapping("login")
    public String login(@RequestParam(value = "redirect_url", required = false)String url, HttpServletRequest request, HttpServletResponse response){

        Cookie cookie = new Cookie("token", "xxxxxxxxxxxxxxx");
//        cookie.setDomain("atguigu.com");
//        cookie.setPath("/hello");
        response.addCookie(cookie);
        return "redirect:" + url;
    }
}
