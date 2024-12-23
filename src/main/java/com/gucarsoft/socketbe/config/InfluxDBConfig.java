package com.gucarsoft.socketbe.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfig {
    @Bean
    public InfluxDBClient influxDBClient() {
        String url = "http://localhost:8086";
        String token = "jsrNd6Ca4wKlFM_LM36Fj3cS4FNlPmp_V2oqRMkAjDLtzAOSYChlxHr_PsVHXIzg5bntnuNsZoZzdwYJBjJshw==";
        String org = "my-org";
        String bucket = "sensor_data";
        return InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
    }
}


