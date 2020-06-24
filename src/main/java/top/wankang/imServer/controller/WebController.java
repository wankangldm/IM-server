package top.wankang.imServer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
    @RequestMapping("/chat")
    public String chat() {
        return "chat";
    }
    @RequestMapping("/chat2")
    public String chat2() {
        return "chat2";
    }
}