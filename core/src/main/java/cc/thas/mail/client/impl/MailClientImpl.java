package cc.thas.mail.client.impl;

import cc.thas.mail.client.MailClient;
import cc.thas.mail.message.MessageGetter;
import cc.thas.mail.message.wrapper.MessageWrapper;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2019/12/30 22:06
 */
public class MailClientImpl implements MailClient {

    private final String protocol;
    private final String host;
    private final String user;
    private final String password;
    private Store store;
    private Folder folder;

    public MailClientImpl(String protocol, String host, String user, String password) {
        this.protocol = protocol;
        this.host = host;
        this.user = user;
        this.password = password;
    }

    @Override
    public List<MessageGetter> getMessages() throws MessagingException {
        return invertAndToList(folder.getMessages());
    }

    @Override
    public List<MessageGetter> getMessages(int limit) throws MessagingException {
        if (limit <= 0) {
            return Collections.emptyList();
        }
        int count = getMessageCount();
        int start = count - limit;
        if (start < 0) {
            start = 0;
        }
        return invertAndToList(folder.getMessages(start, count - 1));
    }

    @Override
    public MessageGetter getMessage(String messageId) throws MessagingException {
        if (messageId == null) {
            return null;
        }
        return getMessages().stream().filter(item -> {
            try {
                return messageId.equals(item.getId());
            } catch (MessagingException ignored) {
                return false;
            }
        }).findFirst().orElse(null);
    }

    @Override
    public int getMessageCount() throws MessagingException {
        return folder.getMessageCount();
    }

    @Override
    public void connect() throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        store = session.getStore(protocol);
        store.connect(host, user, password);
        folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
    }

    @Override
    public void close() throws MessagingException {
        folder.close();
        store.close();
    }

    private static List<MessageGetter> invertAndToList(Message[] messages) {
        if (messages == null) {
            return Collections.emptyList();
        }
        int length = messages.length;
        List<MessageGetter> list = new ArrayList<>(length);
        for (int i = length - 1; i >= 0; i--) {
            list.add(new MessageWrapper((MimeMessage) messages[i]));
        }
        return list;
    }

}
