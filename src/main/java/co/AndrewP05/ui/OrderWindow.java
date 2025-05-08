package co.AndrewP05.ui;

import co.AndrewP05.rabbit.RabbitConnection;
import co.AndrewP05.rabbit.OrderProducer;
import co.AndrewP05.domain.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class OrderWindow extends JFrame {
    private JTextField txtId;
    private JTextField txtItems;
    private JTextField txtTotal;
    private JButton btnSend;

    public OrderWindow() {
        setTitle("Enviar Pedidos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 220);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        // Layout de formulario simple
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID Pedido:"), gbc);
        txtId = new JTextField();
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtId, gbc);

        // Items
        gbc.gridy = 1; gbc.gridx = 0; gbc.weightx = 0;
        panel.add(new JLabel("Items (coma):"), gbc);
        txtItems = new JTextField();
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtItems, gbc);

        // Total
        gbc.gridy = 2; gbc.gridx = 0; gbc.weightx = 0;
        panel.add(new JLabel("Total:"), gbc);
        txtTotal = new JTextField();
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(txtTotal, gbc);

        // Botón enviar
        btnSend = new JButton("Enviar Pedido");
        btnSend.addActionListener(this::onSend);
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(btnSend, gbc);

        add(panel);
    }

    private void onSend(ActionEvent e) {
        try {
            // Leer valores
            String id = txtId.getText().trim();
            if (id.isEmpty()) throw new IllegalArgumentException("ID vacío");

            List<String> items = Arrays.stream(txtItems.getText().split(","))
                                       .map(String::trim)
                                       .filter(s->!s.isEmpty())
                                       .toList();
            if (items.isEmpty()) throw new IllegalArgumentException("Debes poner al menos un ítem");

            BigDecimal total = new BigDecimal(txtTotal.getText().trim());

            // Construir Order
            Order order = new Order();
            order.id = id;
            order.items = items;
            order.total = total;

            // Conectar y enviar
            Connection conn = RabbitConnection.getInstance(null);
            Channel ch = conn.createChannel();
            OrderProducer prod = new OrderProducer(ch);
            prod.sendOrder(order);
            ch.close();

            JOptionPane.showMessageDialog(this, "Pedido enviado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al enviar pedido:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Arranca la UI en el hilo de Swing
        SwingUtilities.invokeLater(() -> {
            new OrderWindow().setVisible(true);
        });
    }
}
