package cc.thas.mail.sample;

import cc.thas.mail.event.listener.impl.DefaultMailReceivedEventListener;
import cc.thas.mail.event.publisher.EventPublisher;
import cc.thas.mail.event.publisher.impl.DefaultEventPublisher;
import cc.thas.mail.schedule.MailScheduler;
import cc.thas.mail.schedule.SchedulerTaskConfig;
import cc.thas.mail.schedule.impl.DefaultMailScheduler;

import java.io.IOException;
import java.util.Properties;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 21:37
 */
public class MailSchedulerSample {

    public static void main(String[] args) throws InterruptedException, IOException {
        SchedulerTaskConfig schedulerTaskConfig = new SchedulerTaskConfig();
        Properties properties = new Properties();
        properties.load(MailSchedulerSample.class.getResourceAsStream("task-config-thas.properties"));
        schedulerTaskConfig.setProtocol(properties.getProperty("protocol"));
        schedulerTaskConfig.setHost(properties.getProperty("host"));
        schedulerTaskConfig.setUser(properties.getProperty("user"));
        schedulerTaskConfig.setPassword(properties.getProperty("password"));
        EventPublisher eventPublisher = new DefaultEventPublisher();
        eventPublisher.registerEventListener(new DefaultMailReceivedEventListener());
        MailScheduler mailScheduler = new DefaultMailScheduler(eventPublisher);
        mailScheduler.submit(schedulerTaskConfig);
        Thread.sleep(100000);
        mailScheduler.shutdown();
    }
}
