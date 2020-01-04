package cc.thas.mail.message.content.wrapper;

import cc.thas.mail.message.content.AbstractMessageContentWrapper;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2019/12/30 21:44
 */
public class TextHtmlContentWrapper extends AbstractMessageContentWrapper {

    private final String html;

    public TextHtmlContentWrapper(String html) {
        this.html = html;
    }

    @Override
    public void init() {

    }

    @Override
    public String getHtml() {
        return html;
    }

}
