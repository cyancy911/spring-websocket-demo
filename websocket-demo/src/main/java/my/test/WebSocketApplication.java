package my.test;

import my.test.message.WebSocketComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import javax.websocket.*;
import java.io.IOException;

/**
 * <p/>
 * Created by cyancy911 on 2018/10/19 23:17 .
 */
@SpringBootApplication
public class WebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketApplication.class, args);
        new Thread(new Echo()).start();
    }

    private static class Echo implements Runnable {

        @Override
        public void run() {
            while(true) {
                for(Session session : WebSocketComponent.SESSION_MAP.values()) {
                    try {
                        session.getBasicRemote().sendText(session.getId() + ": echo");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                }
            }
        }
    }

}
