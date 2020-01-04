package cc.thas.mail.message.resource.wrapper;

import cc.thas.mail.message.resource.ResourceGetter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/1 21:41
 */
public class ResourceWrapper implements ResourceGetter {

    public static final String EMPTY_STR = "";

    private final MimeBodyPart bodyPart;

    public ResourceWrapper(MimeBodyPart bodyPart){
        this.bodyPart = bodyPart;
    }

    @Override
    public String getContentId() throws MessagingException {
        return convertContentId(bodyPart.getContentID());
    }

    @Override
    public String getFileName() throws MessagingException {
        return bodyPart.getFileName();
    }

    @Override
    public String getContentType() throws MessagingException {
        return convertContentType(bodyPart.getContentType());
    }

    @Override
    public InputStream getContent() throws IOException, MessagingException {
        return (InputStream)bodyPart.getContent();
    }

    public static String convertContentId(String contentId) {
        if (contentId == null) {
            return null;
        }
        return contentId.replace(">", EMPTY_STR).replace("<", EMPTY_STR);
    }

    public static String convertContentType(String contendType) {
        //if (contendType == null) {
        //    return null;
        //}
        //return contendType.replaceAll("; .*", EMPTY_STR);
        return contendType;
    }
}
