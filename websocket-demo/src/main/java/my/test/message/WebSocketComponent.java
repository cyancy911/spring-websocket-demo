package my.test.message;

import my.test.bean.TestBean;
import my.test.decoder.Json2TestBeanDecoder;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 * Created by cyancy911 on 2018/10/19 23:59 .
 */
@Component
@ServerEndpoint(
        value = "/web-socket",
        subprotocols = {},
        decoders = {Json2TestBeanDecoder.class},
        encoders = {})
public class WebSocketComponent {

    public static final Map<String, Session> SESSION_MAP = new HashMap<>();

    /**
     * 连接开启
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("open: " + session.getId());
        SESSION_MAP.put(session.getId(), session);
    }

    /**
     * 请求数据
     * 1、如果有decoder，不要把String参数写进来
     * 2、如果有String参数，没有Bean参数，默认传输String，decoder不生效
     * 3、如果String和Bean参数同时存在，则启动报错
     *
     * 报文格式：{"a":"adfdasfdsa"}
     *
     *
     *
     * @param session
     */
    @OnMessage
    public void onMessage(Session session, TestBean testBean) {
        System.out.println("message: " + session.getId());
        System.out.println(testBean);
    }

    /**
     * 连接关闭
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("close: " + session.getId());
        SESSION_MAP.remove(session.getId());
    }

    /**
     * 错误处理
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("error: " + session.getId());
        System.out.println(throwable.getMessage());
    }

}
