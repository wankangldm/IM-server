/*
package top.wankang.imServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 设置消息代理的前缀，如果消息的前缀为"/topic"、"/queue"，就会将消息转发给消息代理（broker）
        // 再由消息代理广播给当前连接的客户端
        config.enableSimpleBroker("/topic","/queue");
        // 下面方法可以配置一个或多个前缀，通过这些前缀过滤出需要被注解方法处理的消息。
        // 例如这里表示前缀为"/app"的destination可以通过@MessageMapping注解的方法处理
        // 而其他 destination（例如"/topic""/queue"）将被直接交给 broker 处理
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 定义一个前缀为"/chart"的endpoint，并开启 sockjs 支持。
        // sockjs 可以解决浏览器对WebSocket的兼容性问题，客户端将通过这里配置的URL建立WebSocket连接
        registry.addEndpoint("/chat").withSockJS();
    }
}*/
package top.wankang.imServer.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author chen
 * @date 2019/10/26
 * @email 15218979950@163.com
 * @description WebSocketConfig配置类，注入对象ServerEndpointExporter，
 * *            这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}

