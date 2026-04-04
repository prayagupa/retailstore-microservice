package com.api.rest.schema;

public record HealthStatus(long timestamp,
                           String applicationName,
                           String applicationVersion) {
}
