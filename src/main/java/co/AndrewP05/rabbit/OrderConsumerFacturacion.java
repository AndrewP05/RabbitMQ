package co.AndrewP05.rabbit;

import co.AndrewP05.domain.Order;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class OrderConsumerFacturacion {
    public static void main(String[] args) throws Exception {
        Connection conn = RabbitConnection.getInstance(null);
        Channel ch = conn.createChannel();

        ch.basicQos(1);
        ch.basicConsume("orders.queue", false, "facturacion-tag",
            new DefaultConsumer(ch) {
                @Override
                public void handleDelivery(String consumerTag, Envelope env,
                                           AMQP.BasicProperties props, byte[] body) throws IOException {
                    try {
                        Order order = Order.fromJson(new String(body, "UTF-8"));
                        System.out.println("[Facturación] Generando factura para order: " + order.id);
                        // lógica de cálculo y generación de factura...
                        ch.basicAck(env.getDeliveryTag(), false);
                    } catch (Exception e) {
                        ch.basicReject(env.getDeliveryTag(), false);
                    }
                }
        });
    }
}
