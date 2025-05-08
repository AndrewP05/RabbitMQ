package co.AndrewP05.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.math.BigDecimal;

public class Order {
    public String id;
    public List<String> items;
    public BigDecimal total;

    private static ObjectMapper MAPPER = new ObjectMapper();

    public String toJson() throws Exception {
        return MAPPER.writeValueAsString(this);
    }
    public static Order fromJson(String json) throws Exception {
        return MAPPER.readValue(json, Order.class);
    }
}
