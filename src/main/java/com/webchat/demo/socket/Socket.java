package com.webchat.demo.socket;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ServerEndpoint(value = "/websocket")
@Component
public class Socket {
    public static Map<String, Session> sessionMap = new HashMap<String, Session>();
    private Session session ;



    @OnOpen
    public void startSocket(Session session) {
        this.session = session;
        System.out.println("链接成功");
        if (sessionMap.size() == 0) {
            return ;
        }
    }

    @OnMessage
    public void getMessgae(Session session, String str, boolean last) {
        System.out.println(new Date());
        if (session.isOpen()) {
            try {
                System.out.println(str);
                Gson gson = new Gson();
                Message msg = gson.fromJson(str, Message.class);
                Message toMessage = msg;
                toMessage.setFrom(msg.getId());
                toMessage.setTo(msg.getTo());

                //开启socket链接时msg的值是newUser
                if (msg.getMsg().equals("newUser")) {
                    //如果存在这个用户
                    if (sessionMap.containsKey(msg.getId())) {
                        //删除掉防止重复(如果更换了电脑或者浏览器。这个操作能保证session与id是唯一对应的且session是最新的)
                        sessionMap.remove(msg.getId());
                    }
                    //将用户id放进去
                    sessionMap.put(msg.getId(), session);
                    //发送在线人数
                    pubMessage(session);
                } else {
                    Session toSession = sessionMap.get(msg.getTo());
                    if (toSession != null && toSession.isOpen()) {
                        toSession.getBasicRemote().sendText(gson.toJson(toMessage).toString(), last);
                        //发送在线人数
                        pubMessage(toSession);
                    } else {
                        toMessage.setMsg("用户不存在");
                        toMessage.setFrom("系统");
                        session.getBasicRemote().sendText(gson.toJson(toMessage).toString(), last);

                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            System.out.println("session is closed");
        }
    }


    private static void pubMessage(Session session) throws IOException {
        Set userIds = sessionMap.keySet();
        StringBuffer sBuffer  = new StringBuffer();
        for (Object str1 : userIds) {
            sBuffer.append(str1.toString() + ",");
        }
        Gson gson = new Gson();
        Message message = new Message();
        message.setLive(sBuffer.toString());
        session.getBasicRemote().sendText(gson.toJson(message),true);
    }
}