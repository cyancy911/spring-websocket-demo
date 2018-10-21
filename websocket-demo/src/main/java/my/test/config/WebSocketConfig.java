package my.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerEndpoint;

/**
 * 加入输出器ServerEndpointExporter，Spring boot 已经帮你扫描了{@link ServerEndpoint}，其他装配由 {@link ServerEndpointExporter}来做
 * 所以不用再手动add ServerEndpoint
 * <p/>
 * Created by cyancy911 on 2018/10/19 23:58 .
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
