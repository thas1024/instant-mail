package cc.thas.mail.event.listener;

import cc.thas.mail.event.Event;

import java.util.List;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 13:02
 */
public interface EventListener {

    List<Class<? extends Event<?>>> getSupportedListeners();

    default boolean isSupported(Event<?> event) {
        if (event == null) {
            return false;
        }
        return getSupportedListeners().stream().anyMatch(item -> event.getClass().isAssignableFrom(item));
    }

    void handle(Event<?> event);
}
