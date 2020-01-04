package cc.thas.mail.message.content.wrapper;

import cc.thas.mail.message.content.AbstractMessageContentWrapper;
import cc.thas.mail.message.content.MessageContentGetter;
import cc.thas.mail.message.resource.ResourceGetter;
import cc.thas.mail.message.resource.wrapper.ResourceWrapper;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2019/12/30 21:44
 */
public class MultiPartRelatedContentWrapper extends AbstractMessageContentWrapper {

    private final Multipart content;
    private MessageContentGetter multiPartAlternativePartWrapper;
    private List<ResourceGetter> inlineResources;

    public MultiPartRelatedContentWrapper(Multipart content) {
        this.content = content;
    }

    @Override
    public void init() throws MessagingException, IOException {
        inlineResources = new ArrayList<>();
        for (int i = 0; i < content.getCount(); ++i) {
            MimeBodyPart bodyPart = (MimeBodyPart)content.getBodyPart(i);
            if (bodyPart.isMimeType(AbstractMessageContentWrapper.MULTI_PART_ALTERNATIVE)) {
                multiPartAlternativePartWrapper = new MultiPartAlternativeContentWrapper(
                    (Multipart)bodyPart.getContent());
            } else if (bodyPart.getContentID() != null) {
                inlineResources.add(new ResourceWrapper(bodyPart));
            }
        }
    }

    @Override
    public String getPlainText() throws IOException, MessagingException {
        checkInitialed();
        if (multiPartAlternativePartWrapper != null) {
            return multiPartAlternativePartWrapper.getPlainText();
        }
        return null;
    }

    @Override
    public String getHtml() throws IOException, MessagingException {
        checkInitialed();
        if (multiPartAlternativePartWrapper != null) {
            return multiPartAlternativePartWrapper.getHtml();
        }
        return null;
    }

    @Override
    public List<ResourceGetter> getInlineResources() throws MessagingException, IOException {
        checkInitialed();
        return new ImmutableArrayList<>(inlineResources);
    }
}
