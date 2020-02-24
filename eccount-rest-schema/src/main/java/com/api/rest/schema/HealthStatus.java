package com.api.rest.schema;

//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;

/**
 * Created by prayagupd
 * on 1/29/17.
 */

//
//@AllArgsConstructor
//@NoArgsConstructor
public class HealthStatus {

    private long timestamp;
    private String applicationName;
    private String applicationVersion;

    public HealthStatus() {

    }

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

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }
}
