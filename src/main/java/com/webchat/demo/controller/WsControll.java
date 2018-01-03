package com.webchat.demo.controller;

import com.webchat.demo.domain.RequestMessage;
import com.webchat.demo.domain.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
public class WsControll {

    @MessageMapping("/welcome")
    public void say1(RequestMessage message) {
        this.say(message);
//        return new ResponseMessage("welcome," + message.getName() + " !");
    }

    @SubscribeMapping("http://localhost:8081/topic/getResponse")
    public ResponseMessage say(RequestMessage message) {
        System.out.println(message.getName());
        return new ResponseMessage("welcome," + message.getName() + " !");
    }
}
