package cc.thas.mail.schedule.impl;

import cc.thas.mail.client.MailClient;
import cc.thas.mail.client.impl.MailClientImpl;
import cc.thas.mail.event.Event;
import cc.thas.mail.event.impl.MailReceivedEvent;
import cc.thas.mail.event.impl.MailTaskErrorEvent;
import cc.thas.mail.event.listener.AbstractMailReceivedEventListener;
import cc.thas.mail.event.listener.EventListener;
import cc.thas.mail.event.publisher.EventPublisher;
import cc.thas.mail.logger.MailScheduleLog;
import cc.thas.mail.schedule.MailScheduler;
import cc.thas.mail.schedule.SchedulerTaskConfig;
import cc.thas.mail.schedule.task.MailTask;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 13:33
 */
public class DefaultMailScheduler implements MailScheduler {

    private final EventPublisher eventPublisher;
    private final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(16);
    private final Map<String, Store> storeMap = new ConcurrentHashMap<>();

    public DefaultMailScheduler(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        eventPublisher.registerEventListener(new MailTaskErrorEventListener());
        eventPublisher.registerEventListener(new LogMailReceivedEventListener());
    }

    @Override
    public void submit(SchedulerTaskConfig config) {
        MailClient mailClient = new MailClientImpl(config.getProtocol(), config.getHost(), config.getUser(),
                config.getPassword());
        MailTask mailTask = new MailTask(mailClient, eventPublisher);
        MailScheduleLog.info("Submit task %s user %s.", mailTask.getTaskId(), config.getUser());
        ScheduledFuture<?> scheduledFuture = executor.scheduleAtFixedRate(mailTask, config.getInitialDelay(),
                config.getPeriod(), config.getUnit());
        Store store = new Store();
        store.setConfig(config);
        store.setFuture(scheduledFuture);
        store.setMailClient(mailClient);
        storeMap.put(mailTask.getTaskId(), store);
    }

    @Override
    public void shutdown() {
        storeMap.forEach((taskId, store) -> {
            MailScheduleLog.info("Cancel task %s user %s.", taskId, store.getConfig().getUser());
            store.getFuture().cancel(true);
            MailClient mailClient = store.getMailClient();
            try {
                mailClient.close();
            } catch (MessagingException e) {
                MailScheduleLog.error(e, "MailClient closed failed.");
            }

        });
        MailScheduleLog.info("Mail scheduler shutdown.");
        executor.shutdown();
    }

    private void removeTask(String taskId) {

    }

    public class MailTaskErrorEventListener implements EventListener {

        @Override
        public List<Class<? extends Event<?>>> getSupportedListeners() {
            return Collections.singletonList(MailTaskErrorEvent.class);
        }

        @Override
        public void handle(Event<?> event) {
            if (event instanceof MailTaskErrorEvent) {
                onError((MailTaskErrorEvent) event);
            }
        }

        public void onError(MailTaskErrorEvent event) {
            MailTaskErrorEvent.MailTaskErrorPayload payload = event.getPayload();
            Store store = storeMap.get(payload.getTaskId());
            if (store == null) {
                MailScheduleLog.warn("Task %s missed when on error.", payload.getTaskId());
                return;
            }
            MailScheduleLog.error(payload.getException(), "Task %s User %s on error.", payload.getTaskId(),
                    store.getConfig().getUser());
            ScheduledFuture<?> scheduledFuture = store.getFuture();
            MailScheduleLog.info("Cancel task %s user %s.", payload.getTaskId(), store.getConfig().getUser());
            scheduledFuture.cancel(false);
            try {
                store.getMailClient().close();
            } catch (MessagingException e) {
                MailScheduleLog.error(e, "MailClient closed failed.");
            }
            MailScheduleLog.info("Remove task %s user %s.", payload.getTaskId(), store.getConfig().getUser());
            storeMap.remove(payload.getTaskId());
        }
    }

    public static class Store {
        private ScheduledFuture<?> future;
        private MailClient mailClient;
        private SchedulerTaskConfig config;

        public ScheduledFuture<?> getFuture() {
            return future;
        }

        public void setFuture(ScheduledFuture<?> future) {
            this.future = future;
        }

        public MailClient getMailClient() {
            return mailClient;
        }

        public void setMailClient(MailClient mailClient) {
            this.mailClient = mailClient;
        }

        public SchedulerTaskConfig getConfig() {
            return config;
        }

        public void setConfig(SchedulerTaskConfig config) {
            this.config = config;
        }
    }

    public static class LogMailReceivedEventListener extends AbstractMailReceivedEventListener {

        @Override
        protected void onMailReceived(MailReceivedEvent event) {
            MailReceivedEvent.MailReceivedPayload payload = event.getPayload();
            MailScheduleLog.info("Task %s user %s received mail %s.", payload.getTaskId(),
                    payload.getMessage().getId());
        }
    }
}
