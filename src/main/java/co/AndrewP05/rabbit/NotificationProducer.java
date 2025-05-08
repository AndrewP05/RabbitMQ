package co.AndrewP05.rabbit;

import com.rabbitmq.client.Channel;

import co.AndrewP05.domain.Notification;

public class NotificationProducer {
    private static final String EXCHANGE = "notifications.exchange";
    private final Channel channel;

    public NotificationProducer(Channel channel) throws Exception {
        this.channel = channel;
        channel.exchangeDeclare(EXCHANGE, "fanout", true);
    }

    public void sendNotification(Notification note) throws Exception {
        String payload = note.toJson();
        channel.basicPublish(
            EXCHANGE,
            "",
            com.rabbitmq.client.MessageProperties.PERSISTENT_TEXT_PLAIN,
            payload.getBytes(java.nio.charset.StandardCharsets.UTF_8)
        );
        System.out.println("[x] Notification sent for user: " + note.userId);
    }
}
