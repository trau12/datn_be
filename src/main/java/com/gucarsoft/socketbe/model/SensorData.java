package com.gucarsoft.socketbe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorData {
    private Map<String, Object> sensors;

    private Map<String, Boolean> devices;

    private Boolean isManualMode;
}
