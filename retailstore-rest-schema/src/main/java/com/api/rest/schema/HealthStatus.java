package com.api.rest.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public final class HealthStatus {
    private long timestamp;
    private String applicationName;
    private String applicationVersion;
}
