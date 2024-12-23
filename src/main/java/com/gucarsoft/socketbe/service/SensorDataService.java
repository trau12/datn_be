package com.gucarsoft.socketbe.service;

import com.gucarsoft.socketbe.model.SensorData;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class SensorDataService {
    private final WriteApiBlocking writeApi;

    public SensorDataService(InfluxDBClient influxDBClient) {
        this.writeApi = influxDBClient.getWriteApiBlocking();
    }

    public void saveSensorData(SensorData sensorData) {
        try {
            if (sensorData.getSensors() == null || sensorData.getSensors().isEmpty()) {
                log.warn("No sensor data to save.");
                return;
            }

            Point point = Point.measurement("sensors")
                    .addFields(sensorData.getSensors()) // Thêm tất cả các trường sensors
                    .time(Instant.now(), WritePrecision.MS);

            writeApi.writePoint(point);
            log.info("Sensor data saved to InfluxDB: {}", sensorData);
        } catch (Exception e) {
            log.error("Error saving sensor data to InfluxDB", e);
        }
    }
}

