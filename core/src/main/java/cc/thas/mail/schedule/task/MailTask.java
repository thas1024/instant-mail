package cc.thas.mail.schedule.task;

import cc.thas.mail.client.MailClient;
import cc.thas.mail.client.impl.MailClientImpl;
import cc.thas.mail.event.impl.MailReceivedEvent;
import cc.thas.mail.event.impl.MailTaskErrorEvent;
import cc.thas.mail.event.publisher.EventPublisher;
import cc.thas.mail.exception.MailTaskException;
import cc.thas.mail.message.MailMessage;
import cc.thas.mail.message.MessageGetter;
import cc.thas.mail.schedule.SchedulerTaskConfig;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 13:36
 */
public class MailTask implements Runnable {

    public static final int DEFAULT_MAX_CACHE_COUNT = 20;

    private final String taskId;
    private final SchedulerTaskConfig config;
    private final MailClient mailClient;
    private final EventPublisher eventPublisher;
    private final int maxCacheCount;
    private Queue<String> cachedMessageIds = new LinkedList<>();

    public MailTask(SchedulerTaskConfig config, EventPublisher eventPublisher) {
        this.taskId = UUID.randomUUID().toString();
        this.config = config;
        this.mailClient = new MailClientImpl(config.getProtocol(), config.getHost(), config.getUser(),
                config.getPassword());
        this.maxCacheCount = config.getMaxCacheCount() <= 0 ? DEFAULT_MAX_CACHE_COUNT : config.getMaxCacheCount();
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void run() {
        try {
            doRun();
        } catch (Exception e) {
            eventPublisher.publish(new MailTaskErrorEvent(taskId, new MailTaskException(e)));
        }
    }

    public String getTaskId() {
        return taskId;
    }

    private void doRun() throws Exception {
        List<String> list = new ArrayList<>();
        List<MessageGetter> messages = mailClient.getMessages(maxCacheCount);
        for (MessageGetter message : messages) {
            if (cachedMessageIds.contains(message.getId())) {
                // 最新的ID在缓存中 没有新邮件
                break;
            }
            eventPublisher.publish(buildEvent(message));
            list.add(message.getId());
        }
        cachedMessageIds.addAll(list);
        // 剔除超出缓存的部分
        int count = cachedMessageIds.size() - maxCacheCount;
        while (count-- > 0) {
            cachedMessageIds.poll();
        }
    }

    private MailReceivedEvent buildEvent(MessageGetter messageGetter) throws MessagingException, UnsupportedEncodingException {
        MailMessage mailMessage = new MailMessage();
        mailMessage.setId(messageGetter.getId());
        mailMessage.setSubject(messageGetter.getSubject());
        mailMessage.setFrom(messageGetter.getFrom());
        mailMessage.setTo(messageGetter.getTo());
        mailMessage.setSeen(messageGetter.isSeen());
        mailMessage.setSentDate(messageGetter.getSentDate());
        return new MailReceivedEvent(taskId, mailMessage);
    }

    public void init() throws MessagingException {
        mailClient.connect();
        cachedMessageIds.clear();
        List<MessageGetter> messages = mailClient.getMessages(maxCacheCount);
        for (MessageGetter message : messages) {
            cachedMessageIds.offer(message.getId());
        }
    }

    public void destory() throws MessagingException {
        mailClient.close();
        cachedMessageIds.clear();
    }

}