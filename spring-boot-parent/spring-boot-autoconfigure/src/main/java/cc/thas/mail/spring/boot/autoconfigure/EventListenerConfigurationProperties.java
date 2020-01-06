package cc.thas.mail.spring.boot.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cc.thas.mail.spring.boot.autoconfigure.InstantMailConfigurationProperties.INSTANT_MAIL_PREFIX;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/6 22:52
 */
@Data
@ConfigurationProperties(prefix = INSTANT_MAIL_PREFIX + ".listeners")
public class EventListenerConfigurationProperties {

    private List<HttpListener> httpListeners;

    @Data
    public static class HttpListener {
        private String url;
        private Map<String, String> params = Collections.emptyMap();
        private Map<String, String> headers = Collections.emptyMap();
        private boolean usePost = false;
    }
}
