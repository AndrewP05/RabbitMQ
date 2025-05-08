package co.AndrewP05.rabbit;

import co.AndrewP05.domain.Notification;
import com.rabbitmq.client.*;

public class NotificationConsumer {
    public static void main(String[] args) throws Exception {
        Connection conn = RabbitConnection.getInstance(null);
        Channel ch = conn.createChannel();

        ch.basicConsume("notifications.email.queue", true,
            new DefaultConsumer(ch) {
                @Override
                public void handleDelivery(String consumerTag, Envelope env,
                                           AMQP.BasicProperties props, byte[] body) {
                    try {
                        Notification note = Notification.fromJson(new String(body, "UTF-8"));
                        System.out.println("[Email] Enviando email a: " + note.email);
                        // lógica de envío de email...
                    } catch (Exception ignore) {}
                }
        });
    }
}
