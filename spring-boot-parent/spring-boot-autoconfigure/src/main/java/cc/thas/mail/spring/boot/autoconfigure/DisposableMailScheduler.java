package cc.thas.mail.spring.boot.autoconfigure;

import cc.thas.mail.schedule.MailScheduler;
import cc.thas.mail.schedule.SchedulerTaskConfig;
import org.springframework.beans.factory.DisposableBean;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/5 14:30
 */
public class DisposableMailScheduler implements MailScheduler, DisposableBean {

    private final MailScheduler scheduler;

    public DisposableMailScheduler(MailScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void submit(SchedulerTaskConfig config) {
        scheduler.submit(config);
    }

    @Override
    public void shutdown() {
        scheduler.shutdown();
    }

    @Override
    public void destroy() throws Exception {
        shutdown();
    }
}
