package cc.thas.mail.message.wrapper;

import cc.thas.mail.message.AbstractMessageWrapper;
import cc.thas.mail.message.Initable;
import cc.thas.mail.message.content.MessageContentGetter;
import cc.thas.mail.message.content.wrapper.MessageContentWrapper;
import cc.thas.mail.message.resource.ResourceGetter;

import javax.mail.Address;
import javax.mail.Flags.Flag;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2019/12/28 23:02
 */
public class MessageWrapper extends AbstractMessageWrapper implements Initable {

    private final MimeMessage message;
    private MessageContentGetter messageContentGetter;

    public MessageWrapper(MimeMessage message) {
        this.message = message;
    }

    @Override
    public String getId() throws MessagingException {
        return message.getMessageID();
    }

    @Override
    public boolean isSeen() throws MessagingException {
        return message.getFlags().contains(Flag.SEEN);
    }

    @Override
    public String getSubject() throws MessagingException, UnsupportedEncodingException {
        String subject = message.getSubject();
        return subject != null ? MimeUtility.decodeText(subject) : null;
    }

    @Override
    public InternetAddress getFromAddress() throws MessagingException {
        Address[] from = message.getFrom();
        return from.length == 0 ? null : (InternetAddress) from[0];
    }

    @Override
    public List<InternetAddress> getReceiveAddressList(RecipientType type) throws MessagingException {
        Address[] addresses;
        if (type == null) {
            addresses = message.getAllRecipients();
        } else {
            addresses = message.getRecipients(type);
        }

        return addresses == null ? Collections.emptyList() :
                Arrays.stream(addresses).map(address -> (InternetAddress) address).collect(Collectors.toList());
    }

    @Override
    public Date getSentDate() throws MessagingException {
        return message.getSentDate();
    }

    @Override
    public void init() {
        messageContentGetter = new MessageContentWrapper(message);
    }

    @Override
    public String getPlainText() throws IOException, MessagingException {
        checkInitialed();
        return messageContentGetter.getPlainText();
    }

    @Override
    public String getHtml() throws IOException, MessagingException {
        checkInitialed();
        return messageContentGetter.getHtml();
    }

    @Override
    public List<ResourceGetter> getInlineResources() throws MessagingException, IOException {
        checkInitialed();
        return messageContentGetter.getInlineResources();
    }

    @Override
    public List<ResourceGetter> getAttachmentResources() throws MessagingException, IOException {
        checkInitialed();
        return messageContentGetter.getAttachmentResources();
    }
}
