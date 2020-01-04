package cc.thas.mail.event;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 12:58
 */
public interface Event<T> {

    T getPayload();
}
