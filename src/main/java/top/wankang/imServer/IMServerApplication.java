package top.wankang.imServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author wankang
 * @version 1.0
 * @date 2020/6/22 17:51
 */
@SpringBootApplication
public class IMServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IMServerApplication.class,args);
    }

    //WebSocket
    /*@Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }*/

}
