package cc.thas.mail.message.content.wrapper;

import cc.thas.mail.message.content.AbstractMessageContentWrapper;
import cc.thas.mail.message.content.MessageContentGetter;
import cc.thas.mail.message.resource.ResourceGetter;
import cc.thas.mail.message.resource.wrapper.ResourceWrapper;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2019/12/30 22:11
 */
public class MultiPartMixedContentWrapper extends AbstractMessageContentWrapper {

    private final Multipart content;
    private MessageContentGetter multiPartRelatedPartWrapper;
    private List<ResourceGetter> attachmentResources;

    public MultiPartMixedContentWrapper(Multipart content) {
        this.content = content;
    }

    @Override
    public void init() throws MessagingException, IOException {
        attachmentResources = new ArrayList<>();
        for (int i = 0; i < content.getCount(); ++i) {
            MimeBodyPart bodyPart = (MimeBodyPart)content.getBodyPart(i);
            if (bodyPart.isMimeType(MULTI_PART_RELATED)) {
                multiPartRelatedPartWrapper = new MultiPartRelatedContentWrapper((Multipart)bodyPart.getContent());
            } else if (bodyPart.getContentID() != null) {
                if(bodyPart.getContent() instanceof InputStream){
                    attachmentResources.add(new ResourceWrapper(bodyPart));
                }
            }
        }
    }

    @Override
    public String getPlainText() throws IOException, MessagingException {
        checkInitialed();
        if (multiPartRelatedPartWrapper != null) {
            return multiPartRelatedPartWrapper.getPlainText();
        }
        return null;
    }

    @Override
    public String getHtml() throws IOException, MessagingException {
        checkInitialed();
        if (multiPartRelatedPartWrapper != null) {
            return multiPartRelatedPartWrapper.getHtml();
        }
        return null;
    }

    @Override
    public List<ResourceGetter> getInlineResources() throws MessagingException, IOException {
        checkInitialed();
        if (multiPartRelatedPartWrapper != null) {
            return multiPartRelatedPartWrapper.getInlineResources();
        }
        return super.getInlineResources();
    }

    @Override
    public List<ResourceGetter> getAttachmentResources() throws MessagingException, IOException {
        checkInitialed();
        return new ImmutableArrayList<>(attachmentResources);
    }

}
