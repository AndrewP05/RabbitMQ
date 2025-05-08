package co.AndrewP05.rabbit;

import co.AndrewP05.domain.Order;
import com.rabbitmq.client.*;
import java.io.IOException;
import co.AndrewP05.rabbit.RabbitConnection;

public class OrderConsumerInventario {
    public static void main(String[] args) throws Exception {
        Connection conn = RabbitConnection.getInstance(null);
        Channel ch = conn.createChannel();

        // Cola de órdenes con DLX configurado en tu setup inicial
        ch.basicQos(1);
        ch.basicConsume("orders.queue", false, "inventario-tag",
            new DefaultConsumer(ch) {
                @Override
                public void handleDelivery(String consumerTag, Envelope env,
                                           AMQP.BasicProperties props, byte[] body) throws IOException {
                    try {
                        Order order = Order.fromJson(new String(body, "UTF-8"));
                        System.out.println("[Inventario] Procesando order: " + order.id);
                        // lógica de reserva de stock...
                        ch.basicAck(env.getDeliveryTag(), false);
                    } catch (Exception e) {
                        ch.basicReject(env.getDeliveryTag(), false);
                    }
                }
        });
    }
}
