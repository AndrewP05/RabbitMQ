package co.AndrewP05.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Notification {
    public String userId;
    public String email;
    public String message;

    private static ObjectMapper MAPPER = new ObjectMapper();

    public String toJson() throws Exception {
        return MAPPER.writeValueAsString(this);
    }
    public static Notification fromJson(String json) throws Exception {
        return MAPPER.readValue(json, Notification.class);
    }
}
