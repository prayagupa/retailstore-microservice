package com.api.rest.schema;

/**
 * Created by prayagupd
 * on 1/29/17.
 */

public class HealthStatus {

    private final long timestamp;
    private final String applicationName;
    private final String applicationVersion;

    public HealthStatus(long timestamp,
                        String applicationName,
                        String applicationVersion) {
        this.timestamp = timestamp;
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }
}
