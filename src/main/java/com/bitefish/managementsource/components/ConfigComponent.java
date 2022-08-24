package com.bitefish.managementsource.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigComponent {
    @Value("${com.bitefish.management.source_connection}")
    private String sourceConnection;

    @Value("${com.bitefish.management.source_database}")
    private String sourceDatabase;


    public String getSourceConnection() {
        return sourceConnection;
    }

    public String getSourceDatabase() {
        return sourceDatabase;
    }
}

