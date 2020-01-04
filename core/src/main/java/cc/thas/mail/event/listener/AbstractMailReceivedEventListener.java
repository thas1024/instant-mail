package cc.thas.mail.event.listener;

import cc.thas.mail.event.Event;
import cc.thas.mail.event.listener.EventListener;
import cc.thas.mail.event.impl.MailReceivedEvent;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 13:10
 */
public abstract class AbstractMailReceivedEventListener implements EventListener {
    @Override
    public List<Class<? extends Event<?>>> getSupportedListeners() {
        return Collections.singletonList(MailReceivedEvent.class);
    }

    @Override
    public void handle(Event<?> event) {
        if(event instanceof MailReceivedEvent){
            onMailReceived((MailReceivedEvent) event);
        }
    }

    protected abstract void onMailReceived(MailReceivedEvent event);
}
