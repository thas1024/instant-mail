package cc.thas.mail.message;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * 可初始化的
 *
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/1 21:10
 */
public interface Initable {

    /**
     * 检查是否初始化
     */
    void checkInitialed() throws MessagingException, IOException;

    /**
     * 初始化
     */
    void init() throws MessagingException, IOException;
}
