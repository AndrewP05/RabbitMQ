package co.AndrewP05.rabbit;

import co.AndrewP05.domain.Order;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class OrderProducer {
    private static final String EXCHANGE = "orders.exchange";
    private final Channel channel;

    public OrderProducer(Channel channel) throws Exception {
        this.channel = channel;
        channel.exchangeDeclare(EXCHANGE, "direct", true);
    }

    public void sendOrder(Order order) throws Exception {
        String payload = order.toJson();
        channel.basicPublish(
            EXCHANGE,
            "order.new",
            com.rabbitmq.client.MessageProperties.PERSISTENT_TEXT_PLAIN,
            payload.getBytes(java.nio.charset.StandardCharsets.UTF_8)
        );
        System.out.println("[x] Order sent: " + order.id);
    }
}
