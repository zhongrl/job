package cn.xn.freamwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/freamwork")
public class FrameworkController {


    @RequestMapping("/test")
    public String test() {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));

        return "test";
    }


    public static void main(String[] args) {

        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }


}
