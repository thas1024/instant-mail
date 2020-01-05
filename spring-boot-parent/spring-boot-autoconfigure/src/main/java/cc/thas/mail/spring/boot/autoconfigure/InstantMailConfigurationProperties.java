package cc.thas.mail.spring.boot.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cc.thas.mail.spring.boot.autoconfigure.InstantMailAutoConfiguration.INSTANT_MAIL_PREFIX;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/5 14:07
 */
@Data
@ConfigurationProperties(prefix = INSTANT_MAIL_PREFIX)
public class InstantMailConfigurationProperties {

    private long initialDelay = 0;
    private long period = 10000;
    private int maxCacheCount = 20;

    @NestedConfigurationProperty
    private List<TaskConfig> configs;

    @NestedConfigurationProperty
    private Listeners listeners;

    @Data
    public static class TaskConfig {
        private String protocol;
        private String host;
        private String user;
        private String password;
        private long initialDelay;
        private long period;
        private int maxCacheCount;
    }

    @Data
    public static class Listeners {

        @NestedConfigurationProperty
        private List<HttpListener> httpListeners;

        @Data
        public static class HttpListener {
            private String url;
            private Map<String, String> params = Collections.emptyMap();
            private Map<String, String> headers = Collections.emptyMap();
            private boolean usePost = false;
        }
    }
}
