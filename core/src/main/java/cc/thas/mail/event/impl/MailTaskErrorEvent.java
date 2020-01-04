package cc.thas.mail.event.impl;

import cc.thas.mail.event.Event;
import cc.thas.mail.exception.MailTaskException;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 15:57
 */
public class MailTaskErrorEvent implements Event<MailTaskErrorEvent.MailTaskErrorPayload> {

    MailTaskErrorPayload payload;

    public MailTaskErrorEvent(String taskId, MailTaskException exception) {
        payload = new MailTaskErrorPayload();
        payload.setTaskId(taskId);
        payload.setException(exception);
    }

    @Override
    public MailTaskErrorPayload getPayload() {
        return payload;
    }

    public static class MailTaskErrorPayload {
        private String taskId;
        private MailTaskException exception;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public MailTaskException getException() {
            return exception;
        }

        public void setException(MailTaskException exception) {
            this.exception = exception;
        }
    }
}
