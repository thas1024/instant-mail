package cc.thas.mail.message.content.wrapper;

import cc.thas.mail.message.content.AbstractMessageContentWrapper;
import cc.thas.mail.message.content.MessageContentGetter;
import cc.thas.mail.message.resource.ResourceGetter;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2019/12/28 22:27
 */
public class MessageContentWrapper extends AbstractMessageContentWrapper {

    private final Part message;
    private MessageContentGetter messageContentGetter;

    public MessageContentWrapper(Part message) {
        this.message = message;
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
    public List<ResourceGetter> getAttachmentResources() throws MessagingException, IOException {
        checkInitialed();
        return messageContentGetter.getAttachmentResources();
    }

    @Override
    public List<ResourceGetter> getInlineResources() throws MessagingException, IOException {
        checkInitialed();
        return messageContentGetter.getInlineResources();
    }

    @Override
    public void init() throws MessagingException, IOException {
        if (message.isMimeType(TEXT_PLAIN)) {
            messageContentGetter = new TextPlainContentWrapper((String)message.getContent());
        } else if (message.isMimeType(TEXT_HTML)) {
            messageContentGetter = new TextHtmlContentWrapper((String)message.getContent());
        } else if (message.isMimeType(MULTI_PART_RELATED)) {
            messageContentGetter = new MultiPartRelatedContentWrapper((Multipart)message.getContent());
        } else if (message.isMimeType(MULTI_PART_ALTERNATIVE)) {
            messageContentGetter = new MultiPartAlternativeContentWrapper((Multipart)message.getContent());
        } else if (message.isMimeType(MULTI_PART_MIXED)) {
            messageContentGetter = new MultiPartMixedContentWrapper((Multipart)message.getContent());
        }
    }

}
