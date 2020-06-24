/*
package top.wankang.imServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import top.wankang.imServer.model.Chat;
import top.wankang.imServer.model.Message;

import java.security.Principal;

@Controller
public class GreetingController {

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    // 处理来自"/app/hello"路径的消息
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(Message message) throws Exception {
        return message;
    }

    // 处理来自"/app/chat"路径的消息
    @MessageMapping("/chat")
    public void chat(Principal principal, Chat chat) {
        // 获取当前登录用户的用户名
        String from = principal.getName();
        // 将用户设置给chat对象的from属性
        chat.setFrom(from);
        // 再将消息发送出去，发送的目标用户就是 chat 对象的to属性值
        messagingTemplate.convertAndSendToUser(chat.getTo(),
                "/queue/chat", chat);
    }
}*/
