package cc.thas.mail.message.content.wrapper;

import cc.thas.mail.message.content.AbstractMessageContentWrapper;

import javax.mail.internet.MimeUtility;
import java.io.IOException;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2019/12/30 21:43
 */
public class TextPlainContentWrapper extends AbstractMessageContentWrapper {

    private final String text;

    public TextPlainContentWrapper(String text) {
        this.text = text;
    }

    @Override
    public void init() {

    }

    @Override
    public String getPlainText() throws IOException {
        return MimeUtility.decodeText(text);
    }

}
