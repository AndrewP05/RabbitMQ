package co.AndrewP05.rabbit;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitConnection {
    private static volatile Connection connection;
    private static final String HOST = "127.0.0.1";

    private RabbitConnection() { }

    public static Connection getInstance(String virtualHost) throws Exception {
        if (connection == null || !connection.isOpen()) {
            synchronized (RabbitConnection.class) {
                if (connection == null || !connection.isOpen()) {
                    ConnectionFactory factory = new ConnectionFactory();
                    factory.setHost(HOST);
                    if (virtualHost != null) {
                        factory.setVirtualHost(virtualHost);
                    }
                    connection = factory.newConnection();
                }
            }
        }
        return connection;
    }
}
