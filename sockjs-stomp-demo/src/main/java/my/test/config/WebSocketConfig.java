package my.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * {@link EnableWebSocketMessageBroker} 中有说到 a higher-level messaging sub-protocol 其实就是指sockjs与stomp
 * <p/>
 * {@link WebSocketMessageBrokerConfigurer} 要生效得加 {@link EnableWebSocketMessageBroker} 注解
 * <p/>
 * Created by cyancy911 on 2018/10/20 22:07 .
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws") // 握手地址
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                        System.out.println("before handshake");
                        return true;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
                        System.out.println("after handshake");
                    }
                })
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                return message;
            }
        });
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.addDecoratorFactory(handler -> handler);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry
                .enableSimpleBroker("/topic") // 订阅的地址
                //.setHeartbeatValue(new long[]{0, 0}) // index=0，服务端接收/发送心跳事件间隔，index=1，客户端发送心跳间隔，默认0,0，单位：ms
                //.setSelectorHeaderName("")
                //.setTaskScheduler() // 设置心跳周期任务，设置后默认10000,10000，修改用setHeartbeatValue
                ;

        registry
                .setApplicationDestinationPrefixes("/app") // 接口传输地址
                .setUserDestinationPrefix("/user") // 跟用户绑定
                // 比如请求是："/user/queue/position-updates", 实际转换完就会变成这样 "/queue/position-updatesi9oqdfzo"
                // 请求是：/user/woshiyonghu/queue/position-updates，实际转换为：/queue/position-updateswoshiyonghu
//                .setCacheLimit(100) // 可以连多少个客户端，默认：1024
//                .setPathMatcher() // 参照AntPathMatcher
                ;

    }

}
