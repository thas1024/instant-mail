package cc.thas.mail.message.content.wrapper;

import cc.thas.mail.message.content.AbstractMessageContentWrapper;
import cc.thas.mail.message.content.MessageContentGetter;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2019/12/30 21:44
 */
public class MultiPartAlternativeContentWrapper extends AbstractMessageContentWrapper {

    private final Multipart content;
    private MessageContentGetter textPlainPartWrapper;
    private MessageContentGetter textHtmlPartWrapper;

    public MultiPartAlternativeContentWrapper(Multipart content) {
        this.content = content;
    }

    @Override
    public void init() throws MessagingException, IOException {
        for (int i = 0; i < content.getCount(); ++i) {
            MimeBodyPart bodyPart = (MimeBodyPart) content.getBodyPart(i);
            if (bodyPart.isMimeType(AbstractMessageContentWrapper.TEXT_PLAIN)) {
                textPlainPartWrapper = new TextPlainContentWrapper((String) bodyPart.getContent());
            } else if (bodyPart.isMimeType(AbstractMessageContentWrapper.TEXT_HTML)) {
                textHtmlPartWrapper = new TextHtmlContentWrapper((String) bodyPart.getContent());
            }
        }
    }

    @Override
    public String getPlainText() throws IOException, MessagingException {
        checkInitialed();
        if (textPlainPartWrapper != null) {
            return textPlainPartWrapper.getPlainText();
        }
        return null;
    }

    @Override
    public String getHtml() throws IOException, MessagingException {
        checkInitialed();
        if (textHtmlPartWrapper != null) {
            return textHtmlPartWrapper.getHtml();
        }
        return null;
    }

}
