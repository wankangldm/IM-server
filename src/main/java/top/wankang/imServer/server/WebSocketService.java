/*
package top.wankang.imServer.server;

import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//创建连接使用的URL
@ServerEndpoint("/websocket/{name}")
@RestController
public class WebSocketServer {
// 通过ConcurrentHashMap保存全部在线会话对象。
    //存储客户端的连接对象,每个客户端连接都会产生一个连接对象
    private static ConcurrentHashMap<String,WebSocketServer> map=new ConcurrentHashMap();
    //每个连接都会有自己的会话
    private Session session;
    //客户端名
    private String name;
    //当打开连接后触发
    @OnOpen
    public void open(@PathParam("name") String name, Session session){
        map.put(name,this);
        System.out.println(name+"连接服务器成功");
        System.out.println("客户端连接个数:"+getConnetNum());

        this.session=session;
        this.name=name;
    }
//当连接关闭时触发
    @OnClose
    public void close(){
        map.remove(name);
        System.out.println(name+"断开了服务器连接");
    }
    //	当通信异常时触发
    @OnError
    public void error(Throwable error){
        error.printStackTrace();
        System.out.println(name+"出现了异常");
    }

//当接收客户端信息时触发   私聊
    @OnMessage
    public void getMessage(String message) throws IOException {
        String[] str = message.split(":");
        System.out.println("收到" + name + ":" + str[1]);
        System.out.println("客户端连接个数:" + getConnetNum());

        //判断是私聊还是群聊
        if ("0".equals(str[2])) {
            //群聊
            Set<Map.Entry<String, WebSocketServer>> entries1 = map.entrySet();
            for (Map.Entry<String, WebSocketServer> entry : entries1) {
                if (!entry.getKey().equals(name)) {//将消息转发到其他非自身客户端
                    entry.getValue().send(message);
                }
            }
        }else
        {
        //匹配消息需要发送的对象
            //私聊
        Set<Map.Entry<String, WebSocketServer>> entries = map.entrySet();
        for (Map.Entry<String, WebSocketServer> entry : entries) {
            if (entry.getKey().equals(str[2])) {//将消息转发到指定客户端
                entry.getValue().send(str[1]);
            }

        }
    }
    }

    //消息发送
    public void send(String message) throws IOException {
        if(session.isOpen()){
            session.getBasicRemote().sendText(message);
        }
    }
//获取客户端连接数
    public int  getConnetNum(){
        return map.size();
    }
}
*/
package top.wankang.imServer.server;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import top.wankang.imServer.model.SocketMsg;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author chen
 * @date 2019/10/26
 * @email 15218979950@163.com
 * @description websocket的具体实现类
 *  使用springboot的唯一区别是要@Component声明，而使用独立容器是由容器自己管理websocket的，
 *  但在springboot中连容器都是spring管理的。
 *  虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，
 *  所以可以用一个静态set保存起来
 */
@ServerEndpoint(value = "/websocket/{nickname}")
@Component
public class WebSocketService {
    private String nickname;
    private Session session;

    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<WebSocketService>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    //用来记录sessionId和该session进行绑定
    private static Map<String, Session> map = new HashMap<String, Session>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("nickname") String nickname) {
        Map<String,Object> message=new HashMap<String, Object>();
        this.session = session;
        this.nickname = nickname;
        map.put(session.getId(), session);
        webSocketSet.add(this);//加入set中
        System.out.println("有新连接加入:" + nickname + ",当前在线人数为" + webSocketSet.size());
        //this.session.getAsyncRemote().sendText("恭喜" + nickname + "成功连接上WebSocket(其频道号：" + session.getId() + ")-->当前在线人数为：" + webSocketSet.size());
        message.put("type",0); //消息类型，0-连接成功，1-用户消息
        message.put("people",webSocketSet.size()); //在线人数
        message.put("name",nickname); //昵称
        message.put("aisle",session.getId()); //频道号
        this.session.getAsyncRemote().sendText(new Gson().toJson(message));
    }

    /**
     * 连接关闭调用的方法    
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); //从set中删除
        System.out.println("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("nickname") String nickname) {
        System.out.println("来自客户端的消息-->" + nickname + ": " + message);

        //从客户端传过来的数据是json数据，所以这里使用jackson进行转换为SocketMsg对象，
        // 然后通过socketMsg的type进行判断是单聊还是群聊，进行相应的处理:
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMsg socketMsg;

        try {
            socketMsg = objectMapper.readValue(message, SocketMsg.class);
            if (socketMsg.getType() == 1) {
                //单聊.需要找到发送者和接受者.
                socketMsg.setFromUser(session.getId());//发送者.
                Session fromSession = map.get(socketMsg.getFromUser());
                Session toSession = map.get(socketMsg.getToUser());
                //发送给接受者.
                if (toSession != null) {
                    //发送给发送者.
                    Map<String,Object> m=new HashMap<String, Object>();
                    m.put("type",1);
                    m.put("name",nickname);
                    m.put("msg",socketMsg.getMsg());
                    fromSession.getAsyncRemote().sendText(new Gson().toJson(m));
                    toSession.getAsyncRemote().sendText(new Gson().toJson(m));
                } else {
                    //发送给发送者.
                    fromSession.getAsyncRemote().sendText("系统消息：对方不在线或者您输入的频道号不对");
                }
            } else {
                //群发消息
                broadcast(nickname + ": " + socketMsg.getMsg());
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用   
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 群发自定义消息
     */
    public void broadcast(String message) {
        for (WebSocketService item : webSocketSet) {
            item.session.getAsyncRemote().sendText(message);//异步发送消息.
        }
    }
}