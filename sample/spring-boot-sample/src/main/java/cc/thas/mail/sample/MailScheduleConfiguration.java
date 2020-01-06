package cc.thas.mail.sample;

import cc.thas.mail.event.impl.MailReceivedEvent;
import cc.thas.mail.event.listener.AbstractMailReceivedEventListener;
import cc.thas.mail.event.listener.EventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/5 14:55
 */
//@Configuration
public class MailScheduleConfiguration {

    @Bean
    public EventListener customEventListener() {
        return new AbstractMailReceivedEventListener() {
            @Override
            protected void onMailReceived(MailReceivedEvent event) {
                System.out.println("自定义1" + event.getPayload().getMessage());
            }
        };
    }

    @Bean
    public EventListener customEventListener2() {
        return new AbstractMailReceivedEventListener() {
            @Override
            protected void onMailReceived(MailReceivedEvent event) {
                System.out.println("自定义2" + event.getPayload().getMessage());
            }
        };
    }
}
