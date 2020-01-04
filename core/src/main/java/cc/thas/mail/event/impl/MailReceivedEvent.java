package cc.thas.mail.event.impl;

import cc.thas.mail.event.Event;
import cc.thas.mail.message.MailMessage;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/4 13:07
 */
public class MailReceivedEvent implements Event<MailReceivedEvent.MailReceivedPayload> {

    private final MailReceivedPayload payload;

    public MailReceivedEvent(String taskId, MailMessage message) {
        MailReceivedPayload payload = new MailReceivedPayload();
        payload.setTaskId(taskId);
        payload.setMessage(message);
        this.payload = payload;
    }

    @Override
    public MailReceivedPayload getPayload() {
        return payload;
    }

    public static class MailReceivedPayload {
        private String taskId;
        private MailMessage message;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public MailMessage getMessage() {
            return message;
        }

        public void setMessage(MailMessage message) {
            this.message = message;
        }
    }
}
