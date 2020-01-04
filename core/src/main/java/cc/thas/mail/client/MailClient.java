package cc.thas.mail.client;

import cc.thas.mail.message.MessageGetter;

import javax.mail.MessagingException;
import java.util.List;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2019/12/30 22:00
 */
public interface MailClient {

    void connect() throws MessagingException;

    void close() throws MessagingException;

    /**
     * 获取所有消息 由新到旧
     */
    List<MessageGetter> getMessages() throws MessagingException;

    /**
     * 获取limit条消息 由新到旧
     */
    List<MessageGetter> getMessages(int limit) throws MessagingException;

    /**
     * 根据ID获取指定的邮件
     */
    MessageGetter getMessage(String messageId) throws MessagingException;

    /**
     * 获取邮件总数量
     */
    int getMessageCount() throws MessagingException;
}
