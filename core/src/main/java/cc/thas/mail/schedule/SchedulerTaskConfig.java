package cc.thas.mail.schedule;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 16:49
 */
@Data
public class SchedulerTaskConfig {

    private String protocol;
    private String host;
    private String user;
    private String password;
    private long initialDelay = 0;
    private long period = 10000;
    private TimeUnit unit = TimeUnit.MILLISECONDS;
    private int maxCacheCount = 20;
}
