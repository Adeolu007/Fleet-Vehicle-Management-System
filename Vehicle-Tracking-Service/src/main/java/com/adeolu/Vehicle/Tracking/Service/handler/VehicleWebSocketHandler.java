package com.adeolu.Vehicle.Tracking.Service.handler;


import com.adeolu.Vehicle.Tracking.Service.message.LocationUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VehicleWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String vehicleId = session.getUri().getQuery();
        sessions.put(vehicleId, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages if needed
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String vehicleId = session.getUri().getQuery();
        sessions.remove(vehicleId);
    }

    public void sendVehicleLocationUpdate(String vehicleId, double latitude, double longitude) {
        WebSocketSession session = sessions.get(vehicleId);
        if (session != null && session.isOpen()) {
            try {
                messagingTemplate.convertAndSendToUser(vehicleId, "/topic/vehicle-location", new LocationUpdate(latitude, longitude));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
