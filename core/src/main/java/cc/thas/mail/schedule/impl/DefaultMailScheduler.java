package cc.thas.mail.schedule.impl;

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
import lombok.Data;

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
    private final ScheduledExecutorService executor;
    private final Map<String, Store> storeMap = new ConcurrentHashMap<>();

    public DefaultMailScheduler(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        MailScheduleLog.info("Mail scheduler start.");
        executor = new ScheduledThreadPoolExecutor(16);
        eventPublisher.registerEventListener(new MailTaskErrorEventListener());
        eventPublisher.registerEventListener(new LogMailReceivedEventListener());
    }

    @Override
    public void submit(SchedulerTaskConfig config) {
        MailTask mailTask = new MailTask(config, eventPublisher);
        MailScheduleLog.info("Submit task %s user %s.", mailTask.getTaskId(), config.getUser());
        ScheduledFuture<?> scheduledFuture = executor.scheduleAtFixedRate(mailTask, config.getInitialDelay(),
                config.getPeriod(), config.getUnit());
        Store store = new Store();
        store.setConfig(config);
        store.setTaskId(mailTask.getTaskId());
        store.setFuture(scheduledFuture);
        storeMap.put(mailTask.getTaskId(), store);
    }

    @Override
    public void shutdown() {
        storeMap.forEach((taskId, store) -> {
            cancelTask(store);
        });
        MailScheduleLog.info("Mail scheduler shutdown.");
        executor.shutdown();
    }

    private void cancelTask(Store store) {
        MailScheduleLog.info("Cancel task %s user %s.", store.getTaskId(), store.getConfig().getUser());
        store.getFuture().cancel(false);

    }

    @Data
    public static class Store {
        private String taskId;
        private SchedulerTaskConfig config;
        private ScheduledFuture<?> future;
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
            cancelTask(store);
            MailScheduleLog.info("Remove task %s user %s.", payload.getTaskId(), store.getConfig().getUser());
            storeMap.remove(payload.getTaskId());
        }
    }

    public static class LogMailReceivedEventListener extends AbstractMailReceivedEventListener {

        @Override
        protected void onMailReceived(MailReceivedEvent event) {
            MailReceivedEvent.MailReceivedPayload payload = event.getPayload();
            MailScheduleLog.info("Task %s user %s received mail.", payload.getTaskId(),
                    payload.getMessage().getId());
        }
    }
}
