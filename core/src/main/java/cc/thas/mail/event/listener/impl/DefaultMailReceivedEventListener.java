package cc.thas.mail.event.listener.impl;

import cc.thas.mail.event.impl.MailReceivedEvent;
import cc.thas.mail.event.listener.AbstractMailReceivedEventListener;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 13:17
 */
public class DefaultMailReceivedEventListener extends AbstractMailReceivedEventListener {
    @Override
    protected void onMailReceived(MailReceivedEvent event) {
        System.out.println(event.getPayload().getMessage());
    }
}
