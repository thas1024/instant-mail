package cc.thas.mail.message.content;

import cc.thas.mail.message.Initable;
import cc.thas.mail.message.resource.ResourceGetter;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.AbstractList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2019/12/28 22:41
 */
public abstract class AbstractMessageContentWrapper implements MessageContentGetter, Initable {

    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String MULTI_PART_ALTERNATIVE = "multipart/alternative";
    public static final String MULTI_PART_RELATED = "multipart/related";
    public static final String MULTI_PART_MIXED = "multipart/mixed";

    private volatile boolean initialed;

    @Override
    public String getPlainText() throws IOException, MessagingException {
        return null;
    }

    @Override
    public String getHtml() throws IOException, MessagingException {
        return null;
    }

    @Override
    public List<ResourceGetter> getInlineResources() throws MessagingException, IOException {
        return ImmutableArrayList.emptyList();
    }

    @Override
    public List<ResourceGetter> getAttachmentResources() throws MessagingException, IOException {
        return ImmutableArrayList.emptyList();
    }

    @Override
    public void checkInitialed() throws MessagingException, IOException {
        if (!initialed) {
            init();
            initialed = true;
        }
    }

    protected static final class ImmutableArrayList<E> extends AbstractList<E> {

        @SuppressWarnings("rawtypes")
        protected static final List EMPTY_LIST = new ImmutableArrayList<>();

        @SuppressWarnings("unchecked")
        protected static <T> List<T> emptyList(){
            return (List<T>)EMPTY_LIST;
        }

        private final List<E> list;

        public ImmutableArrayList(){
            this(null);
        }

        public ImmutableArrayList(List<E> list) {
            this.list = list == null ? Collections.emptyList() : list;
        }

        @Override
        public E get(int index) {
            return list.get(index);
        }

        @Override
        public int size() {
            return list.size();
        }
    }
}
