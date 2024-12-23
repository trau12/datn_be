package com.gucarsoft.socketbe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gucarsoft.socketbe.model.Message;
import com.gucarsoft.socketbe.model.SensorData;
import com.gucarsoft.socketbe.service.SensorDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final SensorDataService sensorDataService;

    public MessageController(SimpMessagingTemplate messagingTemplate, SensorDataService sensorDataService) {
        this.messagingTemplate = messagingTemplate;
        this.sensorDataService = sensorDataService;
    }

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, @RequestBody Message<?> message) {
        try {
            log.info("Received message for /chat/{}: {}", to, message);

            String destination = "/topic/messages/" + to;

            switch (message.getMessageType()) {
                case "SENSOR":
                    log.info("Processing SENSOR message: {}", message);

                    // Convert Message<?> to SensorData and save to InfluxDB
                    SensorData sensorData = convertToSensorData(message.getMessage());
                    sensorDataService.saveSensorData(sensorData);

                    // Forward the sensor data to WebSocket clients
                    messagingTemplate.convertAndSend(destination, message);
                    break;

                case "CONTROL":
                    log.info("Processing CONTROL message: {}", message);

                    // Process control commands
                    processControlMessage(message);

                    // Forward the control message to WebSocket clients
                    messagingTemplate.convertAndSend(destination, message);
                    break;

                default:
                    log.warn("Unhandled message type: {}", message.getMessageType());
            }
        } catch (Exception e) {
            log.error("Error processing message: ", e);
        }
    }

    private SensorData convertToSensorData(Object message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(message, SensorData.class);
        } catch (Exception e) {
            log.error("Error converting message to SensorData: ", e);
            throw new IllegalArgumentException("Invalid sensor data format");
        }
    }

    private void processControlMessage(Message<?> message) {
        try {
            Map<String, Object> controlData = (Map<String, Object>) message.getMessage();

            if (controlData.containsKey("isManualMode")) {
                boolean isManualMode = (boolean) controlData.get("isManualMode");
                log.info("Updated isManualMode to: {}", isManualMode ? "Manual" : "Auto");
            }

            // Additional logic for control messages can be added here
        } catch (Exception e) {
            log.error("Error processing control message: ", e);
        }
    }
}

