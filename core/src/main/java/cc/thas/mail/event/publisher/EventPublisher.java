package cc.thas.mail.event.publisher;

import cc.thas.mail.event.Event;
import cc.thas.mail.event.listener.EventListener;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 12:58
 */
public interface EventPublisher {

    void publish(Event<?> event);

    void registerEventListener(EventListener listener);

}
