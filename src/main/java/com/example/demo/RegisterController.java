package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
public class RegisterController {


//    @GetMapping(value = "/WW_verify_4eI0wdeqX1BzQA6s.txt")
//    @ResponseBody
//    public String key(){
//        return "4eI0wdeqX1BzQA6s";
//    }

    @GetMapping(value = "/")
    public String index(){
        return "index";
    }

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }
    @GetMapping(value = "/start")
    public String start(){ return "start";}

    @GetMapping("/show")
    public String showImg(@RequestParam Map<String, Object> map, Model model){
        try{
            String code = (String) map.get("code");
            RestTemplate restTemplate = new RestTemplate();
            AccessToken accessToken = restTemplate.getForObject(
                    "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={corpid}&corpsecret={corpsecret}",
                    AccessToken.class,
                    "ww5f3c78aa1ea4bbdc",
                    "TkhfuhzJVwI8S_j033gnrnAu6N5G2jA9OeAHbghmIVg");
            String access_token = accessToken.getAccess_token();

            ResponseEntity<String> userId = restTemplate.getForEntity(
                    "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token={access_token}&code={code}",
                    String.class,
                    access_token,
                    code);
            JSONObject jsonObject1 = JSONObject.parseObject(userId.getBody());
            String userid = (String) jsonObject1.get("UserId");
            String errmsg = (String) jsonObject1.get("errmsg");
            String jsonString = restTemplate.getForObject(
                    "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token={access_code}&userid={userid}",
                    String.class,
                    access_token,
                    userid);
            JSONObject jsonObject2 = JSONObject.parseObject(jsonString);


            System.out.println(userId.getBody());
            System.out.println(map);
            System.out.println(access_token);
            System.out.println(code);
            System.out.println(userid);
            String url = (String)jsonObject2.get("avatar");
            String name = (String)jsonObject2.get("name");
            model.addAttribute("errmsg",errmsg);
            model.addAttribute("content", name);
            model.addAttribute("url", url);

            return "login";
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
