package cc.thas.mail.message;

import cc.thas.mail.message.content.MessageContentGetter;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2019/12/28 22:35
 */
public interface MessageGetter extends MessageContentGetter {
    String getId() throws MessagingException;

    boolean isSeen() throws MessagingException;

    String getSubject() throws MessagingException, UnsupportedEncodingException;

    InternetAddress getFromAddress() throws MessagingException;

    String getFrom() throws MessagingException, UnsupportedEncodingException;

    List<InternetAddress> getReceiveAddressList(Message.RecipientType type) throws MessagingException;

    List<InternetAddress> getToAddressList() throws MessagingException;

    String getTo() throws MessagingException, UnsupportedEncodingException;

    Date getSentDate() throws MessagingException;

    String getFormattedSentDate() throws MessagingException;

    String stringify() throws MessagingException, IOException;
}
