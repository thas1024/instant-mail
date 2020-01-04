package cc.thas.mail.event.publisher.impl;

import cc.thas.mail.event.Event;
import cc.thas.mail.event.listener.EventListener;
import cc.thas.mail.event.publisher.EventPublisher;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 13:22
 */
public class DefaultEventPublisher implements EventPublisher {

    private List<EventListener> list = new CopyOnWriteArrayList<>();

    @Override
    public void publish(Event<?> event) {
        list.forEach(item -> {
            if (item.isSupported(event)) {
                item.handle(event);
            }
        });
    }

    @Override
    public void registerEventListener(EventListener listener) {
        list.add(listener);
    }
}
