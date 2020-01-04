package cc.thas.mail.schedule;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 12:51
 */
public interface MailScheduler {

    void submit(SchedulerTaskConfig config);

    void shutdown();
}
