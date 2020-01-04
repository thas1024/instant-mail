package cc.thas.mail.message.content;

import cc.thas.mail.message.resource.ResourceGetter;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2019/12/28 22:35
 */
public interface MessageContentGetter {

    default String getContent() throws IOException, MessagingException {
        String plainText = getPlainText();
        return plainText != null ? plainText : getHtml();
    }

    /**
     * 获得纯文本内容
     */
    String getPlainText() throws IOException, MessagingException;

    /**
     * 获得超文本内容
     */
    String getHtml() throws IOException, MessagingException;

    /**
     * 获得超文本内嵌资源
     */
    List<ResourceGetter> getInlineResources() throws MessagingException, IOException;

    /**
     * 获得附件资源
     */
    List<ResourceGetter> getAttachmentResources() throws MessagingException, IOException;

}
