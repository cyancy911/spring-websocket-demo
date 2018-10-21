package my.test.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
/**
 * <p/>
 * Created by cyancy911 on 2018/10/20 23:48 .
 */
@Slf4j
@Controller
public class TestMessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/retrieve")
    public void retrieve(String message) {
        simpMessagingTemplate.convertAndSend("/topic/send", "已经接受了消息：" + message);
    }

    @MessageMapping("/to-internal")
    @SendTo("/topic/to-internal")
//    @SendToUser(destinations = "/topic/internal")
    public String toInternal(String message) {
        return "发送到内部的消息：" + message;
    }

    @MessageMapping("/internal")
    @SendTo("/topic/internal")
    public void internal() {
    }

    /**
     * 订阅的时候使用
     */
    @SubscribeMapping("/internal")
    public String internalSub() {
        System.out.println("订阅成功");
        return "订阅成功";
    }

}
