package cc.thas.mail.sample;

import cc.thas.mail.event.listener.EventListener;
import cc.thas.mail.event.listener.impl.DefaultMailReceivedEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/5 14:55
 */
@Configuration
public class MailScheduleConfiguration {

    @Bean
    public EventListener customEventListener() {
        return new DefaultMailReceivedEventListener();
    }

}
