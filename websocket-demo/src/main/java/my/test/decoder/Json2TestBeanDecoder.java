package my.test.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.test.bean.TestBean;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

/**
 * <p/>
 * Created by cyancy911 on 2018/10/20 21:18 .
 */
public class Json2TestBeanDecoder implements Decoder.Text<TestBean> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public TestBean decode(String s) throws DecodeException {
        try {
            return OBJECT_MAPPER.readValue(s, TestBean.class);
        } catch (IOException e) {
            throw new DecodeException(s, "can't convert to TestBean.class", e);
        }
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // do nothing
    }

    @Override
    public void destroy() {
        // do nothing
    }
}