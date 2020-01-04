package cc.thas.mail.message.resource;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 二进制资源Getter
 *
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/1 21:21
 */
public interface ResourceGetter {

    String getContentId() throws MessagingException;

    String getFileName() throws MessagingException;

    String getContentType() throws MessagingException;

    InputStream getContent() throws IOException, MessagingException;
}
