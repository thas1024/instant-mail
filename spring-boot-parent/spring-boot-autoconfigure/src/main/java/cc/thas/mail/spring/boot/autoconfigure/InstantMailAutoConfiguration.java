package cc.thas.mail.spring.boot.autoconfigure;

import cc.thas.mail.event.listener.EventListener;
import cc.thas.mail.event.listener.impl.DefaultMailReceivedEventListener;
import cc.thas.mail.event.publisher.EventPublisher;
import cc.thas.mail.event.publisher.impl.DefaultEventPublisher;
import cc.thas.mail.schedule.MailScheduler;
import cc.thas.mail.schedule.SchedulerTaskConfig;
import cc.thas.mail.schedule.impl.DefaultMailScheduler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

import static cc.thas.mail.spring.boot.autoconfigure.InstantMailAutoConfiguration.INSTANT_MAIL_PREFIX;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/5 13:59
 */
@Configuration
@ConditionalOnProperty(prefix = INSTANT_MAIL_PREFIX, name = "enabled", matchIfMissing = false)
@EnableConfigurationProperties(InstantMailConfigurationProperties.class)
public class InstantMailAutoConfiguration {

    public static final String INSTANT_MAIL_PREFIX = "instant-mail";

    @Bean
    @ConditionalOnMissingBean(EventListener.class)
    public EventListener defaultEventListener() {
        // 没有自定义listener 使用默认的
        return new DefaultMailReceivedEventListener();
    }

    @Bean
    @ConditionalOnMissingBean(EventPublisher.class)
    public EventPublisher eventPublisher(Collection<EventListener> listeners) {
        DefaultEventPublisher publisher = new DefaultEventPublisher();
        listeners.forEach(publisher::registerEventListener);
        return publisher;
    }

    @Bean
    public MailScheduler scheduler(InstantMailConfigurationProperties properties,
                                   EventPublisher eventPublisher) {
        MailScheduler scheduler = new DefaultMailScheduler(eventPublisher);
        properties.getConfigs().forEach(item -> {
            SchedulerTaskConfig config = new SchedulerTaskConfig();
            config.setHost(item.getHost());
            long initialDelay =
                    item.getInitialDelay() <= 0 ? properties.getInitialDelay() : item.getInitialDelay();
            config.setInitialDelay(initialDelay);
            int maxCacheCount =
                    item.getMaxCacheCount() <= 0 ? properties.getMaxCacheCount() : item.getMaxCacheCount();
            config.setMaxCacheCount(maxCacheCount);
            config.setHost(item.getHost());
            config.setProtocol(item.getProtocol());
            config.setUser(item.getUser());
            config.setPassword(item.getPassword());
            scheduler.submit(config);
        });
        return new DisposableMailScheduler(scheduler);
    }
}
